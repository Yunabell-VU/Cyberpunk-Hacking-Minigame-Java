package engine.UI;

import entity.Sequence;
import entity.Tile;
import game.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static entity.Sequence.*;
import static entity.Tile.*;



//Game UI.
//Things need to FIX : to separate Timer to another panel to avoid repaint with puzzles.

// DO NOT modify this file!

public class GamePanel extends JPanel {

    private final GameLogic gameLogic;
    private static final int TIMER_PERIOD = 1000;
    private int count;

    TextLabel countDownLabel = new TextLabel("", 50, 50);

    //init GamePanel
    public GamePanel(GameLogic logic) {
        gameLogic = logic;

        this.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
        drawGamingPanel();

    }

    private void drawGamingPanel(){
        this.add(drawTimerPanel());
        this.add(drawBuffer());
        this.add(drawCodeMatrix());
        this.add(drawSequence());
        this.add(drawScorePanel());
    }
    private void drawGamingOver(){
        removeAll();
        repaint();
        this.add(drawTimerPanel());
        this.add(drawBuffer());
        this.add(drawTimeOutPanel());
        this.add(drawSequence());
        this.add(drawScorePanel());
        revalidate();
    }

    //refresh the Panel
    private void repaintPuzzle() {
        removeAll();
        repaint();
        drawGamingPanel();
        revalidate();
    }

    private JPanel drawCodeMatrix(){

        Panel panel = new Panel(450,400);
        panel.setBorder(BorderFactory.createLineBorder(new Color(222,255,85),2,true));

        Panel titlePanel = new Panel(446, 25);
        titlePanel.setBackground(new Color(222,255,85));
        panel.add(titlePanel);

        TextLabel gridLabel = new TextLabel("CODE MATRIX",420,25);
        gridLabel.setFont(new Font("Consolas", Font.BOLD,20));
        gridLabel.setForeground(new Color(0,0,0,90));
        titlePanel.add(gridLabel);

        Panel codeMatrixPanel = new Panel(446, 360);
        panel.add(codeMatrixPanel);

        int matrixSpan = gameLogic.status.getMatrixSpan();
        final GridLayout gridLayout = new GridLayout(matrixSpan, matrixSpan);
        codeMatrixPanel.setLayout(gridLayout);

        JButton[][] buttons = new JButton[matrixSpan][matrixSpan];

        Tile[][] codeSource = gameLogic.status.getCodeMatrix();

        for (int row = 0; row < matrixSpan; row++) {
            for (int col = 0; col < matrixSpan; col++) {
                buttons[row][col] = new JButton(codeSource[row][col].getCode());

                buttons[row][col].setBackground(Color.BLACK);
                buttons[row][col].setPreferredSize(new Dimension(60, 60));
                buttons[row][col].setFont(new Font("Consolas",Font.BOLD,20));

                buttons[row][col].setBorderPainted(false);
                buttons[row][col].setForeground(new Color(222,255,85));

                int finalRow = row;
                int finalCol = col;

                if(codeSource[row][col].isSelected()){
                    buttons[row][col].setForeground(new Color(70,44,84));
                }

                if(codeSource[row][col].isAvailable()){
                    buttons[row][col].setBackground(new Color(41,44,57));
                    int[] tileSelected = new int [2];
                    if(!codeSource[row][col].isSelected()){
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
                            public void mouseEntered(MouseEvent e) {buttons[finalRow][finalCol].setForeground(new Color(95,245,255));}
                            @Override
                            public void mouseExited(MouseEvent e) {buttons[finalRow][finalCol].setForeground(new Color(222,255,85));}
                        });
                    }
                }

                codeMatrixPanel.add(buttons[row][col]);
            }
        }
        return panel;
    }

    private JPanel drawBuffer() {

        Panel panel = new Panel(550, 120);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));

        TextLabel bufferText = new TextLabel("BUFFER",550, 40);
        bufferText.setFont(new Font("Consolas",Font.BOLD,25));
        panel.add(bufferText);

        for (int i = 0; i < gameLogic.status.getBufferSize(); i++) {
            TextLabel label = new TextLabel(gameLogic.status.getBuffer().get(i),35, 35);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Consolas",Font.BOLD,18));
            label.setBorder(BorderFactory.createDashedBorder(new Color(222,255,85,90),12,5));
            panel.add(label);
        }
        return panel;
    }

    private JPanel drawSequence() {
        Panel panel = new Panel(550, 350);
        panel.setBorder(BorderFactory.createLineBorder(new Color(222,255,85),1));

        TextLabel seqTextLabel = new TextLabel(" SEQUENCE REQUIRED TO UPLOAD",550, 35);
        seqTextLabel.setFont(new Font("Consolas",Font.BOLD,20));
        seqTextLabel.setBorder(BorderFactory.createMatteBorder(0,0,2,0,new Color(222,255,85)));
        panel.add(seqTextLabel);

        ArrayList<Sequence> sequences = gameLogic.status.getSequences();

        for (Sequence sequence : sequences) {
            Panel p = new Panel(548, 65);
            panel.add(p);

            if(sequence.isSucceeded()){
                TextLabel label = new TextLabel(" SUCCEEDED",548, 55);
                label.setForeground(new Color(0,0,0,98));
                label.setOpaque(true);
                label.setBackground(new Color(27,213,117));
                label.setFont(new Font("Consolas",Font.BOLD,20));
                p.add(label);
            }
            if(sequence.isFailed()){
                TextLabel label = new TextLabel(" FAILED",548, 55);
                label.setForeground(new Color(0,0,0,98));
                label.setOpaque(true);
                label.setBackground(new Color(255,87,81));
                label.setFont(new Font("Consolas",Font.BOLD,20));
                p.add(label);
            }
            if(!sequence.isFailed() && !sequence.isSucceeded()){
                for (int j = 0; j < sequence.getSeq().size(); j++) {
                    TextLabel label = new TextLabel(sequence.getSeq().get(j).getCode(),50, 50);
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setFont(new Font("Consolas",Font.BOLD,20));
                    if (!sequence.getSeq().get(j).isAdded()) {label.setForeground(Color.WHITE);}
                    if (sequence.getSeq().get(j).isSelected()) {label.setBorder(BorderFactory.createLineBorder(new Color(222,255,85)));}
                    p.add(label);
                }
            }
        }

        return panel;
    }

    private JPanel drawScorePanel(){
        Panel panel = new Panel(900, 100);
        panel.setBackground(new Color(95,245,255,50));

        TextLabel textLabel = new TextLabel("SCORE PANEL TO BE IMPLEMENTED",900, 100);
        textLabel.setFont(new Font("Consolas",Font.BOLD,20));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setForeground(new Color(95,245,255));
        panel.add(textLabel);

        return panel;
    }

    private JPanel drawTimeOutPanel(){
        Panel panel = new Panel(450, 400);
        panel.setBackground(new Color(255,87,81,90));

        TextLabel textLabel = new TextLabel("TIME OUT",450, 400);
        textLabel.setFont(new Font("Consolas",Font.BOLD,50));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setForeground(new Color(255,87,81));
        panel.add(textLabel);

        return panel;
    }

    private JPanel drawTimerPanel(){
        Panel panel = new Panel(450, 100);

        TextLabel timeTextLabel = new TextLabel("TIME REMAINING",320, 100);
        timeTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeTextLabel.setFont(new Font("Consolas",Font.BOLD,35));
        panel.add(timeTextLabel);

        countDownLabel.setBorder(BorderFactory.createLineBorder(new Color(222,255,85)));
        countDownLabel.setFont(new Font("Consolas",Font.BOLD,35));
        countDownLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(countDownLabel);

        String text = (gameLogic.status.getCurrentCount() -count)+"";
        setCountDownLabelText(text);

        return panel;
    }

    //set Timer Label text
    private void setCountDownLabelText(String text) {countDownLabel.setText(text);}

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
                drawGamingOver();
            }
        }).start();
    }

}
