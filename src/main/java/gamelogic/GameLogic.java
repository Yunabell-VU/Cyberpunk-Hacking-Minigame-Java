package gamelogic;

import engine.Puzzle;
import engine.TimerPanel;
import entity.Tile;

import static game.Game.INBUFFER;

public class GameLogic {

    public Puzzle currentPuzzle;
    public int timeFlag = 0;
    public TimerPanel timer;

    public GameLogic(Puzzle puzzle, TimerPanel timerPanel){
        currentPuzzle = puzzle;
        timer = timerPanel;
    }

    public void updateState(int[] selectedTile){
        /////////////FIXME//////////////////////////
        currentPuzzle.buffer.set(0, currentPuzzle.codeMatrix[selectedTile[0]][selectedTile[1]].code);
        currentPuzzle.codeMatrix[selectedTile[0]][selectedTile[1]] = new Tile("[ ]",1);
        currentPuzzle.sequences.get(1).get(0).state = INBUFFER;
    }

}
