package game;

import entity.*;

import java.io.*;

class Status implements Serializable {

    private final Difficulty gameDifficulty;
    private int score;
    private Puzzle puzzle;

    public
    Status(Difficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
        this.puzzle = new Puzzle(gameDifficulty.getBufferOffset());
        this.score = 0;
    }

    public int
    getScore() {
        return score;
    }

    public void
    setScore(int scoreReward) {
        this.score += scoreReward;
    }

    public Puzzle
    getPuzzle(){
        return puzzle;
    }

    public void
    switchPuzzle() {
        this.puzzle = new Puzzle(gameDifficulty.getBufferOffset());
    }

    public Difficulty
    getGameDifficulty() {
        return gameDifficulty;
    }

    public Object
    deepClone() throws Exception {
        // Serialize
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(this);

        // Deserialize
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);

        return ois.readObject();
    }
}
