package game;

import command.Command;
import entity.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


import java.util.Deque;
import java.util.LinkedList;

public class Game extends JPanel {

    private final transient GameLogic gameLogic;
    private final transient GameUI gameUI;

    private static final int TIMER_PERIOD = 1000;
    private boolean gameTimeStarted = false;
    private static final int UNDO_COOL_DOWN = 8;
    private int undoCoolDown;
    private boolean undoAvailable = true;

    private final Deque<Status> statuses = new LinkedList<>();
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

        gameUI.updateGameUI(gameLogic.getTimeLimit(), gameLogic.getHighestScore(),currentStatus);
    }

    //refresh the Panel
    public void
    updatePanel() {
        gameUI.updateGameUI(gameLogic.getTimeLimit(),gameLogic.getHighestScore(),currentStatus);
    }

    public void
    executeCommand(Command command){
        if(command.executable()) command.execute();
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

    public void
    endGame() {
        gameLogic.finishGame();
    }

    public boolean
    isGameOver(){
        return gameLogic.isGameOver();
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
                endGame();
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
