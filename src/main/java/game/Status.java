package game;

import entity.*;

import java.io.*;
import java.util.List;

class Status implements Serializable {

    private final Difficulty gameDifficulty;

    private int score;

    private Puzzle puzzle;

    public Status(Puzzle puzzle, Difficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
        this.puzzle = puzzle;
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public List<Daemon> getDaemons() {
        return puzzle.getDaemons();
    }

    public CodeMatrix getCodeMatrix() {
        return puzzle.getCodeMatrix();
    }

    public Buffer getBuffer() {
        return puzzle.getBuffer();
    }

    public void setScore(int scoreReward) {
        this.score += scoreReward;
    }

    public Difficulty getGameDifficulty() {
        return gameDifficulty;
    }

    public Object deepClone() throws Exception {
        // Serialize
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(this);

        // Deserialize
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);

        return ois.readObject();
    }

    public void switchPuzzle() {
        this.puzzle = new Puzzle(gameDifficulty.getBufferOffset());
    }
}
