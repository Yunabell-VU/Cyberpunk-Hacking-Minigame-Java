package entity;

import java.io.Serializable;

public class CodeMatrix implements Serializable {

    private final MatrixCell[][] matrix;
    private MatrixCell cellPicked;
    private String pickedCharacter;
    private boolean rowAvailable = true;
    private final int matrixSpan;

    public CodeMatrix(MatrixCell[][] matrix, int span) {
        this.matrix = matrix;
        this.cellPicked = null;
        this.pickedCharacter = null;
        this.matrixSpan = span;
    }

    public int getMatrixSpan() {
        return matrixSpan;
    }

    public MatrixCell getMatrixCell(int row, int col) {
        return matrix[row][col];
    }

    public String getPickedCharacter() {
        return pickedCharacter;
    }

    public void setCell(int row, int col, MatrixCell matrixCell) {
        this.matrix[row][col] = matrixCell;
    }

    public boolean isRowAvailable() {
        return rowAvailable;
    }

    public void updateCellPicked(Coordinate coordinate) {
        this.cellPicked = getMatrixCell(coordinate.getRow(), coordinate.getCol());
        pickedCharacter = cellPicked.getCode();
        cellPicked.setCode("[ ]");
        cellPicked.setSelected(true);
    }

    public void setOneColAvailable() {
        rowAvailable = false;
        for (MatrixCell[] matrixCells : matrix)
            if (!matrixCells[cellPicked.getCoordinate().getCol()].isAvailable())
                matrixCells[cellPicked.getCoordinate().getCol()].setAvailable(true);
    }

    public void setOneRowAvailable() {
        rowAvailable = true;
        for (int col = 0; col < matrix[cellPicked.getCoordinate().getRow()].length; col++)
            if (!matrix[cellPicked.getCoordinate().getRow()][col].isAvailable())
                matrix[cellPicked.getCoordinate().getRow()][col].setAvailable(true);
    }

    public void disableAllCells() {
        for (MatrixCell[] matrixCells : matrix)
            for (MatrixCell matrixCell : matrixCells) if (matrixCell.isAvailable()) matrixCell.setAvailable(false);
    }
}
