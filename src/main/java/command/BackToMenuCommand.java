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

    @Override
    public boolean
    executable() {
        return game.isGameOver();
    }

    @Override
    public void
    execute() {
        button.addActionListener(game.exitGame);
    }
}
