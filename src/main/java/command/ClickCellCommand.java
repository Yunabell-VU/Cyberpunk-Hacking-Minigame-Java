package command;

import entity.Coordinate;
import game.Game;

public class ClickCellCommand implements Command {
    private final Game game;
    private final Coordinate coordinate;

    public
    ClickCellCommand(Game game, Coordinate coordinate){
        this.game = game;
        this.coordinate = coordinate;
    }

    //only available matrix cells will be added with this command
    @Override
    public boolean
    executable() {
        return true;
    }

    @Override
    public void
    execute() {
        game.triggerGameTimer();
        game.saveAndUpdateStatus(coordinate);
        game.updatePanel();
    }

}
