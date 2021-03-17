package game;

import graphics.Redraw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class Engine implements Redraw {

    private final Difficulty gameDifficulty = new Difficulty();
    private final JFrame gameFrame = new JFrame("Cyberpunk Hacking - Infinity");

    public Engine() {
        initGameFrame();
        gameFrame.getContentPane().add(new Menu(startGame(), gameDifficulty), BorderLayout.CENTER);
    }

    private void initGameFrame(){
        gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gameFrame.setSize(1200, 800);
        gameFrame.setVisible(true);
        gameFrame.setResizable(false);
    }

    private ActionListener startGame() {
        return e -> initGame();
    }

    private ActionListener exitGame() {
        return e -> displayMenu();
    }

    public void initGame() {
        Status firstStatus = new Status(gameDifficulty);
        Game game = new Game(firstStatus, exitGame());
        displayGamePanel(game);
    }

    private void displayGamePanel(Game game) { Redraw.redraw(gameFrame.getContentPane(),game); }

    private void displayMenu() { Redraw.redraw(gameFrame.getContentPane(),new Menu(startGame(), gameDifficulty)); }

    private static void runGame() { new Engine(); }

    public static void main(String[] args) { javax.swing.SwingUtilities.invokeLater(Engine::runGame); }
}

