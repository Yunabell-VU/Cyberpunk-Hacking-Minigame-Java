package entity;

public class Tile {
    public String code;
    public int state;

    public Tile(String c, int s){
        code = c;
        state = s;
    }
    public void resetState(int newState){
        this.state = newState;
    }

}
