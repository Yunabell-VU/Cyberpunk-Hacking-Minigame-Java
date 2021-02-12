package engine;

import entity.Tile;
import gamelogic.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static game.Game.AVAILABLE;
import static game.Game.INBUFFER;


//Puzzle UI implement. DO NOT change this file!

public class PuzzlePanel extends JPanel {

    private final GameLogic gameLogic;

    public PuzzlePanel(GameLogic logic) {
        gameLogic = logic;
        JPanel gridPanel = drawCodeMatrix();
        add(gridPanel);

        JPanel bufferPanel = drawBuffer();
        add(bufferPanel);

        JPanel seqPanel = drawSequence();
        add(seqPanel);
    }

    public void repaintCodeMatrix() {
        removeAll();
        repaint();
        JPanel gridPanel = drawCodeMatrix();
        add(gridPanel);
        JPanel bufferPanel = drawBuffer();
        add(bufferPanel);
        JPanel seqPanel = drawSequence();
        add(seqPanel);
        revalidate();
    }

    public JPanel drawCodeMatrix(){

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);

        final GridLayout gridLayout = new GridLayout(6, 6);
        panel.setLayout(gridLayout);

        JButton[][] buttons = new JButton[6][6];

        Tile[][] codeSource = gameLogic.currentPuzzle.codeMatrix;

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                buttons[row][col] = new JButton(codeSource[row][col].code);
                //style
                buttons[row][col].setBackground(Color.BLACK);
                buttons[row][col].setFont(new  java.awt.Font("Arial", Font.BOLD,  15));
                //border
                buttons[row][col].setBorderPainted(false);
                buttons[row][col].setForeground(Color.YELLOW);

                int finalRow = row;
                int finalCol = col;

                if(codeSource[row][col].state==AVAILABLE){
                    buttons[row][col].setBackground(Color.GRAY);
                    int[] selectedTile = new int [2];
                    buttons[row][col].addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if(gameLogic.timeFlag==0){
                                gameLogic.timeFlag = 1;
                                gameLogic.timer.start();
                            }

                            selectedTile[0] = finalRow;
                            selectedTile[1] = finalCol;
                            System.out.println("("+selectedTile[0]+","+selectedTile[1]+")");
                            gameLogic.updateState(selectedTile);
                            repaintCodeMatrix();
                        }
                        @Override
                        public void mousePressed(MouseEvent e) {}
                        @Override
                        public void mouseReleased(MouseEvent e) {}
                        @Override
                        public void mouseEntered(MouseEvent e) {}
                        @Override
                        public void mouseExited(MouseEvent e) {
                        }
                    });
                }

                panel.add(buttons[row][col]);
            }
        }
        return panel;
    }

    public JPanel drawBuffer() {

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        add(panel);
        final GridLayout gridLayout = new GridLayout(1, 0);
        panel.setLayout(gridLayout);
        gridLayout.setHgap(5);
        gridLayout.setVgap(5);

        for (int i = 0; i < gameLogic.currentPuzzle.bufferSize; i++) {
            JLabel label = new JLabel();
            label.setForeground(Color.YELLOW);
            label.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
            label.setText(gameLogic.currentPuzzle.buffer.get(i));
            panel.add(label);
        }
        return panel;
    }

    public JPanel drawSequence() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        add(panel);
        final GridLayout gridLayout = new GridLayout(4, 0);
        panel.setLayout(gridLayout);
        gridLayout.setHgap(5);
        gridLayout.setVgap(5);

        ArrayList<ArrayList<Tile>> sequences = gameLogic.currentPuzzle.sequences;

        for (ArrayList<Tile> tiles : sequences) {
            JPanel p = new JPanel();
            p.setBackground(Color.BLACK);
            panel.add(p);
            final GridLayout gridLayout2 = new GridLayout(1, 0);
            gridLayout2.setHgap(5);
            gridLayout2.setVgap(5);

            for (Tile tile : tiles) {
                JLabel label = new JLabel();
                label.setForeground(Color.WHITE);
                //label.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
                if (tile.state == INBUFFER) {
                    label.setForeground(Color.YELLOW);
                    label.setBorder(BorderFactory.createLineBorder(Color.CYAN));
                }
                label.setText(tile.code);
                p.add(label);
            }
        }

        return panel;
    }
}
