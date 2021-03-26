package command;

import game.Game;

public class EndGameCommand implements Command{
    private final Game game;

    public
    EndGameCommand(Game game){
        this.game = game;
    }

    //This command shall only works when the game is started and not finished.
    @Override
    public boolean
    executable() {
        return !game.isGameOver()&&game.isGameStarted();
    }

    @Override
    public void
    execute() {
        game.setGameTimeToZero();
    }
}
