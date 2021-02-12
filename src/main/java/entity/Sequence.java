package entity;

import java.util.ArrayList;

public class Sequence {
    public final static int UNCHECK = 6666;
    public final static int SUCCESS = 7777;
    public final static int FAIL = 8888;

    public ArrayList<Tile> sequence;
    public int state;

    public Sequence(ArrayList<Tile>sequence, int state){
        this.sequence = sequence;
        this.state = state;
    }
}
