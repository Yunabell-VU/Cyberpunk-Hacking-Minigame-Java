package game;

import engine.UI.GamePanel;
import entity.Puzzle;

import javax.swing.*;
import java.awt.*;

import static game.Setting.TIMELIMIT;


//Game process control: initiate game frame, init status, init game logic
//DO NOT modify this file!

public class Game {

    public static void runGame()
    {
        JFrame frame = new JFrame("CHM - Endless Challenge");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        Container contentPane = frame.getContentPane();

        //Set bufferSize here.
        int bufferOffset = 0;//FIXME

        Status gameState = new Status(new Puzzle(),bufferOffset,TIMELIMIT,0);

        GameLogic logic = new GameLogic(gameState);

        //Init Game Panel
        GamePanel gamePanel = new GamePanel(logic);
        gamePanel.setBackground(Color.BLACK);
        contentPane.add(gamePanel, BorderLayout.CENTER);

    }

    public static void main (String[] args){
        javax.swing.SwingUtilities.invokeLater(Game::runGame);
    }
}
