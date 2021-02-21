package game;

import entity.Puzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class Engine extends JFrame {

    private final Difficulty gameDifficulty = new Difficulty();

    public Engine(String title) {
        super(title);
        getContentPane().add(new Menu(startGame(), gameDifficulty), BorderLayout.CENTER);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setVisible(true);
        this.setResizable(false);
    }

    private ActionListener startGame() {
        return e -> initGame();
    }

    private ActionListener exitGame() {
        return e -> displayMenu();
    }

    private void initGame() {
        Status firstStatus = new Status(new Puzzle(), gameDifficulty);
        Game game = new Game(firstStatus, exitGame());
        displayGamePanel(game);
    }

    private void displayGamePanel(Game game) {
        getContentPane().removeAll();
        getContentPane().repaint();
        getContentPane().add(game, BorderLayout.CENTER);
        getContentPane().revalidate();
    }

    private void displayMenu() {
        getContentPane().removeAll();
        getContentPane().repaint();
        getContentPane().add(new Menu(startGame(), gameDifficulty), BorderLayout.CENTER);
        getContentPane().revalidate();
    }

    private static void runGame() {
        new Engine("Cyberpunk Hacking - Infinity");
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Engine::runGame);
    }
}

