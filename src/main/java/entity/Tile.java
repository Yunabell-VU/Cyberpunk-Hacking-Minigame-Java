package entity;

//One single Tile/Code
//DO NOT modify this file!

public class Tile {
    public final static int AVAILABLE = 1111;
    public final static int UNAVAILABLE = 2222;
    public final static int SELECTED = 3333;
    public final static int WAITING = 4444;//NAME
    public final static int ADDED = 5555;//NAME

    private String code;
    private int state;

    public Tile(String c, int s){
        code = c;
        state = s;
    }

    public int getState() {
        return state;
    }

    public String getCode() {
        return code;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
