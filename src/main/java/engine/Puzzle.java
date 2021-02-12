package engine;

import entity.Tile;

import java.util.ArrayList;

import static game.Game.*;

//Parse the puzzle
public class Puzzle {

    public Tile[][] codeMatrix = new Tile[6][6];

    public ArrayList<ArrayList<Tile>> sequences = new ArrayList<>();
    public int bufferSize;
    public ArrayList<String> buffer = new ArrayList<>();
    public Puzzle(int bufferOffset){
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
        ArrayList<Tile> s1 = new ArrayList<>(3);
        s1.add(t1);
        s1.add(t2);
        s1.add(t3);

        Tile t4 = new Tile("E9",WAITING);
        ArrayList<Tile> s2 = new ArrayList<>(4);
        s2.add(t4);
        s2.add(t3);
        s2.add(t1);
        s2.add(t2);
        sequences.add(s1);
        sequences.add(s2);

         bufferSize = 8+bufferOffset;
        ///////////////////////////////////////////////

        for (int i = 0; i < bufferSize;i++){
            buffer.add("    ");
        }

    }
}
