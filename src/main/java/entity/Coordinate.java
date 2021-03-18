package entity;

import java.io.Serializable;

public class Coordinate implements Serializable {
    private int row;
    private int col;

    public
    Coordinate(int row, int col) {
        this.setRow(row);
        this.setCol(col);
    }

    public int
    getRow() {
        return row;
    }

    public void
    setRow(int row) {
        this.row = row;
    }

    public int
    getCol() {
        return col;
    }

    public void
    setCol(int col) {
        this.col = col;
    }
}
