package game;

import entity.*;
import graphics.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static graphics.GameGraphicStyle.*;

class Game extends JPanel {

    private final transient GameLogic gameLogic;

    private static final int TIMER_PERIOD = 1000;

    private boolean timeStarted = false;

    private final JPanel backgroundPanel;
    private final transient ActionListener exitGame;
    private final Deque<Status> statuses = new LinkedList<>();
    private Status currentStatus;

    //init GamePanel
    public Game(Status firstStatus, ActionListener exitGame) {

        this.gameLogic = new GameLogic(firstStatus);
        currentStatus = firstStatus;
        this.exitGame = exitGame;
        this.setBackground(Color.BLACK);

        backgroundPanel = new Background("GAME");

        add(backgroundPanel, new FlowLayout(FlowLayout.CENTER, 0, 0));

        drawGamingPanel();
    }

    //refresh the Panel
    private void updatePanel() {
        backgroundPanel.removeAll();
        backgroundPanel.repaint();

        if (gameLogic.isGameOver()) drawGameOverPanel();
        else drawGamingPanel();

        backgroundPanel.revalidate();
    }

    private void drawGamingPanel() {
        backgroundPanel.add(drawTimerPanel());
        backgroundPanel.add(drawBuffer());
        backgroundPanel.add(drawCodeMatrix());
        backgroundPanel.add(drawDaemons());
        backgroundPanel.add(drawScorePanel());
        backgroundPanel.add(drawMenuBar());
    }

    private void drawGameOverPanel() {
        backgroundPanel.add(drawTimerPanel());
        backgroundPanel.add(drawBuffer());
        backgroundPanel.add(drawDaemons());
        backgroundPanel.add(drawScorePanel());
        backgroundPanel.add(drawMenuBar());
        backgroundPanel.add(drawTimeOutPanel());
    }

    private JPanel drawCodeMatrix() {
        CodeMatrix codeSource = currentStatus.getCodeMatrix();
        int matrixSpan = codeSource.getMatrixSpan();

        JPanel panel = new JPanel();
        styleCodeMatrixPanel(panel,matrixSpan);

        for (int row = 0; row < matrixSpan; row++) {
            for (int col = 0; col < matrixSpan; col++) {
                JButton matrixCell = drawMatrixCell(codeSource.getMatrixCell(row, col));
                panel.add(matrixCell);
            }
        }
        return panel;
    }

    private JButton drawMatrixCell(MatrixCell tile) {
        JButton matrixCell = new JButton(tile.getCode());
        styleMatrixCellButton(matrixCell);
        if (tile.isSelected()) styleMatrixCellSelected(matrixCell);

        if (tile.isAvailable()) {
            styleMatrixCellAvailable(matrixCell);
            if (!tile.isSelected()) pickMatrixCell(matrixCell, tile.getCoordinate());
        }
        return matrixCell;
    }

    private JPanel drawBuffer() {
        JPanel panel = new JPanel();
        styleBufferPanel(panel);
        for (int i = 0; i < currentStatus.getBuffer().getBufferSize(); i++) {
            JLabel bufferCellLabel = new JLabel(currentStatus.getBuffer().getBufferCode(i),SwingConstants.CENTER);
            styleBufferCell(bufferCellLabel);
            panel.add(bufferCellLabel);
        }
        return panel;
    }

    private JPanel drawDaemons() {
        JPanel panel = new JPanel();
        styleDaemonPanel(panel);
        List<Daemon> daemons = currentStatus.getDaemons();

        for (Daemon daemon : daemons) {
            JPanel daemonPanel = new JPanel();
            GameGraphicStyle.styleDaemonsPanel(daemonPanel);
            panel.add(daemonPanel);

            if (daemon.isSucceeded()) {
                JLabel succeededLable = new JLabel("SUCCEEDED");
                styleResultLabel(succeededLable);
                daemonPanel.add(succeededLable);
            }
            if (daemon.isFailed()) {
                JLabel failedLable = new JLabel("FAILED");
                styleResultLabel(failedLable);
                daemonPanel.add(failedLable);
            }

            if (!daemon.isFailed() && !daemon.isSucceeded()) {
                for (int j = 0; j < daemon.getDaemonCells().size(); j++) {
                    JLabel label = drawDaemonCell(daemon.getDaemonCells().get(j));
                    daemonPanel.add(label);
                }
            }
        }

        return panel;
    }

    private JLabel drawDaemonCell(DaemonCell daemonCell) {
        JLabel label = new JLabel(daemonCell.getCode(),SwingConstants.CENTER);
        styleDaemonCellLabel(label);
        if (!daemonCell.isAdded()) styleDaemonCellNotAdded(label);
        if (daemonCell.isSelected()) styleDaemonCellSelected(label);

        return label;
    }

    private JPanel drawScorePanel() {
        return new JPanel();
    }

    private JPanel drawTimeOutPanel() {
        JPanel panel = new JPanel();
        styleTimeOutPanel(panel);

        JLabel timeOutLabel = new JLabel("TIME OUT", SwingConstants.CENTER);
        styleTimeOutLabel(timeOutLabel);

        panel.add(timeOutLabel);
        return panel;
    }

    private JPanel drawTimerPanel() {
        JPanel panel = new JPanel();
        styleTimeLimitPanel(panel);
        JLabel countDownLabel = new JLabel(gameLogic.getTimeLimit() + "",SwingConstants.CENTER);
        styleCountDownLabel(countDownLabel);
        panel.add(countDownLabel);

        return panel;
    }

    private JPanel drawMenuBar() {
        JPanel menuBar = new JPanel();
        styleMenuBarPanel(menuBar);

        JButton undoButton = new JButton("UNDO");
        styleGameMenuButton(undoButton);
        undoButton.addActionListener(e -> undo());
        menuBar.add(undoButton);

        JButton endButton = new JButton("END");
        styleGameMenuButton(endButton);
        endButton.addActionListener(e -> finishGame());
        menuBar.add(endButton);

        JButton exitButton = new JButton("MENU");
        styleGameMenuButton(exitButton);
        exitButton.addActionListener(exitGame);
        menuBar.add(exitButton);

        return menuBar;
    }

    private void pickMatrixCell(JButton matrixCell, Coordinate clickedCellPosition) {
        matrixCell.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!timeStarted) {
                    timeStarted = true;
                    startTime();
                }
                statuses.push(currentStatus);
                updateCurrentStatus(clickedCellPosition);
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
                styleMatrixCellMouseEnter(matrixCell);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                styleMatrixCellMouseExit(matrixCell);
            }
        });
    }

    private void updateCurrentStatus(Coordinate clickedCellPosition) {
        Status newStatus = null;
        try {
            newStatus = (Status) currentStatus.deepClone();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (newStatus != null) currentStatus = gameLogic.updateStatus(newStatus, clickedCellPosition);
    }

    private void startTime() {
        new Timer(TIMER_PERIOD, e -> {
            if (gameLogic.getTimeLimit() > 0) gameLogic.updateTimeLimit(-1);

            else {
                ((Timer) e.getSource()).stop();
                finishGame();
            }
            updatePanel();
        }).start();
    }

    private void finishGame(){
        gameLogic.setTimeLimitZero();
        gameLogic.setGameOver();
        gameLogic.markUnrewardedDaemonsFailed();
    }

    private void undo() {
        if (!statuses.isEmpty() && !gameLogic.isGameOver()) currentStatus = statuses.pop();
        gameLogic.switchLogicStatusToGameStatus(currentStatus);
        updatePanel();
    }
}
