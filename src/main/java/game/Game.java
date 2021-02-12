package game;

import engine.GamePanel;
import entity.Puzzle;

import javax.swing.*;
import java.awt.*;


//Game process control: initiate game frame, handle events, set Timer
public class Game {

    public static void runGame()
    {
        JFrame frame = new JFrame("CHM - Endless Challenge");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);

        //Set bufferSize here.
        int bufferOffset = 0;//FIXME

        Status gameState = new Status(new Puzzle(),bufferOffset,15,0);

        GameLogic logic = new GameLogic(gameState);

        //Init Game Panel
        GamePanel gamePanel = new GamePanel(logic);
        frame.getContentPane().add(gamePanel, BorderLayout.CENTER);
    }

    public static void main (String[] args){
        javax.swing.SwingUtilities.invokeLater(Game::runGame);
    }
}
