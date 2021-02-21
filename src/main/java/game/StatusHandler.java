package game;

import entity.*;

import java.util.List;

class StatusHandler {

    private boolean gameOver = false;
    private int timeLimit;
    Status statusToBeDisplayed;

    public StatusHandler(Status status) {
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

    //Two states need to change in Status:
    //Inside a sequence: matrixCell successively in the buffer-> state: ADDED
    //Sequence: check if can be marked as SUCCESS or FAIL
    private void updateDaemons() {

        Buffer buffer = statusToBeDisplayed.getBuffer();
        int bufferCount = buffer.getBufferCounter();
        List<Daemon> tmpSeq = statusToBeDisplayed.getDaemons();
        for (int i = 0; i < tmpSeq.size(); i++) {
            if (!tmpSeq.get(i).isFailed() && !tmpSeq.get(i).isSucceeded()) {
                if (buffer.getBufferCode( bufferCount- 1).equals(tmpSeq.get(i).getDaemonCells().get(bufferCount - 1).getCode())) {
                    if (bufferCount >= 2 && tmpSeq.get(i).getDaemonCells().get(bufferCount - 2).isAdded()) { //OLD SEQUENCE
                        tmpSeq.get(i).getDaemonCells().get(bufferCount - 1).setAdded(true);
                        tmpSeq.get(i).getDaemonCells().get(bufferCount - 2).setSelected(false);
                        tmpSeq.get(i).getDaemonCells().get(bufferCount - 1).setSelected(true);
                    } else { //NEW SEQUENCE
                        tmpSeq.get(i).getDaemonCells().get(bufferCount - 1).setAdded(true);
                        tmpSeq.get(i).getDaemonCells().get(bufferCount - 1).setSelected(true);
                    }
                } else {
                    int emptyCount = 0;
                    for (int m = 0; m < bufferCount - 1; m++) {
                        tmpSeq.get(i).getDaemonCells().get(m).setAdded(false);
                        tmpSeq.get(i).getDaemonCells().get(m).setSelected(false);
                        if (tmpSeq.get(i).getDaemonCells().get(m).getCode().equals("")) {
                            emptyCount += 1;
                        }
                    }
                    for (int n = 0; n < bufferCount - emptyCount - 1; n++) {
                        tmpSeq.get(i).addEmptyCell();
                    }
                    if (statusToBeDisplayed.getBuffer().getBufferCode(bufferCount - 1).equals(tmpSeq.get(i).getDaemonCells().get(bufferCount - 1).getCode())) {
                        tmpSeq.get(i).getDaemonCells().get(bufferCount - 1).setAdded(true);
                        tmpSeq.get(i).getDaemonCells().get(bufferCount - 1).setSelected(true);
                    } else {
                        tmpSeq.get(i).addEmptyCell();
                    }
                    if (tmpSeq.get(i).getDaemonCells().size() > buffer.getBufferSize()) {
                        tmpSeq.get(i).setFailed(true);
                    }
                }
                for (int j = 0; j < tmpSeq.get(i).getDaemonCells().size(); j++) {
                    tmpSeq.get(i).setSucceeded(tmpSeq.get(i).getDaemonCells().get(j).isAdded());
                }
            }
        }
        statusToBeDisplayed.setSequences(tmpSeq);
    }


    public void markUnrewardedDaemonsFailed() {
        List<Daemon> tmpSeq = statusToBeDisplayed.getDaemons();
        for (Daemon sequence : tmpSeq) {
            if (!sequence.isFailed() && !sequence.isSucceeded()) {
                sequence.setFailed(true);
            }
        }
        statusToBeDisplayed.setSequences(tmpSeq);
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
        statusToBeDisplayed = new Status(new Puzzle(), statusToBeDisplayed.getGameDifficulty());
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
