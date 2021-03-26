package command;

import game.Game;

import javax.swing.*;

public class BackToMenuCommand implements Command{
    private final Game game;
    private final JButton button;

    public
    BackToMenuCommand(Game game, JButton button){
        this.game = game;
        this.button = button;
    }

    //Player can switch from Game to Menu in either of the following situations:
    //1. Game is not started
    //2. Game is finished
    //During the game process, player shall not be able to return to the Menu
    @Override
    public boolean
    executable() {
        return game.isGameOver() || !game.isGameStarted();
    }

    @Override
    public void
    execute() {
        button.addActionListener(game.exitGame);
    }
}
