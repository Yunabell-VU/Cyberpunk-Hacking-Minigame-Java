package game;

import entity.Puzzle;
import graphics.GamePanel;
import graphics.MenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static game.Settings.TIMELIMIT;

public class GameFrame  extends JFrame {

    GameLogic gameLogic;
    Container contentPane;

    public GameFrame(String title){
        super(title);

        this.setSize(1200, 800);
        this.setVisible(true);
        this.setResizable(false);
        contentPane = this.getContentPane();

        JPanel p1 = new MenuPanel(startGame());
        p1.setBorder(null);
        contentPane.add(p1, BorderLayout.CENTER);

    }
    public ActionListener startGame(){
        return e -> initGame();
    }


    public void initGame(){

        contentPane.removeAll();
        contentPane.repaint();
        Status gameState = new Status(new Puzzle(),TIMELIMIT, 0);

        GameLogic logic = new GameLogic(gameState);

        //Init Game Panel
        GamePanel gamePanel = new GamePanel(logic);
        contentPane.add(gamePanel, BorderLayout.CENTER);
        contentPane.revalidate();
    }

}
