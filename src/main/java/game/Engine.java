package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class Engine {

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

    private void initGame() {
        Status firstStatus = new Status(gameDifficulty);
        Game game = new Game(firstStatus, exitGame());
        displayGamePanel(game);
    }

    private void displayGamePanel(Game game) {
        gameFrame.getContentPane().removeAll();
        gameFrame.getContentPane().repaint();
        gameFrame.getContentPane().add(game, BorderLayout.CENTER);
        gameFrame.getContentPane().revalidate();
    }

    private void displayMenu() {
        gameFrame.getContentPane().removeAll();
        gameFrame.getContentPane().repaint();
        gameFrame.getContentPane().add(new Menu(startGame(), gameDifficulty), BorderLayout.CENTER);
        gameFrame.getContentPane().revalidate();
    }

    private static void runGame() {
        new Engine();
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Engine::runGame);
    }
}

