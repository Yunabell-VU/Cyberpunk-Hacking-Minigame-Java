package entity;

//One single Tile/Code
//DO NOT modify this file!

public class MatrixCell extends Cell {
    private boolean available = false;
    private Coordinate coordinate;

    public MatrixCell(String code) {
        super(code);
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setCoordinate(int x, int y){
        coordinate.x = x;
        coordinate.y = y;
    }

    public Coordinate getCoordinate(){
        return coordinate;
    }
}
