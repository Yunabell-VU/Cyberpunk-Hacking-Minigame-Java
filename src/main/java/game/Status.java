package game;

import entity.Puzzle;
import entity.Daemon;
import entity.MatrixCell;

import java.util.ArrayList;
import java.util.List;

//status of each movement.
//UI draws the panel based on the latest Status
//Do Not modify this file!

 class Status {

    private int currentCount;
    private int score;
    private MatrixCell[][] codeMatrix;
    private List<Daemon> daemons;
    private int bufferSize;
    private List<String> buffer = new ArrayList<>();
    private final int matrixSpan;
    private final Difficulty gameDifficulty;

    public Status(Puzzle puzzle, Difficulty gameDifficulty){

        this.matrixSpan = puzzle.getMatrixSpan();
        this.codeMatrix = puzzle.getCodeMatrix();
        this.daemons = puzzle.getSequences();
        this.gameDifficulty = gameDifficulty;
        this.bufferSize = puzzle.getBufferSize() + gameDifficulty.getBufferOffset();
        this.currentCount = gameDifficulty.getInitTimeLimit();
        this.score = 0;

        for (int i = 0; i < bufferSize;i++)
            buffer.add(" ");
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public int getScore() {
        return score;
    }

    public int getCurrentCount() {return currentCount;}

    public List<Daemon> getDaemons() {
        return daemons;
    }

    public int getMatrixSpan() {return matrixSpan;}

    public MatrixCell[][] getCodeMatrix() {
        return codeMatrix;
    }

    public List<String> getBuffer() {
        return buffer;
    }

    public void setSequences(List<Daemon> daemons) {
        this.daemons = daemons;
    }

    public void setCodeMatrix(MatrixCell[][] codeMatrix) {
        this.codeMatrix = codeMatrix;
    }

    public void setBuffer(List<String> buffer) {
        this.buffer = buffer;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public void setScore(int score) {
        this.score = score;
    }

     public Difficulty getGameDifficulty() {
         return gameDifficulty;
     }
 }
