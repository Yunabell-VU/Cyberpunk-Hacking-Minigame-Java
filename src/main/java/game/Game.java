package game;

import command.Command;
import entity.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


import java.util.Deque;
import java.util.LinkedList;

public class Game extends JPanel {

    public final transient GameLogic gameLogic;

    private static final int TIMER_PERIOD = 1000;
    private boolean timeStarted = false;

    public final transient ActionListener exitGame;
    private final Deque<Status> statuses = new LinkedList<>();
    private Status currentStatus;
    private final transient GameUI gameUI;

    //init GamePanel
    public Game(Status firstStatus, ActionListener exitGame) {

        this.gameLogic = new GameLogic(firstStatus);
        currentStatus = firstStatus;
        this.gameUI = new GameUI(this, currentStatus);
        this.exitGame = exitGame;

        this.setBackground(Color.BLACK);

        gameUI.updateGameUI(gameLogic.getTimeLimit(), gameLogic.getHighestScore(),currentStatus);
    }

    //refresh the Panel
    public void updatePanel() {
        gameUI.updateGameUI(gameLogic.getTimeLimit(),gameLogic.getHighestScore(),currentStatus);
    }

    public void triggerGameTimer(){
        if (!timeStarted) {
            timeStarted = true;
            startGameTimer();
        }
    }

    public void saveAndUpdateStatus(Coordinate clickedCellPosition){
        statuses.push(currentStatus);
        updateCurrentStatus(clickedCellPosition);
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

    private void startGameTimer() {
        new Timer(TIMER_PERIOD, e -> {
            if (gameLogic.getTimeLimit() > 0) gameLogic.updateTimeLimit(-1);

            else {
                ((Timer) e.getSource()).stop();
                finishGame();
            }
            updatePanel();
        }).start();
    }

    private void finishGame() {
        gameLogic.setGameOver();
        gameLogic.markUnrewardedDaemonsFailed();
        gameLogic.saveHighestScore();
    }

    public boolean canUndo(){
        return !statuses.isEmpty() && !gameLogic.isGameOver();
    }
    public void undo() {
        currentStatus = statuses.pop();
        gameLogic.switchLogicStatusToGameStatus(currentStatus);
        updatePanel();
    }

    public void executeCommand(Command command){
        if(command.executable()) command.execute();
    }
}
