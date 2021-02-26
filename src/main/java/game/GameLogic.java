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

    private boolean isMatched(int bufferCount, List<Daemon> tmpSeq, int i){
        return statusToBeDisplayed.getCodeMatrix().getPickedCharacter().equals(getSeqCell(tmpSeq, i, bufferCount-1).getCode());
    }

    private void unselectAllCells(Daemon tmpSeq){
        for (int i = 0; i < tmpSeq.getDaemonCells().size(); i++){
            tmpSeq.getDaemonCells().get(i).setSelected(false);
        }
    }

    private void updateMatchSeq(List<Daemon> tmpSeq, int i, int bufferCount){
        getSeqCell(tmpSeq, i, bufferCount-1).setAdded(true);
        getSeqCell(tmpSeq, i, bufferCount-1).setSelected(true);
    }

    private void updateUnmatchSeq(List<Daemon> tmpSeq, int i, int bufferCount){
        int emptyCount = 0;
        for (int m = 0; m < bufferCount - 1; m++) {
            getSeqCell(tmpSeq, i, m).setAdded(false);
            getSeqCell(tmpSeq, i, m).setSelected(false);
            if (getSeqCell(tmpSeq, i, m).getCode().equals("")) {
                emptyCount += 1;
            }
        }
        moveUnmatchSeq(tmpSeq, i, bufferCount-emptyCount);
        matchLastCell(tmpSeq, i, bufferCount);
        if(isExceed(tmpSeq, i)){tmpSeq.get(i).setFailed(true);}
    }

    private void moveUnmatchSeq(List<Daemon> tmpSeq, int i, int moves){
        for (int n = 0; n < moves - 1; n++) {
            tmpSeq.get(i).addEmptyCell();
        }
    }

    private void matchLastCell(List<Daemon> tmpSeq, int i, int bufferCount){
        if(isMatched(bufferCount, tmpSeq, i)){ //compare last cell of buffer and the 1st of seq
            getSeqCell(tmpSeq, i, bufferCount-1).setAdded(true);
            getSeqCell(tmpSeq, i, bufferCount-1).setSelected(true);
        }
        else tmpSeq.get(i).addEmptyCell();
    }

    private boolean isExceed(List<Daemon> tmpSeq, int i){
        return tmpSeq.get(i).getDaemonCells().size() > statusToBeDisplayed.getBuffer().getBufferSize();
    }

    //Two states need to change in Status:
    //Inside a sequence: matrixCell successively in the buffer-> state: ADDED
    //Sequence: check if can be marked as SUCCESS or FAIL
    private void updateDaemons() {
        int bufferCount = statusToBeDisplayed.getBuffer().getBufferCounter();
        List<Daemon> tmpSeq = statusToBeDisplayed.getDaemons();
        for (int i = 0; i < tmpSeq.size(); i++) {
            if (!isMarked(tmpSeq,i)) { //if this is an unmarked sequence
                unselectAllCells(tmpSeq.get(i)); //unselected All Cells in the specific seq
                if (isMatched(bufferCount, tmpSeq, i)) { //MATCHED SEQUENCE
                    updateMatchSeq(tmpSeq, i, bufferCount);
                }
                else {//UNMATCHED SEQUENCE
                    updateUnmatchSeq(tmpSeq, i, bufferCount);
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
