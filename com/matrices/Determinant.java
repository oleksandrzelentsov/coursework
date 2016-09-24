package com.matrices;

public class Determinant implements TwoDimensional<Double> {
    Double[][] table;
    Integer size;

    public static Determinant getZero(int SIZE) {
        Determinant det = new Determinant(SIZE);
        for(int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                det.set(i, j, 0.0);
            }
        }
        return det;
    }

    @Override
    public Double get(int i, int j) {
        return table[i][j];
    }

    @Override
    public void set(int i, int j, Double value) {
        table[i][j] = value;
    }

    @Override
    public boolean determinable() {
        return true;
    }

    @Override
    public Integer[] getDimensions() {
        return new Integer[]{ size, size };
    }

    private static <T> boolean determinable(T[][] matrix) {
        return matrix.length == matrix[0].length;
    }

    @Override
    public void assign(TwoDimensional<Double> another) {
        this.table = another.getTable();
        this.size = another.getDimensions()[0];
    }

    @Override
    public void multiply(Double value) {
        for (int i = 0; i < size; i++) {
            set(0, i, get(0, i) * value);
        }
    }

    @Override
    public Double[][] getTable() {
        return table;
    }

    @Override
    public void setTable(Double[][] another) {
        this.table = another;
    }

    public Determinant minor(int i, int j) {
        if(i >= size || j >= size) {
            throw new RuntimeException("There is no element with indices " + i + " " + j);
        }
        Matrix mat = new Matrix(getTable());
        mat.deleteRow(i);
        mat.deleteColumn(j);
        return (new Determinant(mat.getTable()));
    }

    public Determinant cofactor(int i, int j) {
        Matrix m = new Matrix(getTable());
        m.multiply(Math.pow(-1, i+j));
        return new Determinant(m.getTable());
    }

    public static double evaluateDeterminant(TwoDimensional<Double> another) {
        if (!another.determinable()) {
            throw new RuntimeException("Cannot find the determinant of not quadratic matrix.");
        }
        if(another.getDimensions()[0] == 2) {
            return another.get(0, 0) * another.get(1,1) - another.get(0, 1) * another.get(1, 0);
        }
        else {
            int size = another.getDimensions()[0];
            double result = 0;
            for (int i = 0; i < size; i++) {
                result += another.get(i, 0) *
                          Math.pow(-1, i) *
                          evaluateDeterminant(another.minor(i, 0));
            }
            return result;
        }
    }

    public Determinant(int size) {
        table = new Double[size][size];
        this.size = size;
    }

    public Determinant(Double[][] table) {
        if(!determinable(table))
            throw new RuntimeException("Determinant could not be not quadratic.");
        this.table = table;
        size = table.length;
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        r.append("{\n");
        for (int i = 0; i < size; i++) {
            r.append("|");
            for (int j = 0; j < size; j++) {
                if (j == size - 1) {
                    r.append(get(i, j));
                }
                else {
                    r.append(get(i, j)).append(" ");
                }
            }
            r.append("|\n");
        }
        r.append("}");
        return r.toString();
    }
}
