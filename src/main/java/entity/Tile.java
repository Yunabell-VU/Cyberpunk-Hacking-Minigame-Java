package entity;

public class Tile {
    public final static int AVAILABLE = 1111;
    public final static int UNAVAILABLE = 2222;
    public final static int SELECTED = 3333;
    public final static int WAITING = 4444;//NAME
    public final static int ADDED = 5555;//NAME

    public String code;
    public int state;

    public Tile(String c, int s){
        code = c;
        state = s;
    }
}
