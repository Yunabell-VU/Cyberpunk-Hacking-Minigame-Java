package game;

import entity.*;

import java.util.List;

//status of each movement.
//UI draws the panel based on the latest Status
//Do Not modify this file!

class Status {

    private final Difficulty gameDifficulty;
    private int timeLimit;
    private int score;

    private final CodeMatrix codeMatrix;
    private List<Daemon> daemons;
    private final Buffer buffer;


    public Status(Puzzle puzzle, Difficulty gameDifficulty, int timeLimit) {

        this.codeMatrix = puzzle.getCodeMatrix();
        this.daemons = puzzle.getDaemons();
        this.gameDifficulty = gameDifficulty;

        int newBufferSize = puzzle.getBuffer().getBufferSize()+gameDifficulty.getBufferOffset();
        this.buffer = new Buffer(newBufferSize);

        this.timeLimit = timeLimit;
        this.score = 0;

    }

    public int getScore() {
        return score;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public List<Daemon> getDaemons() {
        return daemons;
    }

    public CodeMatrix getCodeMatrix() {
        return codeMatrix;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public void setSequences(List<Daemon> daemons) {
        this.daemons = daemons;
    }

    public void addTimeLimit(int offset) {
        timeLimit = Math.max(timeLimit + offset, 0);
    }

    public void setScore(int scoreReward) {
        this.score += scoreReward;
    }

    public Difficulty getGameDifficulty() {
        return gameDifficulty;
    }
}
