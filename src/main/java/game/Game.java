package game;

import entity.Puzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Game extends JFrame {

    Difficulty gameDifficulty= new Difficulty();

    public Game(String title){
        super(title);
        getContentPane().add(new MenuPanel(startGame(),gameDifficulty), BorderLayout.CENTER);
    }

    public ActionListener startGame(){
        return e -> initGame();
    }

    public ActionListener exitGame(){
        return e -> backToMenu();
    }

    public void initGame(){

        getContentPane().removeAll();
        getContentPane().repaint();

        Status gameState = new Status(new Puzzle(),gameDifficulty);
        GameLogic logic = new GameLogic(gameState);

        //Init Game Panel
        GamePanel gamePanel = new GamePanel(logic, exitGame());

        getContentPane().add(gamePanel, BorderLayout.CENTER);
        getContentPane().revalidate();
    }

    public void backToMenu(){
        getContentPane().removeAll();
        getContentPane().repaint();

        getContentPane().add(new MenuPanel(startGame(),gameDifficulty), BorderLayout.CENTER);
        getContentPane().revalidate();
    }

}
