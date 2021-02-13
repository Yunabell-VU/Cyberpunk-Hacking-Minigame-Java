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
    private final int matrixSpan;
    private final Tile[][] codeMatrix;
    private final ArrayList<Sequence> sequences = new ArrayList<>();
    private final int bufferSize;

    public Puzzle(){
        Parse map = new Parse();
        map.readFile();
        matrixSpan = map.getMatrixSpan();
        System.out.println("matrix span: "+ matrixSpan);
        String[][] rawMatrix = map.getMatrix();
        Tile temp;
        codeMatrix = new Tile[matrixSpan][matrixSpan];
        for (int row = 0; row < matrixSpan; row++) {
            for (int col = 0; col < matrixSpan; col++) {
                if (row == 0) {
                    temp = new Tile(rawMatrix[row][col].toUpperCase(),AVAILABLE);
                } else {
                    temp = new Tile(rawMatrix[row][col].toUpperCase(),UNAVAILABLE);
                }
                codeMatrix[row][col] = temp;
            }
        }

        ArrayList<String[]> seq = map.getSeq();
        for (String[] strings : seq) {
            ArrayList<Tile> currentSeq = new ArrayList<>(strings.length);
            for (String s : strings) {
                temp = new Tile(s.toUpperCase(), WAITING);
                currentSeq.add(temp);
            }
            Sequence cookedSeq = new Sequence(currentSeq, UNCHECK);
            sequences.add(cookedSeq);
        }
        bufferSize = map.getBufferSize();
    }
    //Do not modify the gets
    public int getBufferSize() {return bufferSize;}

    public Tile[][] getCodeMatrix() {return codeMatrix;}

    public ArrayList<Sequence> getSequences() {return sequences;}

    public int getMatrixSpan() {return matrixSpan;}
}
