package game;

import entity.*;

import java.util.ArrayList;

public class GameLogic {

    public int timeFlag = 0;
    private  int[] tileSelected = new int [2];
    private int bufferCount = 0;
    private boolean colAvailable = true;
    private boolean timeOut = false;
    public Status status;

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

        //Delete the the corresponding part you implemented
        /////////////FIXME//////////////////////////
        ArrayList<Sequence> tmpSeq = status.getSequences();
        tmpSeq.get(1).getSeq().get(1).setAdded(true);
        tmpSeq.get(1).getSeq().get(1).setSelected(true);
        tmpSeq.get(1).getSeq().get(0).setAdded(true);
        tmpSeq.get(0).setSucceeded(true);
        tmpSeq.get(2).setFailed(true);
        status.setSequences(tmpSeq);
        ///////////////////////////////////////////////
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
        if (bufferCount >= status.getBufferSize()) disableTiles(tmpGrid);
        status.setCodeMatrix(tmpGrid);

    }

    //ADD corresponding tile in the buffer
    //update buffer in Status
    private void updateBuffer(){
        if (bufferCount < status.getBufferSize()) {
            ArrayList<String> tmpbuf = status.getBuffer();
            tmpbuf.set(bufferCount, status.getCodeMatrix()[tileSelected[0]][tileSelected[1]].getCode());
            status.setBuffer(tmpbuf);
            bufferCount += 1;
        }
    }

    //Two states need to change in Status:
    //Inside a sequence: tile successively in the buffer-> state: ADDED
    //Sequence: check if can be marked as SUCCESS or FAIL
    private void updateSequences(){}

    private void disableTiles(Tile[][] grid) {
        for (Tile[] tiles : grid)
            for (Tile tile : tiles) if (tile.isAvailable()) tile.setAvailable(false);

    }

    //Do Not modify this function!
    public void setTimeOut(){
        timeOut = true;
    }

}
