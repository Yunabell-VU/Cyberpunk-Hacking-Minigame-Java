package engine;

import entity.Sequence;
import entity.Tile;
import game.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static entity.Sequence.FAIL;
import static entity.Sequence.SUCCESS;
import static entity.Tile.*;


//Game UI.
//Things need to FIX : to separate Timer to another panel to avoid repaint with puzzles.

// DO NOT modify this file!

public class GamePanel extends JPanel {

    private final GameLogic gameLogic;
    private static final int TIMER_PERIOD = 1000;
    private int count;

    JLabel countDownLabel = new JLabel("", SwingConstants.CENTER);

    //init GamePanel
    public GamePanel(GameLogic logic) {
        gameLogic = logic;

        JPanel timePanel = drawTimerPanel();
        add(timePanel);

        JPanel gridPanel = drawCodeMatrix();
        add(gridPanel);

        JPanel bufferPanel = drawBuffer();
        add(bufferPanel);

        JPanel seqPanel = drawSequence();
        add(seqPanel);
    }

    //refresh the Panel
    private void repaintPuzzle() {
        removeAll();
        repaint();
        JPanel timePanel = drawTimerPanel();
        add(timePanel);
        JPanel gridPanel = drawCodeMatrix();
        add(gridPanel);
        JPanel bufferPanel = drawBuffer();
        add(bufferPanel);
        JPanel seqPanel = drawSequence();
        add(seqPanel);
        revalidate();
    }

    private JPanel drawCodeMatrix(){

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);

        int matrixSpan = gameLogic.status.getMatrixSpan();

        final GridLayout gridLayout = new GridLayout(matrixSpan, matrixSpan);
        panel.setLayout(gridLayout);

        JButton[][] buttons = new JButton[matrixSpan][matrixSpan];

        Tile[][] codeSource = gameLogic.status.getCodeMatrix();

        for (int row = 0; row < matrixSpan; row++) {
            for (int col = 0; col < matrixSpan; col++) {
                buttons[row][col] = new JButton(codeSource[row][col].getCode());
                //style
                buttons[row][col].setBackground(Color.BLACK);
                buttons[row][col].setFont(new  java.awt.Font("Arial", Font.BOLD,  15));
                //border
                buttons[row][col].setBorderPainted(false);
                buttons[row][col].setForeground(Color.YELLOW);

                int finalRow = row;
                int finalCol = col;

                if(codeSource[row][col].getState()==AVAILABLE){
                    buttons[row][col].setBackground(Color.GRAY);
                    int[] tileSelected = new int [2];
                    buttons[row][col].addMouseListener(new MouseListener() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if(gameLogic.timeFlag==0){
                                gameLogic.timeFlag = 1;
                                start();
                            }

                            tileSelected[0] = finalRow;
                            tileSelected[1] = finalCol;
                            System.out.println("("+tileSelected[0]+","+tileSelected[1]+")");
                            gameLogic.setTileSelected(tileSelected);
                            gameLogic.updateState();
                            repaintPuzzle();
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

    private JPanel drawBuffer() {

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);

        final GridLayout gridLayout = new GridLayout(1, 0);
        panel.setLayout(gridLayout);
        gridLayout.setHgap(5);
        gridLayout.setVgap(5);

        for (int i = 0; i < gameLogic.status.getBufferSize(); i++) {
            JLabel label = new JLabel();
            label.setForeground(Color.YELLOW);
            label.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
            label.setText(gameLogic.status.getBuffer().get(i));
            panel.add(label);
        }
        return panel;
    }

    private JPanel drawSequence() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);

        final GridLayout gridLayout = new GridLayout(4, 0);
        panel.setLayout(gridLayout);
        gridLayout.setHgap(5);
        gridLayout.setVgap(5);

        ArrayList<Sequence> sequences = gameLogic.status.getSequences();

        for (Sequence sequence : sequences) {
            JPanel p = new JPanel();
            p.setBackground(Color.BLACK);
            panel.add(p);
            final GridLayout gridLayout2 = new GridLayout(1, 0);
            gridLayout2.setHgap(5);
            gridLayout2.setVgap(5);

            if(sequence.getState()==SUCCESS){
                JLabel label = new JLabel();
                label.setForeground(Color.BLACK);
                label.setOpaque(true);
                label.setBackground(Color.GREEN);
                label.setText("SUCCESS");
                label.setFont(new Font(Font.DIALOG,Font.BOLD,9));
                panel.add(label);
            }
            else if(sequence.getState()==FAIL){
                JLabel label = new JLabel();
                label.setForeground(Color.BLACK);
                label.setText("FAIL");
                label.setOpaque(true);
                label.setBackground(Color.RED);
                label.setFont(new Font(Font.DIALOG,Font.BOLD,9));
                panel.add(label);
            }
            else{
                for (int j = 0; j < sequence.getSeq().size(); j++) {
                    JLabel label = new JLabel();
                    label.setForeground(Color.WHITE);
                    if (sequence.getSeq().get(j).getState()==ADDED) {
                        label.setForeground(Color.YELLOW);
                        label.setBorder(BorderFactory.createLineBorder(Color.CYAN));
                    }
                    label.setText(sequence.getSeq().get(j).getCode());
                    p.add(label);
                }
            }
        }

        return panel;
    }
    private JPanel drawTimerPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);

        panel.add(countDownLabel);
        String text = (gameLogic.status.getCurrentCount() -count)+"";
        setCountDownLabelText(text);

        countDownLabel.setForeground(Color.YELLOW);
        countDownLabel.setFont(new Font(Font.DIALOG,Font.BOLD,14));

        return panel;
    }

    //set Timer Label text
    private void setCountDownLabelText(String text) {
        countDownLabel.setText(text);
    }

    //Timer event
    private void start() {
        new Timer(TIMER_PERIOD, e -> {
            if (count < gameLogic.status.getCurrentCount()) {
                count++;
                String text = (gameLogic.status.getCurrentCount() -count)+"";
                setCountDownLabelText(text);
            } else {
                ((Timer) e.getSource()).stop();
                gameLogic.setTimeOut();
                gameLogic.updateState();
                repaintPuzzle();
            }
        }).start();
    }
}
