package entity;

public class CodeMatrix {

    private final MatrixCell[][] matrix;
    private int[] cellSelected;
    private String selectedCharacter;
    private boolean colAvailable = true;
    private final int matrixSpan;

    public CodeMatrix(MatrixCell[][] matrix, int span) {
        this.matrix = matrix;
        this.cellSelected = null;
        this.matrixSpan = span;

    }

    public int getMatrixSpan() {
        return matrixSpan;
    }

    public MatrixCell[][] getMatrix(){
        return matrix;
    }

    public MatrixCell getMatrixCell(int row, int col) {
        return matrix[row][col];
    }

    public void setCell(int row, int col, MatrixCell matrixCell) {
        this.matrix[row][col] = matrixCell;
    }

    public boolean isColAvailable() {
        return colAvailable;
    }

    public String getSelectedCharacter() {
        return selectedCharacter;
    }

    public void setCellSelected(int[] cellSelected) {
        this.cellSelected = cellSelected;
        selectedCharacter = matrix[cellSelected[0]][cellSelected[1]].getCode();
        matrix[cellSelected[0]][cellSelected[1]].setCode("[ ]");
        matrix[cellSelected[0]][cellSelected[1]].setSelected(true);
    }

    public void setOneRowAvailable() {
        colAvailable = false;
        for (MatrixCell[] matrixCells : matrix)
            if (!matrixCells[cellSelected[1]].isAvailable())
                matrixCells[cellSelected[1]].setAvailable(true);
    }

    public void setOneColAvailable() {
        colAvailable = true;
        for (int col = 0; col < matrix[cellSelected[0]].length; col++)
            if (!matrix[cellSelected[0]][col].isAvailable())
                matrix[cellSelected[0]][col].setAvailable(true);
    }

    public void disableAllCells() {
        for (MatrixCell[] matrixCells : matrix)
            for (MatrixCell matrixCell : matrixCells) if (matrixCell.isAvailable()) matrixCell.setAvailable(false);
    }
}
