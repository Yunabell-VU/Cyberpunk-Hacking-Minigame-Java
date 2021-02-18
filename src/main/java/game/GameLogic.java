package game;

import entity.*;

import java.util.List;

 class GameLogic {

    public int timeFlag = 0;
    private  int[] tileSelected = new int [2];
    private int bufferCount = 0;
    private boolean colAvailable = true;
    private boolean timeOut = false;
    public Status status;
    //private Sequence seqCode = new Sequence();

    public GameLogic(Status status){
        this.status = status;
    }

    //Do Not modify this function!
    public void setTileSelected(int[] matrixCell) {
        this.tileSelected = matrixCell;
    }

    //
    public void updateState(){
        updateBuffer();
        updateCodeMatrix();
        updateSequences();
        updateReward();

        if(isDaemonsAllChecked()&&!timeOut)
            switchPuzzle();
    }

    //checked when time out
    public void finalCheck(){
        updateSequences();
    }

    //Change the whole code matrix tiles' state in the Status ->codeMatrix
    //e.g. from AVAILABLE to SELECTED
    private void updateCodeMatrix(){
        MatrixCell[][] tmpGrid = status.getCodeMatrix();
        disableTiles(tmpGrid);
        tmpGrid[tileSelected[0]][tileSelected[1]]=new MatrixCell("[ ]");
        tmpGrid[tileSelected[0]][tileSelected[1]].setSelected(true);
        if (colAvailable) {
            colAvailable = false;
            for (MatrixCell[] matrixCells : tmpGrid)
                if (!matrixCells[tileSelected[1]].isAvailable())
                    matrixCells[tileSelected[1]].setAvailable(true);

        } else {
            colAvailable = true;
            for (int col = 0; col < tmpGrid[tileSelected[0]].length; col++)
                if (!tmpGrid[tileSelected[0]][col].isAvailable())
                    tmpGrid[tileSelected[0]][col].setAvailable(true);

        }
        if (bufferCount >= status.getBufferSize()) disableTiles(tmpGrid);
        status.setCodeMatrix(tmpGrid);

    }

    //ADD corresponding matrixCell in the buffer
    //update buffer in Status
    private void updateBuffer(){
        if (bufferCount < status.getBufferSize()) {
            List<String> tmpbuf = status.getBuffer();
            tmpbuf.set(bufferCount, status.getCodeMatrix()[tileSelected[0]][tileSelected[1]].getCode());
            status.setBuffer(tmpbuf);
            bufferCount += 1;
        }
    }

    //Two states need to change in Status:
    //Inside a sequence: matrixCell successively in the buffer-> state: ADDED
    //Sequence: check if can be marked as SUCCESS or FAIL
    private void updateSequences(){
        if(timeOut){
            gameFailed();
        }
        List<Daemon> tmpSeq = status.getDaemons();
        for(int i = 0; i < tmpSeq.size(); i++){
            if (!tmpSeq.get(i).isFailed() && !tmpSeq.get(i).isSucceeded()){
                if(status.getBuffer().get(bufferCount - 1).equals(tmpSeq.get(i).getSeq().get(bufferCount -1).getCode())) {
                    if(bufferCount >= 2 && tmpSeq.get(i).getSeq().get(bufferCount - 2).isAdded()) { //OLD SEQUENCE
                        tmpSeq.get(i).getSeq().get(bufferCount - 1).setAdded(true);
                        tmpSeq.get(i).getSeq().get(bufferCount - 2).setSelected(false);
                        tmpSeq.get(i).getSeq().get(bufferCount - 1).setSelected(true);
                    }
                    else{ //NEW SEQUENCE
                        tmpSeq.get(i).getSeq().get(bufferCount - 1).setAdded(true);
                        tmpSeq.get(i).getSeq().get(bufferCount - 1).setSelected(true);
                    }
                }
                else{
                    int emptyCount = 0;
                    for(int m = 0; m < bufferCount-1; m++) {
                        tmpSeq.get(i).getSeq().get(m).setAdded(false);
                        tmpSeq.get(i).getSeq().get(m).setSelected(false);
                        if(tmpSeq.get(i).getSeq().get(m).getCode().equals("")){
                            emptyCount += 1;
                        }
                    }
                    for(int n = 0; n < bufferCount-emptyCount-1; n++){
                        tmpSeq.get(i).addEmptyCell();
                    }
                    if(status.getBuffer().get(bufferCount - 1).equals(tmpSeq.get(i).getSeq().get(bufferCount -1).getCode())){
                        tmpSeq.get(i).getSeq().get(bufferCount-1).setAdded(true);
                        tmpSeq.get(i).getSeq().get(bufferCount-1).setSelected(true);
                    }
                    else{
                        tmpSeq.get(i).addEmptyCell();
                    }
                    if(tmpSeq.get(i).getSeq().size() > status.getBufferSize()){
                        tmpSeq.get(i).setFailed(true);
                    }
                }
                for(int j = 0; j < tmpSeq.get(i).getSeq().size(); j++){
                    tmpSeq.get(i).setSucceeded(tmpSeq.get(i).getSeq().get(j).isAdded());
                }
            }
        }
        status.setSequences(tmpSeq);
    }

    private void disableTiles(MatrixCell[][] grid) {
        for (MatrixCell[] matrixCells : grid)
            for (MatrixCell matrixCell : matrixCells) if (matrixCell.isAvailable()) matrixCell.setAvailable(false);

    }

    public void gameFailed(){
        List<Daemon> tmpSeq = status.getDaemons();
        for (Daemon sequence : tmpSeq) {
            if (!sequence.isFailed() && !sequence.isSucceeded()) {
                sequence.setFailed(true);
            }
        }
        status.setSequences(tmpSeq);
    }

    //Do Not modify this function!
    public void setTimeOut(){
        timeOut = true;
    }

    private boolean isDaemonsAllChecked(){
        List<Daemon> daemons = status.getDaemons();
        for (Daemon daemon : daemons) {
            if (!daemon.isFailed() && !daemon.isSucceeded())
                return false;
            }
        return true;
    }

    private void switchPuzzle(){
        status = new Status(new Puzzle(),status.getGameDifficulty(),status.getTimeLimit(),status.getScore());
        bufferCount = 0;
        colAvailable = true;
    }

    private void updateReward(){
        List<Daemon> daemons = status.getDaemons();
        for(Daemon daemon : daemons) {
            if (daemon.isFailed()){
                punishTime();
            }
            if (daemon.isSucceeded()){
                rewardTime();
            }
        }
    }

    private void rewardTime(){
        int time = status.getGameDifficulty().getTimeReward();
        status.addTimeLimit(time);
    }
    private void punishTime(){
        status.addTimeLimit(-5);
    }
}
