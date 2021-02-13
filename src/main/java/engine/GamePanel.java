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
import static game.Setting.MAX_SEQ_NUM;


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

        FlowLayout layout = (FlowLayout) getLayout();
        layout.setHgap(20);
        layout.setVgap(20);
        JPanel timePanel = drawTimerPanel();
        add(timePanel);

        JPanel bufferPanel = drawBuffer();
        add(bufferPanel);

        JPanel gridPanel = drawCodeMatrix();
        add(gridPanel);

        JPanel seqPanel = drawSequence();
        add(seqPanel);

        JPanel scorePanel = drawScorePanel();
        add(scorePanel);
    }

    //refresh the Panel
    private void repaintPuzzle() {
        removeAll();
        repaint();
        JPanel timePanel = drawTimerPanel();
        add(timePanel);
        JPanel bufferPanel = drawBuffer();
        add(bufferPanel);
        JPanel gridPanel = drawCodeMatrix();
        add(gridPanel);
        JPanel seqPanel = drawSequence();
        add(seqPanel);
        JPanel scorePanel = drawScorePanel();
        add(scorePanel);
        revalidate();
    }

    private JPanel drawCodeMatrix(){

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        panel.setBorder(BorderFactory.createLineBorder(new Color(222,255,85),2,true));
        panel.setBackground(Color.BLACK);

        panel.setPreferredSize(new Dimension(450, 400));
        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(446, 25));
        titlePanel.setBackground(new Color(222,255,85));
        titlePanel.setBorder(null);
        panel.add(titlePanel);

        JLabel gridLabel = new JLabel("CODE MATRIX",SwingConstants.LEFT);
        gridLabel.setFont(new Font("Consolas", Font.BOLD,20));
        gridLabel.setPreferredSize(new Dimension(420, 25));
        gridLabel.setForeground(new Color(0,0,0,90));
        titlePanel.add(gridLabel);

        JPanel codeMatrixPanel = new JPanel();
        codeMatrixPanel.setPreferredSize(new Dimension(446, 360));
        codeMatrixPanel.setBorder(null);
        codeMatrixPanel.setBackground(Color.BLACK);
        panel.add(codeMatrixPanel);

        int matrixSpan = gameLogic.status.getMatrixSpan();
        final GridLayout gridLayout = new GridLayout(matrixSpan, matrixSpan);
        codeMatrixPanel.setLayout(gridLayout);


        JButton[][] buttons = new JButton[matrixSpan][matrixSpan];

        Tile[][] codeSource = gameLogic.status.getCodeMatrix();

        for (int row = 0; row < matrixSpan; row++) {
            for (int col = 0; col < matrixSpan; col++) {
                buttons[row][col] = new JButton(codeSource[row][col].getCode());
                //style
                buttons[row][col].setBackground(Color.BLACK);
                buttons[row][col].setPreferredSize(new Dimension(60, 60));
                buttons[row][col].setFont(new Font("Consolas",Font.BOLD,20));
                //border
                buttons[row][col].setBorderPainted(false);
                buttons[row][col].setForeground(new Color(222,255,85));

                int finalRow = row;
                int finalCol = col;

                if(codeSource[row][col].getState()==SELECTED){
                    buttons[row][col].setForeground(new Color(70,44,84));
                }

                if(codeSource[row][col].getState()==AVAILABLE){
                    buttons[row][col].setBackground(new Color(41,44,57));
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

                codeMatrixPanel.add(buttons[row][col]);
            }
        }
        return panel;
    }

    private JPanel drawBuffer() {

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setPreferredSize(new Dimension(550, 120));

        JLabel bufferText = new JLabel("BUFFER",SwingConstants.LEFT);
        bufferText.setFont(new Font("Consolas",Font.BOLD,25));
        bufferText.setPreferredSize(new Dimension(550, 40));
        bufferText.setForeground(new Color(222,255,85));
        panel.add(bufferText);

        panel.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));

        for (int i = 0; i < gameLogic.status.getBufferSize(); i++) {
            JLabel label = new JLabel(gameLogic.status.getBuffer().get(i),SwingConstants.CENTER);
            label.setPreferredSize(new Dimension(35, 35));
            label.setForeground(new Color(222,255,85));
            label.setFont(new Font("Consolas",Font.BOLD,18));
            label.setBorder(BorderFactory.createDashedBorder(new Color(222,255,85,90),12,5));
            panel.add(label);
        }
        return panel;
    }

    private JPanel drawSequence() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createLineBorder(new Color(222,255,85),1));
        panel.setBackground(Color.BLACK);
        panel.setPreferredSize(new Dimension(550, 350));

        JLabel seqTextLabel = new JLabel(" SEQUENCE REQUIRED TO UPLOAD",SwingConstants.LEFT);
        seqTextLabel.setFont(new Font("Consolas",Font.BOLD,20));
        seqTextLabel.setPreferredSize(new Dimension(550, 30));
        seqTextLabel.setForeground(new Color(222,255,85));
        seqTextLabel.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(222,255,85)));
        panel.add(seqTextLabel);

        ArrayList<Sequence> sequences = gameLogic.status.getSequences();

        for (Sequence sequence : sequences) {
            JPanel p = new JPanel();
            p.setBackground(Color.BLACK);
            p.setPreferredSize(new Dimension(548, 65));
            p.setLayout(new FlowLayout(FlowLayout.LEFT));
            panel.add(p);

            if(sequence.getState()==SUCCESS){
                JLabel label = new JLabel(" SUCCEEDED",SwingConstants.LEFT);
                label.setForeground(new Color(0,0,0,98));
                label.setOpaque(true);
                label.setBackground(new Color(27,213,117));
                label.setPreferredSize(new Dimension(548, 65));
                label.setFont(new Font("Consolas",Font.BOLD,20));
                p.add(label);
            }
            else if(sequence.getState()==FAIL){
                JLabel label = new JLabel(" FAILED",SwingConstants.LEFT);
                label.setForeground(new Color(0,0,0,98));
                label.setOpaque(true);
                label.setPreferredSize(new Dimension(548, 65));
                label.setBackground(new Color(255,87,81));
                label.setFont(new Font("Consolas",Font.BOLD,20));
                p.add(label);
            }
            else{
                for (int j = 0; j < sequence.getSeq().size(); j++) {
                    JLabel label = new JLabel(sequence.getSeq().get(j).getCode(),SwingConstants.CENTER);

                    label.setPreferredSize(new Dimension(50, 50));
                    label.setForeground(Color.WHITE);
                    if (sequence.getSeq().get(j).getState()==SELECTED) {
                        label.setForeground(new Color(222,255,85));
                        label.setBorder(BorderFactory.createLineBorder(new Color(222,255,85)));
                    }
                    if (sequence.getSeq().get(j).getState()==ADDED) {
                        label.setForeground(new Color(222,255,85));
                    }
                    label.setFont(new Font("Consolas",Font.BOLD,20));
                    p.add(label);
                }
            }
        }

        return panel;
    }

    private JPanel drawScorePanel(){
        JPanel panel = new JPanel();

        panel.setPreferredSize(new Dimension(900, 100));
        panel.setBackground(new Color(95,245,255,50));

        JLabel textLabel = new JLabel("SCORE PANEL TO BE IMPLEMENTED",SwingConstants.CENTER);
        textLabel.setFont(new Font("Consolas",Font.BOLD,20));
        textLabel.setPreferredSize(new Dimension(900, 100));
        textLabel.setForeground(new Color(95,245,255));
        panel.add(textLabel);

        return panel;
    }

    private JPanel drawTimerPanel(){
        JPanel panel = new JPanel();
        //panel.setBorder(BorderFactory.createLineBorder(Color.RED));
        panel.setPreferredSize(new Dimension(450, 100));
        panel.setBackground(Color.BLACK);

        JLabel timeTextLabel = new JLabel("TIME REMAINING",SwingConstants.LEFT);
        timeTextLabel.setFont(new Font("Consolas",Font.BOLD,35));
        timeTextLabel.setPreferredSize(new Dimension(300, 100));
        timeTextLabel.setForeground(new Color(222,255,85));
        panel.add(timeTextLabel);

        countDownLabel.setBorder(BorderFactory.createLineBorder(new Color(222,255,85)));
        countDownLabel.setPreferredSize(new Dimension(50, 50));
        panel.add(countDownLabel);
        String text = (gameLogic.status.getCurrentCount() -count)+"";
        setCountDownLabelText(text);

        countDownLabel.setForeground(new Color(222,255,85));
        countDownLabel.setFont(new Font("Consolas",Font.BOLD,35));

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
