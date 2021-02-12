package entity;

import java.util.ArrayList;

//One single sequence set

//DO NOT modify this file!

public class Sequence {
    public final static int UNCHECK = 6666;
    public final static int SUCCESS = 7777;
    public final static int FAIL = 8888;

    private ArrayList<Tile> seq;
    private int state;

    public Sequence(ArrayList<Tile> seq, int state){
        this.seq = seq;
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public ArrayList<Tile> getSeq() {
        return seq;
    }

    public void setSeq(ArrayList<Tile> seq) {
        this.seq = seq;
    }

    public void setState(int state) {
        this.state = state;
    }
}
