package entity;

//One single Tile/Code
//DO NOT modify this file!

public class MatrixCell extends Cell {
    private boolean available = false;

    public MatrixCell(String code) {
        super(code);
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
