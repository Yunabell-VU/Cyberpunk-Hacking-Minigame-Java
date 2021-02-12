package game;

import entity.Puzzle;
import entity.Sequence;
import entity.Tile;

import java.util.ArrayList;

public class Status {
    public int currentCount;
    public int score;
    public Tile[][] codeMatrix;
    public ArrayList<Sequence> sequences;
    public int bufferSize;
    public ArrayList<String> buffer = new ArrayList<>();

    public Status(Puzzle puzzle, int bufferSizeOffset, int count, int score){

        this.codeMatrix = puzzle.codeMatrix;
        this.sequences = puzzle.sequences;
        this.bufferSize = puzzle.bufferSize + bufferSizeOffset;
        this.currentCount = count;
        this.score = 0;

        ////////////////FIXME///////////////////////
        for (int i = 0; i < bufferSize;i++){
            buffer.add("    ");
        }
        ///////////////////////////////////////////
    }
}
