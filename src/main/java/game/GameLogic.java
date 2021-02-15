package game;

import entity.*;

import java.util.ArrayList;

public class GameLogic {

    public int timeFlag = 0;
    private  int[] tileSelected = new int [2];
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
        updateCodeMatrix();
        updateBuffer();
        updateSequences();

        //Delete the the corresponding part you implemented
        /////////////FIXME//////////////////////////
        ArrayList<String> tmpbuf = status.getBuffer();
        tmpbuf.set(0, status.getCodeMatrix()[tileSelected[0]][tileSelected[1]].getCode());
        status.setBuffer(tmpbuf);

         Tile[][] tmpGrid = status.getCodeMatrix();
         tmpGrid[tileSelected[0]][tileSelected[1]]=new Tile("[]");
         tmpGrid[tileSelected[0]][tileSelected[1]].setSelected(true);
        tmpGrid[tileSelected[0]][tileSelected[1]].setAvailable(true);
         status.setCodeMatrix(tmpGrid);

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
    private void updateCodeMatrix(){}

    //ADD corresponding tile in the buffer
    //update buffer in Status
    private void updateBuffer(){}

    //Two states need to change in Status:
    //Inside a sequence: tile successively in the buffer-> state: ADDED
    //Sequence: check if can be marked as SUCCESS or FAIL
    private void updateSequences(){}

    //Do Not modify this function!
    public void setTimeOut(){
        timeOut = true;
    }

}
