package game;

import entity.Puzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

//Game process control: initiate game frame, init status, init game
//DO NOT modify this file!

public class Engine extends JFrame {

    private Difficulty gameDifficulty = new Difficulty();

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
        Status gameState = new Status(new Puzzle(), gameDifficulty, gameDifficulty.getInitTimeLimit(), 0);
        StatusHandler newStatusHandler = new StatusHandler(gameState);
        displayGamePanel(newStatusHandler);
    }

    private void displayGamePanel(StatusHandler statusHandler) {
        getContentPane().removeAll();
        getContentPane().repaint();

        Game game = new Game(statusHandler, exitGame());

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

