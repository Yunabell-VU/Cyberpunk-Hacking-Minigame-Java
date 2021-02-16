package game;

import entity.Puzzle;
import entity.Sequence;
import entity.Tile;

import java.util.ArrayList;

//status of each movement.
//UI draws the panel based on the latest Status
//Do Not modify this file!

public class Status {

    private int currentCount;
    private int score;
    private Tile[][] codeMatrix;
    private ArrayList<Sequence> sequences;
    private int bufferSize;
    private ArrayList<String> buffer = new ArrayList<>();
    private final int matrixSpan;
    private final Difficulty gameDifficulty;

    public Status(Puzzle puzzle, Difficulty gameDifficulty){

        this.matrixSpan = puzzle.getMatrixSpan();
        this.codeMatrix = puzzle.getCodeMatrix();
        this.sequences = puzzle.getSequences();
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

    public ArrayList<Sequence> getSequences() {
        return sequences;
    }

    public int getMatrixSpan() {return matrixSpan;}

    public Tile[][] getCodeMatrix() {
        return codeMatrix;
    }

    public ArrayList<String> getBuffer() {
        return buffer;
    }

    public void setSequences(ArrayList<Sequence> sequences) {
        this.sequences = sequences;
    }

    public void setCodeMatrix(Tile[][] codeMatrix) {
        this.codeMatrix = codeMatrix;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void setBuffer(ArrayList<String> buffer) {
        this.buffer = buffer;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
