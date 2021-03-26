package game;

import entity.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


import java.util.Deque;
import java.util.LinkedList;

//save and track status history
//pass status, time and score to GameUI to display
//pass status to GameLogic to update

public class Game extends JPanel {

    private final transient GameLogic gameLogic;
    private final transient GameUI gameUI;

    private static final int TIMER_PERIOD = 1000;
    private boolean gameTimeStarted = false;

    private static final int UNDO_COOL_DOWN = 8; //Default cool down time for undo
    private int undoCoolDown;
    private boolean undoAvailable = true;

    private final Deque<Status> statuses = new LinkedList<>(); //Status History
    private Status currentStatus;

    public final transient ActionListener exitGame;

    //init GamePanel
    public
    Game(Status firstStatus, ActionListener exitGame) {

        this.gameLogic = new GameLogic(firstStatus);
        this.currentStatus = firstStatus;
        this.gameUI = new GameUI(this, currentStatus);
        this.exitGame = exitGame;

        this.undoCoolDown = UNDO_COOL_DOWN;
        this.setBackground(Color.BLACK);

        updatePanel();
    }

    public void
    updatePanel() {
        gameUI.updateGameUI(gameLogic.getTimeLimit(),gameLogic.getHighestScore(),currentStatus);
    }

    public void
    triggerGameTimer(){
        if (!gameTimeStarted) {
            gameTimeStarted = true;
            startGameTimer();
        }
    }

    public void
    saveAndUpdateStatus(Coordinate clickedCellPosition){
        statuses.push(currentStatus);
        updateCurrentStatus(clickedCellPosition);
    }

    public void
    setGameTimeToZero(){
        gameLogic.setTimeLimitZero();
    }

    public boolean
    isGameOver(){
        return gameLogic.isGameOver();
    }

    public boolean
    isGameStarted(){
        return gameTimeStarted;
    }

    public boolean
    canUndo(){
        return !statuses.isEmpty() && !gameLogic.isGameOver() && undoAvailable;
    }

    public void
    undo() {
        startUndoTimer();
        undoAvailable = false;
        currentStatus = statuses.pop();
        gameLogic.updateStatus(currentStatus);
        updatePanel();
    }

    public int
    getUndoCoolDown(){
        return undoCoolDown;
    }

    private void
    updateCurrentStatus(Coordinate clickedCellPosition) {
        Status newStatus = null;
        try {
            newStatus = (Status) currentStatus.deepClone();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (newStatus != null) currentStatus = gameLogic.updateStatus(newStatus, clickedCellPosition);
    }

    private void
    startGameTimer() {
        new Timer(TIMER_PERIOD, e -> {
            if (gameLogic.getTimeLimit() > 0) gameLogic.updateTimeLimit(-1);
            else {
                ((Timer) e.getSource()).stop();
                gameLogic.finishGame();
            }
            updatePanel();
        }).start();
    }

    private void
    startUndoTimer() {
        new Timer(TIMER_PERIOD, e -> {
            if (undoCoolDown > 0) undoCoolDown--;
            else {
                ((Timer) e.getSource()).stop();
                undoAvailable = true;
                undoCoolDown = UNDO_COOL_DOWN;
            }
            updatePanel();
        }).start();
    }
}
