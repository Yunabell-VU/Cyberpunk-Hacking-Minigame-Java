package command;

import game.Game;

public class EndGameCommand implements Command{
    private final Game game;

    public
    EndGameCommand(Game game){
        this.game = game;
    }

    @Override
    public boolean
    executable() {
        return !game.isGameOver();
    }

    @Override
    public void
    execute() {
        game.setGameTimeToZero();
    }
}
