package entity;

public class Matrix {

    private MatrixCell[][] matrix;

    public Matrix(MatrixCell[][] matrix) {
        this.matrix = matrix;
    }

    public MatrixCell[][] getMatrix() {
        return matrix;
    }
}
