package game;

import entity.*;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {

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
    public void setTileSelected(int[] tile) {
        this.tileSelected = tile;
    }

    //
    public void updateState(){
        updateBuffer();
        updateCodeMatrix();
        updateSequences();

    }
    public void finalCheck(){
        updateSequences();
    }

    //Change the whole code matrix tiles' state in the Status ->codeMatrix
    //e.g. from AVAILABLE to SELECTED
    private void updateCodeMatrix(){
        Tile[][] tmpGrid = status.getCodeMatrix();
        disableTiles(tmpGrid);
        tmpGrid[tileSelected[0]][tileSelected[1]]=new Tile("[ ]");
        tmpGrid[tileSelected[0]][tileSelected[1]].setSelected(true);
        if (colAvailable) {
            colAvailable = false;
            for (Tile[] tiles : tmpGrid)
                if (!tiles[tileSelected[1]].isAvailable())
                    tiles[tileSelected[1]].setAvailable(true);

        } else {
            colAvailable = true;
            for (int col = 0; col < tmpGrid[tileSelected[0]].length; col++)
                if (!tmpGrid[tileSelected[0]][col].isAvailable())
                    tmpGrid[tileSelected[0]][col].setAvailable(true);

        }
        //if (bufferCount >= status.getBufferSize()) disableTiles(tmpGrid);
        status.setCodeMatrix(tmpGrid);

    }

    //ADD corresponding tile in the buffer
    //update buffer in Status
    private void updateBuffer(){
        if (bufferCount < status.getBufferSize()) {
            List<String> tmpbuf = status.getBuffer();
            tmpbuf.set(bufferCount, status.getCodeMatrix()[tileSelected[0]][tileSelected[1]].getCode());
            status.setBuffer(tmpbuf);
            bufferCount += 1;
        }
        else gameFailed();
    }

    //Two states need to change in Status:
    //Inside a sequence: tile successively in the buffer-> state: ADDED
    //Sequence: check if can be marked as SUCCESS or FAIL
    private void updateSequences(){
        List<Daemon> tmpSeq = status.getDaemons();
        for(int i = 0; i < tmpSeq.size(); i++){
            if (!tmpSeq.get(i).isFailed() && !tmpSeq.get(i).isSucceeded()){
                if(status.getBuffer().get(bufferCount - 1).equals(tmpSeq.get(i).getSeq().get(bufferCount -1).getCode())) {
                    if(!tmpSeq.get(i).getSeq().get(bufferCount - 1).isAdded()) {
                        tmpSeq.get(i).getSeq().get(bufferCount - 1).setAdded(true);
                    }
                    else{
                        tmpSeq.get(i).getSeq().get(bufferCount - 1).setAdded(true);
                        tmpSeq.get(i).getSeq().get(bufferCount - 2).setSelected(false);
                    }
                    tmpSeq.get(i).getSeq().get(bufferCount - 1).setSelected(true);
                }
                else{
                    int emptyCount = 0;
                    for(int m = 0; m < bufferCount-1; m++) {
                        tmpSeq.get(i).getSeq().get(m).setAdded(false);
                        tmpSeq.get(i).getSeq().get(m).setSelected(false);
                        if(tmpSeq.get(i).getSeq().get(m).getCode() == ""){
                            emptyCount += 1;
                        }
                    }
                    for(int n = 0; n < bufferCount-emptyCount; n++){
                        tmpSeq.get(i).addEmptyCell();
                    }
                    if(tmpSeq.get(i).getSeq().size() >= status.getBufferSize()){
                        gameFailed();
                    }
                }
                //一个sequence里面所有的都是added的则 succeed
                for(int j = 0; j < tmpSeq.get(i).getSeq().size(); j++){
                    tmpSeq.get(i).setSucceeded(tmpSeq.get(i).getSeq().get(j).isAdded());
                }
            }
        }
        status.setSequences(tmpSeq);
        //System.out.println("matrix span: "+ matrixSpan);
    }

    private void disableTiles(Tile[][] grid) {
        for (Tile[] tiles : grid)
            for (Tile tile : tiles) if (tile.isAvailable()) tile.setAvailable(false);

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
        gameFailed();
    }

}
