package game;

import entity.*;

import java.util.List;

//status of each movement.
//UI draws the panel based on the latest Status
//Do Not modify this file!

 class Status {

    private int timeLimit;
    private int score;
    private final CodeMatrix codeMatrix;
    private List<Daemon> daemons;
    private final Buffer buffer;
    private final int matrixSpan;
    private final Difficulty gameDifficulty;

    public Status(Puzzle puzzle, Difficulty gameDifficulty, int timeLimit, int score){

        this.matrixSpan = puzzle.getMatrixSpan();
        this.codeMatrix = puzzle.getCodeMatrix();
        this.daemons = puzzle.getSequences();
        this.gameDifficulty = gameDifficulty;
        this.buffer = new Buffer(puzzle.getBufferSize() + gameDifficulty.getBufferOffset());
        this.timeLimit = timeLimit;
        this.score = score;

    }

    public int getScore() {
        return score;
    }

    public int getTimeLimit() {return timeLimit;}

    public List<Daemon> getDaemons() {
        return daemons;
    }

    public int getMatrixSpan() {return matrixSpan;}

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

    public void setScore(int score) {
        this.score = score;
    }

    public Difficulty getGameDifficulty() {
         return gameDifficulty;
     }
 }
