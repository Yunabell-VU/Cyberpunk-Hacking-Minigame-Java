package game;

import entity.*;

import static game.ScoreHandler.exportHighestScore;
import static game.ScoreHandler.importHighestScore;

class GameLogic {

    private boolean gameOver = false;
    private int timeLimit;

    private Status statusToBeDisplayed;
    private final PuzzleHandler puzzleHandler;
    private final Difficulty difficulty;

    private int highestScore = importHighestScore();

    public
    GameLogic(Status status) {
        this.statusToBeDisplayed = status;
        this.difficulty = statusToBeDisplayed.getGameDifficulty();
        this.puzzleHandler = new PuzzleHandler(statusToBeDisplayed.getPuzzle());
        this.timeLimit = status.getGameDifficulty().getInitTimeLimit();
    }

    //API called in Game, returned the updated status
    public Status
    updateStatus(Status status, Coordinate clickedCellPosition) {
        statusToBeDisplayed = status;

        puzzleHandler.updatePuzzle(statusToBeDisplayed.getPuzzle(), clickedCellPosition);
        updateReward(puzzleHandler.countUncheckedDaemons("SUCCEEDED"), puzzleHandler.countUncheckedDaemons("FAILED"));

        if (puzzleHandler.isAllDaemonsChecked() && !gameOver) statusToBeDisplayed.switchPuzzle();
        return statusToBeDisplayed;
    }

    //overloading
    public void
    updateStatus(Status currentStatus){
        statusToBeDisplayed = currentStatus;
        puzzleHandler.updatePuzzle(statusToBeDisplayed.getPuzzle());
    }

    public boolean
    isGameOver() {
        return gameOver;
    }

    public void
    finishGame(){
        setGameOver();
        puzzleHandler.markUncheckedDaemonsFailed();
        saveHighestScore();
    }

    public int
    getTimeLimit() {
        return timeLimit;
    }

    public void
    setTimeLimitZero() {
        timeLimit = 0;
    }

    public void
    updateTimeLimit(int offset) {
        timeLimit = Math.max(timeLimit + offset, 0);
    }


    public int
    getHighestScore(){ return highestScore; }

    private void
    updateReward(int succeeded, int failed) {
        for (int i = 0; i < succeeded; i++) {
            rewardTime();
            rewardScore();
        }
        for (int i = 0; i < failed; i++) {
            punishTime();
        }
    }

    private void
    rewardScore(){
        int scoreReward = difficulty.getScoreReward();
        statusToBeDisplayed.setScore(scoreReward);
        if(highestScore < statusToBeDisplayed.getScore()){
            highestScore = statusToBeDisplayed.getScore();
        }
    }

    private void
    rewardTime() {
        updateTimeLimit(difficulty.getTimeReward());
    }

    private void
    punishTime() {
        updateTimeLimit(difficulty.getTimePunishment());
    }

    private void
    setGameOver() {
        gameOver = true;
    }

    private void
    saveHighestScore(){ exportHighestScore(highestScore); }
}
