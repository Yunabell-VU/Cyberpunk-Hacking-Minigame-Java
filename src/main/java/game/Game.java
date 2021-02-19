package game;

import entity.*;

import java.util.List;

class Game {

    private boolean timeOut = false;
    public Status status;

    public Game(Status status) {
        this.status = status;
    }

    //
    public void updateStatus() {
        updateBuffer();
        updateCodeMatrix();
        updateDaemons();
        updateReward();

        if (isDaemonsAllRewarded() && !timeOut)
            switchPuzzle();
    }

    //checked when time out
    public void finalCheck() {
        updateDaemons();
    }

    //Change the whole code matrix tiles' state in the Status ->codeMatrix
    //e.g. from AVAILABLE to SELECTED
    private void updateCodeMatrix() {
        CodeMatrix codeMatrix = status.getCodeMatrix();
        codeMatrix.disableAllCells();

        if (codeMatrix.isColAvailable()) codeMatrix.setOneRowAvailable();
        else codeMatrix.setOneColAvailable();

        if (status.getBuffer().isBufferFull()) codeMatrix.disableAllCells();

    }

    //ADD corresponding matrixCell in the buffer
    //update buffer in Status
    private void updateBuffer() {
        if (!status.getBuffer().isBufferFull())
            status.getBuffer().addCellToBuffer(status.getCodeMatrix().getSelectedCharacter());
    }

    //Two states need to change in Status:
    //Inside a sequence: matrixCell successively in the buffer-> state: ADDED
    //Sequence: check if can be marked as SUCCESS or FAIL
    private void updateDaemons() {
        if (timeOut) {
            gameFailed();
        }
        Buffer buffer = status.getBuffer();
        int bufferCount = buffer.getBufferCounter();
        List<Daemon> tmpSeq = status.getDaemons();
        for (int i = 0; i < tmpSeq.size(); i++) {
            if (!tmpSeq.get(i).isFailed() && !tmpSeq.get(i).isSucceeded()) {
                if (buffer.getBufferCode( bufferCount- 1).equals(tmpSeq.get(i).getSeq().get(bufferCount - 1).getCode())) {
                    if (bufferCount >= 2 && tmpSeq.get(i).getSeq().get(bufferCount - 2).isAdded()) { //OLD SEQUENCE
                        tmpSeq.get(i).getSeq().get(bufferCount - 1).setAdded(true);
                        tmpSeq.get(i).getSeq().get(bufferCount - 2).setSelected(false);
                        tmpSeq.get(i).getSeq().get(bufferCount - 1).setSelected(true);
                    } else { //NEW SEQUENCE
                        tmpSeq.get(i).getSeq().get(bufferCount - 1).setAdded(true);
                        tmpSeq.get(i).getSeq().get(bufferCount - 1).setSelected(true);
                    }
                } else {
                    int emptyCount = 0;
                    for (int m = 0; m < bufferCount - 1; m++) {
                        tmpSeq.get(i).getSeq().get(m).setAdded(false);
                        tmpSeq.get(i).getSeq().get(m).setSelected(false);
                        if (tmpSeq.get(i).getSeq().get(m).getCode().equals("")) {
                            emptyCount += 1;
                        }
                    }
                    for (int n = 0; n < bufferCount - emptyCount - 1; n++) {
                        tmpSeq.get(i).addEmptyCell();
                    }
                    if (status.getBuffer().getBufferCode(bufferCount - 1).equals(tmpSeq.get(i).getSeq().get(bufferCount - 1).getCode())) {
                        tmpSeq.get(i).getSeq().get(bufferCount - 1).setAdded(true);
                        tmpSeq.get(i).getSeq().get(bufferCount - 1).setSelected(true);
                    } else {
                        tmpSeq.get(i).addEmptyCell();
                    }
                    if (tmpSeq.get(i).getSeq().size() > buffer.getBufferSize()) {
                        tmpSeq.get(i).setFailed(true);
                    }
                }
                for (int j = 0; j < tmpSeq.get(i).getSeq().size(); j++) {
                    tmpSeq.get(i).setSucceeded(tmpSeq.get(i).getSeq().get(j).isAdded());
                }
            }
        }
        status.setSequences(tmpSeq);
    }


    public void gameFailed() {
        List<Daemon> tmpSeq = status.getDaemons();
        for (Daemon sequence : tmpSeq) {
            if (!sequence.isFailed() && !sequence.isSucceeded()) {
                sequence.setFailed(true);
            }
        }
        status.setSequences(tmpSeq);
    }

    //Do Not modify this function!
    public void setTimeOut() {
        timeOut = true;
    }

    private boolean isDaemonsAllRewarded() {
        for (Daemon daemon : status.getDaemons()) {
            if (!daemon.isRewarded())
                return false;
        }
        return true;
    }

    private void switchPuzzle() {
        status = new Status(new Puzzle(), status.getGameDifficulty(), status.getTimeLimit(), status.getScore());
    }

    private void updateReward() {
        for (Daemon daemon : status.getDaemons()) {
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
        int rewardTime = status.getGameDifficulty().getTimeReward();
        status.addTimeLimit(rewardTime);
    }

    private void punishTime() {
        status.addTimeLimit(status.getGameDifficulty().getTimePunishment());
    }

    public boolean isTimeOut() {
        return timeOut;
    }

}
