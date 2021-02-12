package game;

import engine.Puzzle;
import engine.PuzzlePanel;
import engine.TimerPanel;
import gamelogic.GameLogic;

import javax.swing.*;
import java.awt.*;


//Game process control: initiate game frame, handle events, set Timer
public class Game {
    public final static int AVAILABLE = 1111;
    public final static int UNAVAILABLE = 2222;
    public final static int SELECTED = 3333;
    public final static int WAITING = 4444;
    public final static int INBUFFER = 5555;

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

        TimerPanel timePanel = new TimerPanel();
        //GameLogic with new Puzzle
        GameLogic logic = new GameLogic(new Puzzle(bufferOffset),timePanel);

        //Add Timer Panel
        frame.getContentPane().add(timePanel,BorderLayout.NORTH);
        //Init Puzzle Panel
        PuzzlePanel puzzlePanel = new PuzzlePanel(logic);
        frame.getContentPane().add(puzzlePanel, BorderLayout.WEST);

    }

    public static void main (String[] args){
        javax.swing.SwingUtilities.invokeLater(Game::runGame);
    }
}
