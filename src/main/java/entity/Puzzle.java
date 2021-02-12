package entity;

import java.util.ArrayList;

import static entity.Sequence.UNCHECK;
import static entity.Tile.*;

//Parse the puzzle
public class Puzzle {
    public Tile[][] codeMatrix = new Tile[6][6];
    public ArrayList<Sequence> sequences = new ArrayList<>();
    public int bufferSize;

    public Puzzle(){

        ///////////////////FIXME/////////////////////////
        Tile t = new Tile("E9",UNAVAILABLE);
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                codeMatrix[row][col] = t;
            }
        }
        for (int col = 0; col < 6; col++) {
            codeMatrix[0][col]= new Tile("E9",AVAILABLE);
        }

        Tile t1 = new Tile("7C",WAITING);
        Tile t2 = new Tile("55",WAITING);
        Tile t3 = new Tile("BD",WAITING);
        ArrayList<Tile> a1 = new ArrayList<>(3);
        a1.add(t1);
        a1.add(t2);
        a1.add(t3);

        Sequence seq1 = new Sequence(a1,UNCHECK);

        Tile t4 = new Tile("E9",WAITING);
        ArrayList<Tile> a2 = new ArrayList<>(4);

        a2.add(t4);
        a2.add(t3);
        a2.add(t1);
        a2.add(t2);
        Sequence seq2 = new Sequence(a2,UNCHECK);
        Sequence seq3 = new Sequence(a2,UNCHECK);
        sequences.add(seq1);
        sequences.add(seq2);
        sequences.add(seq3);

        bufferSize = 8;
        ///////////////////////////////////////////////

    }
}
