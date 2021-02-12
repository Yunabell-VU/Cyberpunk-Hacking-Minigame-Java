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
        String[][] rawMatrix = map.getMatrix();
        Tile temp;

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (row == 0) {
                    temp = new Tile(rawMatrix[row][col],AVAILABLE);
                } else {
                    temp = new Tile(rawMatrix[row][col],UNAVAILABLE);
                }
                codeMatrix[row][col] = temp;
            }
        }

        ArrayList<String[]> seq = map.getSeq();
        for (String[] strings : seq) {
            ArrayList<Tile> currentSeq = new ArrayList<>(strings.length);
            for (String s : strings) {
                temp = new Tile(s, WAITING);
                currentSeq.add(temp);
            }
            Sequence cookedSeq = new Sequence(currentSeq, UNCHECK);
            sequences.add(cookedSeq);
        }
        bufferSize = map.getBufferSize();
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
}
