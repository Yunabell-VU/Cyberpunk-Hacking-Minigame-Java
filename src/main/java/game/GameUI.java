package game;

import command.BackToMenuCommand;
import command.ClickCellCommand;
import command.EndGameCommand;
import command.UndoCommand;
import entity.*;
import graphics.Background;
import graphics.Redraw;
import graphics.GameGraphicStyle;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import static graphics.GameGraphicStyle.*;
import static graphics.GameGraphicStyle.styleMatrixCellAvailable;

public class GameUI implements Redraw,GameGraphicStyle {
    private Status status;
    private final JPanel backgroundPanel;
    private final Game game;

    public GameUI (Game game, Status status){
        this.status = status;
        this.game = game;
        backgroundPanel = new Background("GAME");
        game.add(backgroundPanel, new FlowLayout(FlowLayout.CENTER, 0, 0));
    }

    public void updateGameUI(int time, int score, Status status){
        this.status = status;
        Redraw.clearCanvas(backgroundPanel);

        if (game.isGameOver()) drawGameOverPanel(time,score);
        else drawGamingPanel(time,score);
        backgroundPanel.revalidate();
    }

    public void drawGamingPanel(int time, int score) {
        backgroundPanel.add(drawTimerPanel(time));
        backgroundPanel.add(drawBuffer());
        backgroundPanel.add(drawCodeMatrix());
        backgroundPanel.add(drawDaemons());
        backgroundPanel.add(drawScorePanel(score));
        backgroundPanel.add(drawMenuBar());
        backgroundPanel.add(undoCoolDownPanel());
    }

    private void drawGameOverPanel(int time, int score) {
        backgroundPanel.add(drawTimerPanel(time));
        backgroundPanel.add(drawBuffer());
        backgroundPanel.add(drawDaemons());
        backgroundPanel.add(drawScorePanel(score));
        backgroundPanel.add(drawMenuBar());
        backgroundPanel.add(drawTimeOutPanel());
    }

    private JPanel drawCodeMatrix() {
        CodeMatrix codeSource = status.getCodeMatrix();
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

    private JButton drawMatrixCell(MatrixCell cell) {
        JButton matrixCell = new JButton(cell.getCode());
        styleMatrixCellButton(matrixCell);
        if (cell.isSelected()) styleMatrixCellSelected(matrixCell);

        if (cell.isAvailable()) {
            styleMatrixCellAvailable(matrixCell);
            if (!cell.isSelected()) addMouseEventToMatrixCell(matrixCell, cell.getCoordinate());
        }
        return matrixCell;
    }

    private JPanel drawBuffer() {
        JPanel panel = new JPanel();
        styleBufferPanel(panel);
        for (int i = 0; i < status.getBuffer().getBufferSize(); i++) {
            JLabel bufferCellLabel = new JLabel(status.getBuffer().getBufferCode(i),SwingConstants.CENTER);
            styleBufferCell(bufferCellLabel);
            panel.add(bufferCellLabel);
        }
        return panel;
    }

    private JPanel drawDaemons() {
        JPanel panel = new JPanel();
        styleDaemonPanel(panel);
        List<Daemon> daemons = status.getDaemons();

        for (Daemon daemon : daemons) {
            JPanel daemonPanel = new JPanel();
            GameGraphicStyle.styleDaemonsPanel(daemonPanel);
            panel.add(daemonPanel);

            if(!daemon.isSucceeded()&& !daemon.isFailed())  {
                for (int j = 0; j < daemon.getDaemonCells().size(); j++) {
                    JLabel label = drawDaemonCell(daemon.getDaemonCells().get(j));
                    daemonPanel.add(label);
                }
            } else{
                JLabel resultLabel = new JLabel();

                if (daemon.isSucceeded()) resultLabel.setText("SUCCEEDED");
                else resultLabel.setText("FAILED");

                styleResultLabel(resultLabel);
                daemonPanel.add(resultLabel);
            }
        }

        return panel;
    }

    private JLabel drawDaemonCell(DaemonCell daemonCell) {
        JLabel label = new JLabel(daemonCell.getCode(),SwingConstants.CENTER);
        styleDaemonCellLabel(label);
        if (!daemonCell.isMatched()) styleDaemonCellNotAdded(label);
        if (daemonCell.isSelected()) styleDaemonCellSelected(label);

        return label;
    }

    private JPanel drawScorePanel(int score) {
        JPanel panel = new JPanel();
        styleScorePanel(panel);
        JLabel currentScore = new JLabel(String.valueOf(status.getScore()),SwingConstants.CENTER);
        styleScoreLabel(currentScore);
        JLabel highestScore = new JLabel(String.valueOf(score),SwingConstants.CENTER);
        styleScoreLabel(highestScore);
        panel.add(currentScore);
        panel.add(highestScore);
        return panel;
    }

    private JPanel drawTimeOutPanel() {
        JPanel panel = new JPanel();
        styleTimeOutPanel(panel);

        JLabel timeOutLabel = new JLabel("TIME OUT", SwingConstants.CENTER);
        styleTimeOutLabel(timeOutLabel);

        panel.add(timeOutLabel);
        return panel;
    }

    private JPanel drawTimerPanel(int time) {
        JPanel panel = new JPanel();
        styleTimeLimitPanel(panel);
        JLabel countDownLabel = new JLabel(time + "",SwingConstants.CENTER);
        styleCountDownLabel(countDownLabel);
        panel.add(countDownLabel);

        return panel;
    }

    private JPanel drawMenuBar() {
        JPanel menuBar = new JPanel();
        styleMenuBarPanel(menuBar);

        menuBar.add(createMenuBarButton("UNDO"));
        menuBar.add(createMenuBarButton("END"));
        menuBar.add(createMenuBarButton("MENU"));

        return menuBar;
    }

    private JButton createMenuBarButton(String text){
        JButton button = new JButton(text);
        styleGameMenuButton(button);

        switch (text){
            case "UNDO":
                button.addActionListener(e -> game.executeCommand(new UndoCommand(game)));
                break;
            case "END":
                button.addActionListener(e -> game.executeCommand(new EndGameCommand(game)));
                break;
            case "MENU":
                game.executeCommand(new BackToMenuCommand(game,button));
                break;
            default:
                break;
        }
        return button;
    }

    private JPanel undoCoolDownPanel(){
        JPanel panel = new JPanel();
        JLabel label = new JLabel(""+SwingConstants.CENTER);

        if(game.canUndo()) label.setText("OK");
        else label.setText(String.valueOf(game.getUndoCoolDown()));

        styleUndoCDPanel(panel);
        styleUndoCDLabel(label);
        panel.add(label);

        return panel;
    }

    private void addMouseEventToMatrixCell(JButton matrixCell, Coordinate clickedCellPosition) {
        matrixCell.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                game.executeCommand(new ClickCellCommand(game,clickedCellPosition));
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

}
