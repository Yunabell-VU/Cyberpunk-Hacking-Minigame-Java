package entity;

//One single Tile/Code
//DO NOT modify this file!

import java.io.Serializable;

public class MatrixCell extends Cell implements Serializable {
    private boolean available = false;
    private final Coordinate coordinate;

    public
    MatrixCell(String code) {
        super(code);
        this.coordinate = new Coordinate(-1, -1);
    }

    public boolean
    isAvailable() {
        return available;
    }

    public void
    setAvailable(boolean available) {
        this.available = available;
    }

    public void
    setCoordinate(int x, int y) {
        coordinate.setRow(x);
        coordinate.setCol(y);
    }

    public Coordinate
    getCoordinate() {
        return coordinate;
    }
}
