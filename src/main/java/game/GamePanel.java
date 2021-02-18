package game;

import entity.Daemon;
import entity.DaemonCell;
import entity.MatrixCell;
import graphics.*;
import graphics.MenuBar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.List;


//Game UI.
// DO NOT modify this file!

 class GamePanel extends JPanel {

    private final GameLogic gameLogic;
    private static final int TIMER_PERIOD = 1000;
    private int count;
    JLabel countDownLabel = new CountDownLabel("");
    private JPanel backgroundPanel;

    private ActionListener exitGame;

    //init GamePanel
    public GamePanel(GameLogic logic, ActionListener exitGame) {

        gameLogic = logic;
        this.exitGame = exitGame;

        this.setBackground(Color.BLACK);

        Image image = new ImageIcon("src/main/java/image/gamePanel2.jpg").getImage();
        backgroundPanel = new Background(image);

        add(backgroundPanel,new FlowLayout(FlowLayout.CENTER, 0, 0));

        drawGamingPanel();
    }

     //refresh the Panel
     private void updatePanel() {
         backgroundPanel.removeAll();
         backgroundPanel.repaint();
         drawGamingPanel();
         backgroundPanel.revalidate();
     }

    private void drawGamingPanel() {
        backgroundPanel.add(drawTimerPanel());
        backgroundPanel.add(drawBuffer());
        backgroundPanel.add(drawCodeMatrix());
        backgroundPanel.add(drawSequence());
        backgroundPanel.add(drawScorePanel());
        backgroundPanel.add(drawMenuBar());
    }

    private void drawGameOver() {
        backgroundPanel.removeAll();
        backgroundPanel.repaint();
        backgroundPanel.add(drawTimerPanel());
        backgroundPanel.add(drawBuffer());
        backgroundPanel.add(drawSequence());
        backgroundPanel.add(drawScorePanel());
        backgroundPanel.add(drawMenuBar());
        backgroundPanel.add(drawTimeOutPanel());
        backgroundPanel.revalidate();
    }

    private JPanel drawCodeMatrix() {

        int matrixSpan = gameLogic.status.getMatrixSpan();

        CodeMatrixPanel panel = new CodeMatrixPanel(matrixSpan);

        MatrixCell[][] codeSource = gameLogic.status.getCodeMatrix();

        for (int row = 0; row < matrixSpan; row++) {
            for (int col = 0; col < matrixSpan; col++) {
                JButton matrixCell = drawMatrixCell(codeSource[row][col], row, col);
                panel.add(matrixCell);
            }
        }
        return panel;
    }

    private JButton drawMatrixCell(MatrixCell tile, int row, int col) {
        JButton matrixCell = new MatrixCellButton(tile.getCode());
        if (tile.isSelected())
            matrixCell.setForeground(new Color(70, 44, 84));

        if (tile.isAvailable()) {
            matrixCell.setBackground(new Color(41, 44, 57));

            if (!tile.isSelected())
                addClickEvent(matrixCell, row, col);
        }
        return matrixCell;
    }

    private JPanel drawBuffer() {
        JPanel panel = new BufferPanel();
        for (int i = 0; i < gameLogic.status.getBufferSize(); i++) {
            JLabel label = new BufferCell(gameLogic.status.getBuffer().get(i));
            panel.add(label);
        }
        return panel;
    }

    private JPanel drawSequence() {
        JPanel panel = new Daemons();
        List<Daemon> daemons = gameLogic.status.getDaemons();

        for (Daemon daemon : daemons) {
            JPanel daemonPanel = new DaemonLabel();
            panel.add(daemonPanel);

            if (daemon.isSucceeded())
                daemonPanel.add(new SucceededLabel());
            if (daemon.isFailed())
                daemonPanel.add(new FailedLabel());

            if (!daemon.isFailed() && !daemon.isSucceeded()) {
                for (int j = 0; j < daemon.getSeq().size(); j++) {
                    JLabel label= drawDaemonCell(daemon.getSeq().get(j));
                    daemonPanel.add(label);
                }
            }
        }

        return panel;
    }

    private JLabel drawDaemonCell(DaemonCell seqCode){
        JLabel label = new DaemonCellLabel(seqCode.getCode());
        if (!seqCode.isAdded())
            label.setForeground(Color.WHITE);

        if (seqCode.isSelected())
            label.setBorder(BorderFactory.createLineBorder(new Color(250, 247, 10)));

        return  label;
    }

    private JPanel drawScorePanel() {
       return new JPanel(); //TODO
    }

    private JPanel drawTimeOutPanel() {
        return new GameOver();
    }

    private JPanel drawTimerPanel() {
        JPanel panel = new TimeLimit();

        panel.add(countDownLabel);

        if(gameLogic.status.getTimeLimit() - count < 0)
            count = 0;

        String text = (gameLogic.status.getTimeLimit() - count) + "";
        setCountDownLabelText(text);

        return panel;
    }

    //set Timer Label text
    private void setCountDownLabelText(String text) {
        countDownLabel.setText(text);
    }

    //Timer event
    private void startTime() {
        new Timer(TIMER_PERIOD, e -> {
            if (count < gameLogic.status.getTimeLimit()) {
                count++;

                String text = (gameLogic.status.getTimeLimit() - count) + "";
                setCountDownLabelText(text);

            } else {
                ((Timer) e.getSource()).stop();
                gameLogic.setTimeOut();
                gameLogic.finalCheck();
                drawGameOver();
            }
        }).start();
    }

    private void addClickEvent(JButton matrixCell, int row, int col) {
        int[] tileSelected = new int[2];
        matrixCell.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameLogic.timeFlag == 0) {
                    gameLogic.timeFlag = 1;
                    startTime();
                }

                tileSelected[0] = row;
                tileSelected[1] = col;
                gameLogic.setTileSelected(tileSelected);
                gameLogic.updateState();
                updatePanel();
            }

            @Override
            public void mousePressed(MouseEvent e) {//no such request
            }

            @Override
            public void mouseReleased(MouseEvent e) {//no such request
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                matrixCell.setForeground(Color.CYAN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                matrixCell.setForeground(new Color(222, 255, 85));
            }
        });
    }

    private JPanel drawMenuBar(){
        JPanel menuBar = new MenuBar();
        JButton exitButton = new ExitButton();
        exitButton.addActionListener(exitGame);
        menuBar.add(exitButton);

        return  menuBar;
    }

}
