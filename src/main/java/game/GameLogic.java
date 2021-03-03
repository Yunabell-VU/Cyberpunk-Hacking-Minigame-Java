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

        if (isDaemonsAllRewarded() && !gameOver) statusToBeDisplayed.switchPuzzle();
        return statusToBeDisplayed;
    }

    //Change the whole code matrix tiles' state in the Status ->codeMatrix
    //e.g. from AVAILABLE to SELECTED
    private void updateCodeMatrix(Coordinate clickedCellPosition) {
        CodeMatrix codeMatrix = statusToBeDisplayed.getCodeMatrix();
        codeMatrix.updateCellPicked(clickedCellPosition);
        codeMatrix.disableAllCells();

        if (codeMatrix.isRowAvailable()) codeMatrix.setOneColAvailable();
        else codeMatrix.setOneRowAvailable();

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
        int bufferCounter = statusToBeDisplayed.getBuffer().getBufferCounter();
        List<Daemon> daemons = statusToBeDisplayed.getDaemons();

        for (Daemon daemon : daemons) {
            if (daemon.isNotRewarded()) { //if this is an unmarked Daemon
                updateDaemonCells(daemon, bufferCounter);

                if (daemon.isLastCellAdded()) daemon.setDaemonSucceeded();
                if (isDaemonExceedsBuffer(daemon)) daemon.setDaemonFailed();
            }
        }
    }

    private void updateDaemonCells(Daemon daemon, int bufferCounter) {
        daemon.setAllDaemonCellsUnSelected();
        DaemonCell cellWaitingToBeCheck = daemon.getDaemonCell(bufferCounter - 1);

        updateStatesOfMatchedDaemonCell(cellWaitingToBeCheck);

        if (!cellWaitingToBeCheck.isMatched()) {
            daemon.setAllDaemonCellsUnAdded();
            alignDaemonCellWithBuffer(daemon, bufferCounter);

            cellWaitingToBeCheck = daemon.getDaemonCell(bufferCounter - 1);//switch waiting cell to first unEmpty cell
            updateStatesOfMatchedDaemonCell(cellWaitingToBeCheck);

            if(!cellWaitingToBeCheck.isMatched()) daemon.addEmptyCell();
        }
    }

    private void updateStatesOfMatchedDaemonCell(DaemonCell waitingCell){
        String pickedCharacter = statusToBeDisplayed.getCodeMatrix().getPickedCharacter();
        String lastBufferCharacter = statusToBeDisplayed.getBuffer().getLastCodeInBuffer();
        if (waitingCell.isMatch(pickedCharacter)) waitingCell.setSelected(true);
        if (waitingCell.isMatch(lastBufferCharacter)) waitingCell.setMatched(true);
    }

    private void alignDaemonCellWithBuffer(Daemon daemon, int bufferCounter) {
        int positionOfWaitingCell = getWaitingDaemonCellPosition(daemon, bufferCounter);
        for (int i = 0; i < positionOfWaitingCell-1; i++) {
            daemon.addEmptyCell();
        }
    }

    private int getWaitingDaemonCellPosition(Daemon daemon,int bufferCounter){
        int emptyCellCounter = 0;
        for (int i = 0; i < bufferCounter - 1; i++) {
            if (daemon.getDaemonCell(i).getCode().equals("")) emptyCellCounter += 1;
        }
        return bufferCounter - emptyCellCounter;
    }

    private boolean isDaemonExceedsBuffer(Daemon daemon) {
        return daemon.getDaemonCells().size() > statusToBeDisplayed.getBuffer().getBufferSize();
    }

    private void updateReward() {
        for (Daemon daemon : statusToBeDisplayed.getDaemons()) {
            if (daemon.isNotRewarded()) {
                if (daemon.isSucceeded()) {
                    rewardTime();
                    daemon.setRewarded();
                }
                if (daemon.isFailed()) {
                    punishTime();
                    daemon.setRewarded();
                }
            }
        }
    }


    public void markUnrewardedDaemonsFailed() {
        List<Daemon> tmpSeq = statusToBeDisplayed.getDaemons();
        for (Daemon sequence : tmpSeq) {
            if (sequence.isNotRewarded()) sequence.setDaemonFailed();
        }
    }

    public void switchLogicStatusToGameStatus(Status currentStatus){
        statusToBeDisplayed = currentStatus;
    }

    //Do Not modify this function!
    public void setGameOver() {
        gameOver = true;
    }

    private boolean isDaemonsAllRewarded() {
        for (Daemon daemon : statusToBeDisplayed.getDaemons()) {
            if (daemon.isNotRewarded()) return false;
        }
        return true;
    }

    private void rewardTime() {
        updateTimeLimit(statusToBeDisplayed.getGameDifficulty().getTimeReward());
    }

    private void punishTime() {
        updateTimeLimit(statusToBeDisplayed.getGameDifficulty().getTimePunishment());
    }

    public void updateTimeLimit(int offset) {
        timeLimit = Math.max(timeLimit + offset, 0);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimitZero() {
        timeLimit = 0;
    }
}
