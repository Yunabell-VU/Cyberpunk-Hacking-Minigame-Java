package game;

import entity.Puzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

//Game process control: initiate game frame, init status, init game
//DO NOT modify this file!

public class Engine extends JFrame {

    Difficulty gameDifficulty = new Difficulty();

    public Engine(String title) {
        super(title);
        getContentPane().add(new MenuPanel(startGame(), gameDifficulty), BorderLayout.CENTER);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setVisible(true);
        this.setResizable(false);
    }

    public ActionListener startGame() {
        return e -> initGame();
    }

    public ActionListener exitGame() {
        return e -> displayMenu();
    }

    public void initGame() {
        Status gameState = new Status(new Puzzle(), gameDifficulty, gameDifficulty.getInitTimeLimit(), 0);
        Game newGame = new Game(gameState);
        displayGamePanel(newGame);
    }

    public void displayGamePanel(Game game) {
        getContentPane().removeAll();
        getContentPane().repaint();

        GamePanel gamePanel = new GamePanel(game, exitGame());

        getContentPane().add(gamePanel, BorderLayout.CENTER);
        getContentPane().revalidate();
    }

    public void displayMenu() {
        getContentPane().removeAll();
        getContentPane().repaint();

        getContentPane().add(new MenuPanel(startGame(), gameDifficulty), BorderLayout.CENTER);
        getContentPane().revalidate();
    }

    public static void runGame() {
        new Engine("Cyberpunk Hacking - Infinity");
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Engine::runGame);
    }
}

