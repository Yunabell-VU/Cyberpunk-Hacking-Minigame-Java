package graphics;

import entity.Sequence;
import entity.Tile;
import game.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


//Game UI.
// DO NOT modify this file!

public class GamePanel extends JPanel {

    private final GameLogic gameLogic;
    private static final int TIMER_PERIOD = 1000;
    private int count;
    TextLabel countDownLabel = new TextLabel("", 50, 50);
    private JPanel backgroundPanel;

    //init GamePanel
    public GamePanel(GameLogic logic) {

        gameLogic = logic;
        this.setBackground(Color.BLACK);

        Image image = new ImageIcon("src/main/java/image/gamePanel2.jpg").getImage();
        backgroundPanel = new BackgroundPanel(image);
        backgroundPanel.setLayout(null);
        backgroundPanel.setPreferredSize(new Dimension(1200, 800));

        add(backgroundPanel);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        drawGamingPanel();
    }

    private void drawGamingPanel() {
        backgroundPanel.add(drawTimerPanel());
        backgroundPanel.add(drawBuffer());
        backgroundPanel.add(drawCodeMatrix());
        backgroundPanel.add(drawSequence());
        backgroundPanel.add(drawScorePanel());
    }

    private void drawGameOver() {
        backgroundPanel.removeAll();
        backgroundPanel.repaint();
        drawGamingPanel();
        backgroundPanel.add(drawTimeOutPanel());
        backgroundPanel.revalidate();
    }

    //refresh the Panel
    private void repaintPuzzle() {
        backgroundPanel.removeAll();
        backgroundPanel.repaint();
        drawGamingPanel();
        backgroundPanel.revalidate();
    }

    private JPanel drawCodeMatrix() {

        JPanel panel = new JPanel();
        panel.setBounds(90,195,449,360);
        panel.setOpaque(false);

        int matrixSpan = gameLogic.status.getMatrixSpan();
        final GridLayout gridLayout = new GridLayout(matrixSpan, matrixSpan);
        panel.setLayout(gridLayout);

        Tile[][] codeSource = gameLogic.status.getCodeMatrix();

        for (int row = 0; row < matrixSpan; row++) {
            for (int col = 0; col < matrixSpan; col++) {
                Button button = drawMatrixCell(codeSource[row][col], row, col);
                panel.add(button);
            }
        }
        return panel;
    }

    private Button drawMatrixCell(Tile tile, int row, int col) {
        Button button = new Button(tile.getCode());
        if (tile.isSelected())
            button.setForeground(new Color(70, 44, 84));

        if (tile.isAvailable()) {
            button.setBackground(new Color(41, 44, 57));

            if (!tile.isSelected())
                addClickEvent(button, row, col);
        }
        return button;
    }

    private JPanel drawBuffer() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBounds(555,70,550,120);
        panel.setOpaque(false);
        for (int i = 0; i < gameLogic.status.getBufferSize(); i++) {
            TextLabel label = new TextLabel(gameLogic.status.getBuffer().get(i), 35, 35);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Consolas", Font.BOLD, 18));
            label.setBorder(BorderFactory.createDashedBorder(new Color(250, 247, 10, 90), 12, 5));
            panel.add(label);
        }
        return panel;
    }

    private JPanel drawSequence() {
        JPanel panel = new JPanel();
        panel.setBounds(560,200,550,350);
        panel.setOpaque(false);

        ArrayList<Sequence> sequences = gameLogic.status.getSequences();

        for (Sequence sequence : sequences) {
            JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            p.setOpaque(false);
            p.setPreferredSize(new Dimension(548, 65));
            panel.add(p);

            if (sequence.isSucceeded()) {
                TextLabel label = new TextLabel(" SUCCEEDED", 548, 55);
                label.setForeground(new Color(0, 0, 0, 98));
                label.setOpaque(true);
                label.setBackground(new Color(27, 213, 117));
                label.setFont(new Font("Consolas", Font.BOLD, 20));
                p.add(label);
            }
            if (sequence.isFailed()) {
                TextLabel label = new TextLabel(" FAILED", 548, 55);
                label.setForeground(new Color(0, 0, 0, 98));
                label.setOpaque(true);
                label.setBackground(new Color(255, 87, 81));
                label.setFont(new Font("Consolas", Font.BOLD, 20));
                p.add(label);
            }
            if (!sequence.isFailed() && !sequence.isSucceeded()) {
                for (int j = 0; j < sequence.getSeq().size(); j++) {
                    TextLabel label = new TextLabel(sequence.getSeq().get(j).getCode(), 40, 40);
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setFont(new Font("Consolas", Font.BOLD, 18));
                    if (!sequence.getSeq().get(j).isAdded()) {
                        label.setForeground(Color.WHITE);
                    }
                    if (sequence.getSeq().get(j).isSelected()) {
                        label.setBorder(BorderFactory.createLineBorder(new Color(222, 255, 85)));
                    }
                    p.add(label);
                }
            }
        }

        return panel;
    }

    private JPanel drawScorePanel() {
       JPanel panel = new JPanel();
       return panel;
    }

    private JPanel drawTimeOutPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(450,400));
        panel.setBackground(new Color(255, 87, 81, 90));

        TextLabel textLabel = new TextLabel("TIME OUT", 450, 400);
        textLabel.setFont(new Font("Consolas", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setForeground(new Color(255, 87, 81));
        panel.add(textLabel);

        return panel;
    }

    private JPanel drawTimerPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);

        panel.setBounds(432, 65, 55, 55);
        countDownLabel.setBorder(BorderFactory.createLineBorder(new Color(250, 247, 10)));
        countDownLabel.setFont(new Font("Consolas", Font.BOLD, 35));
        countDownLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(countDownLabel);

        String text = (gameLogic.status.getCurrentCount() - count) + "";
        setCountDownLabelText(text);

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
                String text = (gameLogic.status.getCurrentCount() - count) + "";
                setCountDownLabelText(text);
            } else {
                ((Timer) e.getSource()).stop();
                gameLogic.setTimeOut();
                gameLogic.finalCheck();
                drawGameOver();
            }
        }).start();
    }

    private void addClickEvent(Button button, int row, int col) {
        int[] tileSelected = new int[2];
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameLogic.timeFlag == 0) {
                    gameLogic.timeFlag = 1;
                    start();
                }

                tileSelected[0] = row;
                tileSelected[1] = col;
                gameLogic.setTileSelected(tileSelected);
                gameLogic.updateState();
                repaintPuzzle();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(95, 245, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(new Color(222, 255, 85));
            }
        });
    }

}
