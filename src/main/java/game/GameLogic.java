package game;

import entity.*;

import static game.ScoreHandler.exportHighestScore;
import static game.ScoreHandler.importHighestScore;

//update puzzle based on clicked matrix cell
//update time and score based on the updated puzzle results

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
    //invoked by Game when undo is called
    //this is used to make it safe under a combo action of UNDO and END because END only changes the status saved in GameLogic
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
