package game;

import entity.*;

import static entity.Sequence.FAIL;
import static entity.Sequence.SUCCESS;
import static entity.Tile.ADDED;

public class GameLogic {

    public int timeFlag = 0;
    private  int[] tileSelected = new int [2];
    private boolean timeOut = false;
    public Status state;

    public GameLogic(Status state){
        this.state = state;
    }

    public void setTileSelected(int[] tile) {
        this.tileSelected = tile;
    }

    public void updateState(){

        /////////////FIXME//////////////////////////
        state.buffer.set(0, state.codeMatrix[tileSelected[0]][tileSelected[1]].code);
        state.codeMatrix[tileSelected[0]][tileSelected[1]] = new Tile("[ ]",1);
        state.sequences.get(1).sequence.get(0).state = ADDED;
        state.sequences.get(0).state=SUCCESS;
        state.sequences.get(2).state=FAIL;
    }

    public void setTimeOut(){
        timeOut = true;
    }

}
