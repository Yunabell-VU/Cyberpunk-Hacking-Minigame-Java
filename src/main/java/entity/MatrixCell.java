package entity;

//One single Tile/Code
//DO NOT modify this file!

import java.io.Serializable;

public class MatrixCell extends Cell implements Serializable {
    private boolean available = false;
    public final Coordinate coordinate;

    public
    MatrixCell(String code, Coordinate coordinate) {
        super(code);
        this.coordinate = coordinate;
    }

    public boolean
    isAvailable() {
        return available;
    }

    public void
    setAvailable(boolean available) {
        this.available = available;
    }
}
