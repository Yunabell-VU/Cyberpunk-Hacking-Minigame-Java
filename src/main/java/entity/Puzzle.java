package entity;

import engine.Parse;

import java.util.ArrayList;

import static entity.Sequence.UNCHECK;
import static entity.Tile.*;

//store the pure puzzle here.
//you can decide how to add state to each element you parsed

//State need to implement:
//CodeMatrix : every tile in first row ->state: AVAILABLE, remaining tiles->state: UNAVAILABLE
//Sequence: every tile in sequence->state: WAITING

public class Puzzle {

    //do not modify the definitions here
    private Tile[][] codeMatrix = new Tile[5][5];
    private ArrayList<Sequence> sequences = new ArrayList<>();
    private int bufferSize;

    public Puzzle(){
        Parse map = new Parse();
        map.readFile();

        String[][] rawMatrix = map.matrix;
        Tile t;

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (row == 0) {
                    t = new Tile(rawMatrix[row][col],AVAILABLE);
                } else {
                    t = new Tile(rawMatrix[row][col],UNAVAILABLE);
                }
                codeMatrix[row][col] = t;
            }
        }

        for (int i = 0; i < map.seq.size(); i++) {
            String[] rawSeq = map.seq.get(i);
            ArrayList<Tile> currentSeq = new ArrayList<>(rawSeq.length);
            for (String s : rawSeq) {
                t = new Tile(s, WAITING);
                currentSeq.add(t);
            }
            Sequence cookedSeq = new Sequence(currentSeq, UNCHECK);
            sequences.add(cookedSeq);
        }
        bufferSize = map.bufferSize;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public Tile[][] getCodeMatrix() {
        return codeMatrix;
    }

    public ArrayList<Sequence> getSequences() {
        return sequences;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void setCodeMatrix(Tile[][] codeMatrix) {
        this.codeMatrix = codeMatrix;
    }

    public void setSequences(ArrayList<Sequence> sequences) {
        this.sequences = sequences;
    }
}
