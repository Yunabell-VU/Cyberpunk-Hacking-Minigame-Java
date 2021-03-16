package command;

import entity.Coordinate;
import game.Game;

public class UndoCommand implements Command{
    private final Game game;

    public UndoCommand(Game game){
        this.game = game;
    }
    @Override
    public boolean executable() {
        return game.canUndo();
    }

    @Override
    public void execute() {
        game.undo();
    }
}
