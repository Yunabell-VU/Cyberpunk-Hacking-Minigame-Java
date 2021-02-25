package game;

import entity.*;

import java.util.List;

class GameLogic {

    private boolean gameOver = false;
    private int timeLimit;
    Status statusToBeDisplayed;

    public GameLogic(Status status) {
        this.statusToBeDisplayed = status;
        this.timeLimit = status.getGameDifficulty().getInitTimeLimit();
    }

    //
    public Status updateStatus(Status status, Coordinate clickedCellPosition) {
        statusToBeDisplayed = status;
        updateCodeMatrix(clickedCellPosition);
        updateBuffer();
        updateDaemons();
        updateReward();

        if (isDaemonsAllRewarded() && !gameOver)
            switchPuzzle();
        return statusToBeDisplayed;
    }

    //Change the whole code matrix tiles' state in the Status ->codeMatrix
    //e.g. from AVAILABLE to SELECTED
    private void updateCodeMatrix(Coordinate clickedCellPosition) {
        CodeMatrix codeMatrix = statusToBeDisplayed.getCodeMatrix();
        codeMatrix.setCellPicked(clickedCellPosition);
        codeMatrix.disableAllCells();

        if (codeMatrix.isColAvailable()) codeMatrix.setOneRowAvailable();
        else codeMatrix.setOneColAvailable();

        if (statusToBeDisplayed.getBuffer().isBufferFull()) codeMatrix.disableAllCells();

    }

    //ADD corresponding matrixCell in the buffer
    //update buffer in Status
    private void updateBuffer() {
        if (!statusToBeDisplayed.getBuffer().isBufferFull())
            statusToBeDisplayed.getBuffer().addCellToBuffer(statusToBeDisplayed.getCodeMatrix().getPickedCharacter());
    }

    private boolean isMarked(List<Daemon> tmpSeq, int i){
        return tmpSeq.get(i).isFailed() || tmpSeq.get(i).isSucceeded();
    }

    private DaemonCell getSeqCell(List<Daemon> tmpSeq, int i, int index){
        return tmpSeq.get(i).getDaemonCells().get(index);
    }

    private boolean isMatched(Buffer buffer, int bufferCount, List<Daemon> tmpSeq, int i){
        return buffer.getBufferCode(bufferCount-1).equals(getSeqCell(tmpSeq, i, bufferCount-1).getCode());
    }

    private void updateMatchSeq(List<Daemon> tmpSeq, int i, int bufferCount){
        if (bufferCount >= 2 && getSeqCell(tmpSeq, i,bufferCount-2).isAdded()) { //OLD SEQUENCE
            getSeqCell(tmpSeq, i, bufferCount-1).setAdded(true);
            getSeqCell(tmpSeq, i, bufferCount-2).setSelected(false);
            getSeqCell(tmpSeq, i, bufferCount-1).setSelected(true);
        }
        else { //NEW SEQUENCE
            getSeqCell(tmpSeq, i, bufferCount-1).setAdded(true);
            getSeqCell(tmpSeq, i, bufferCount-1).setSelected(true);
        }
    }

    private void updateUnmatchSeq(List<Daemon> tmpSeq, int i, Buffer buffer, int bufferCount){
        int emptyCount = 0;
        for (int m = 0; m < bufferCount - 1; m++) {
            getSeqCell(tmpSeq, i, m).setAdded(false);
            getSeqCell(tmpSeq, i, m).setSelected(false);
            if (getSeqCell(tmpSeq, i, m).getCode().equals("")) {
                emptyCount += 1;
            }
        }
        moveUnmatchSeq(tmpSeq, i, bufferCount-emptyCount);
        matchLastCell(tmpSeq, i, buffer, bufferCount);
        if(isExceed(tmpSeq, i, buffer)){tmpSeq.get(i).setFailed(true);}
    }

    private void moveUnmatchSeq(List<Daemon> tmpSeq, int i, int moves){
        for (int n = 0; n < moves - 1; n++) {
            tmpSeq.get(i).addEmptyCell();
        }
    }

    private void matchLastCell(List<Daemon> tmpSeq, int i, Buffer buffer, int bufferCount){
        if(isMatched(buffer, bufferCount, tmpSeq, i)){ //compare last cell of buffer and the 1st of seq
            getSeqCell(tmpSeq, i, bufferCount-1).setAdded(true);
            getSeqCell(tmpSeq, i, bufferCount-1).setSelected(true);
        }
        else tmpSeq.get(i).addEmptyCell();
    }

    private boolean isExceed(List<Daemon> tmpSeq, int i, Buffer buffer){
        return tmpSeq.get(i).getDaemonCells().size() > buffer.getBufferSize();
    }

    //Two states need to change in Status:
    //Inside a sequence: matrixCell successively in the buffer-> state: ADDED
    //Sequence: check if can be marked as SUCCESS or FAIL
    private void updateDaemons() {
        Buffer buffer = statusToBeDisplayed.getBuffer();
        int bufferCount = buffer.getBufferCounter();
        List<Daemon> tmpSeq = statusToBeDisplayed.getDaemons();
        for (int i = 0; i < tmpSeq.size(); i++) {
            if (!isMarked(tmpSeq,i)) { //if this is an unmarked sequence
                if (isMatched(buffer, bufferCount, tmpSeq, i)) { //if seq code match with buffer
                    updateMatchSeq(tmpSeq, i, bufferCount);
                }
                else {// MARKED SEQUENCE
                    updateUnmatchSeq(tmpSeq, i, buffer, bufferCount);
                }
                for (int j = 0; j < tmpSeq.get(i).getDaemonCells().size(); j++) { //check if sequence successes
                    tmpSeq.get(i).setSucceeded(tmpSeq.get(i).getDaemonCells().get(j).isAdded());
                }
            }
        }
    }


    public void markUnrewardedDaemonsFailed() {
        List<Daemon> tmpSeq = statusToBeDisplayed.getDaemons();
        for (Daemon sequence : tmpSeq) {
            if (!sequence.isFailed() && !sequence.isSucceeded()) {
                sequence.setFailed(true);
            }
        }
    }

    //Do Not modify this function!
    public void setGameOver() {
        gameOver = true;
    }

    private boolean isDaemonsAllRewarded() {
        for (Daemon daemon : statusToBeDisplayed.getDaemons()) {
            if (!daemon.isRewarded())
                return false;
        }
        return true;
    }

    private void switchPuzzle() {
        statusToBeDisplayed.switchPuzzle();
    }

    private void updateReward() {
        for (Daemon daemon : statusToBeDisplayed.getDaemons()) {
            if (!daemon.isRewarded()){
                if(daemon.isSucceeded()){
                    rewardTime();
                    daemon.setRewarded(true);
                }
                if(daemon.isFailed()){
                    punishTime();
                    daemon.setRewarded(true);
                }
            }
        }
    }

    private void rewardTime() {
       updateTimeLimit(statusToBeDisplayed.getGameDifficulty().getTimeReward());
    }

    private void punishTime() {
        updateTimeLimit(statusToBeDisplayed.getGameDifficulty().getTimePunishment());
    }

    public void updateTimeLimit( int offset) {
        timeLimit = Math.max(timeLimit + offset, 0);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getTimeLimit(){return timeLimit;}

}
