package com.matrices;

import java.util.Arrays;
import java.util.Scanner;

public class Matrix implements TwoDimensional<Double> {
    //table itself
    private Double[][] table;
    //dimensions
    private Integer rows, columns;

    // methods-accessors
    public Integer[] getDimensions() {
        return new Integer[]{rows, columns};
    }

    @Override
    public Double[][] getTable() {
        return table;
    }

    @Override
    public void setTable(Double[][] another) {
        this.table = another;
    }

    public boolean determinable() {
        return rows.intValue() == columns.intValue();
    }

    public void assign(TwoDimensional<Double> another) {
        // COPY all stuff from another
        this.table = new Double[another.getDimensions()[0]][another.getDimensions()[1]];

        this.rows = another.getDimensions()[0];
        this.columns = another.getDimensions()[1];

        for(int i = 0; i < another.getDimensions()[0]; ++i) {
            for(int j = 0; j < another.getDimensions()[1]; ++j) {
                set(i, j, another.get(i, j));
            }
        }
    }

    public static Matrix divide(Matrix m1, Matrix m2) {
        Matrix result = new Matrix(), temp = new Matrix();
        temp.assign(m2);
        temp.gaussian_invert();
        result.assign(multiply(m1, temp));
        return result;
    }

    public void divide(Matrix another) {
        assign(divide(this, another));
    }

    public void input() {
        System.out.println("Please, input the size of matrix in form:\nrows columns");
        Scanner sc = new Scanner(System.in);
        if(sc.hasNextInt())
            rows = sc.nextInt();
        else throw new RuntimeException("Entered not integer number");
        if (sc.hasNextInt())
            columns = sc.nextInt();
        else throw new RuntimeException("Entered not integer number");

        Double[][] arr = new Double[rows][columns];

        System.out.println("Input the matrix elements\nEach row on new console row\nElements separated by commas");
        for(int i = 0; i < rows; i++) {
//            System.out.println("now enter the "+i+"th row splitted by comma("+columns+" elements):");
            String new_Row = sc.next();
//            new_Row += " 3";
            String[] a = new_Row.split(",");
//            System.out.println(Arrays.toString(a));
            if(a.length != columns) throw new RuntimeException("Entered not "+columns+" numbers, but " + a.length+": "+Arrays.toString(a));
            for(int j = 0; j < columns; j++) {
                arr[i][j] = Double.parseDouble(a[j]);
            }
        }
        assign(new Matrix(arr));
    }

    @Override
    public TwoDimensional<Double> minor(int i, int j) {
        if (!determinable()) {
            throw new RuntimeException("Cannot take minor of non" +
                    " quadratic matrix.");
        }

        return new Determinant(table).minor(i, j);
    }

    public void deleteRow(int index) {
        if(index > rows) throw new RuntimeException("Cannot delete row" +
                " outside matrix.");
        Double[][] temp = new Double[rows-1][columns];
        int L = 0;
        for(int i = 0; i < rows; i++) {
            if(index != i)
                temp[L++] = table[i];
        }
        assign(new Matrix(temp));
    }

    public void deleteColumn(int index) {
        if(index > columns) throw new RuntimeException("Cannot delete" +
                " row outside matrix.");
        // create resulting array
        Double[][] temp = new Double[columns-1][rows];

        int L = 0;
        // transpose current matrix
        transpose();
        //write all rows except i-th one to resulting array
        for(int i = 0; i < rows; i++) {
            if(index != i)
                temp[L++] = table[i];
        }
        // create matrix object from array
        Matrix newMat = new Matrix(temp);
        // transpose it back to original
        newMat.transpose();
        // copy created matrix to this
        assign(newMat);
    }

    private static boolean checkForNaNAndInf(Matrix x) {
        for (int i = 0; i < x.rows; i++) {
            for (int j = 0; j < x.columns; j++) {
                if(x.get(i, j) == Double.NaN || Double.isInfinite(x.get(i, j))) {
                    return true;
                }
            }
        }
        return false;
    }

    public Matrix() {
        table = new Double[0][0];
        rows = columns = 0;
    }

    public Matrix(Double[][] table) {
        if (table == null) {
            throw new RuntimeException("Error creating table using " +
                    "table which equals to zero.");
        }
        this.table = table;
        rows = table.length;
        columns = table[0].length;
        if (rows.equals(0) || columns.equals(0)) {
            throw new RuntimeException("Cannot create Matrix object" +
                    " without any vectors.");
        }
    }

    public Matrix(int xDim, int yDim) {
        if (xDim <= 0 || yDim <= 0) {
            throw new RuntimeException("Error creating table with" +
                    " following dimensions: (" + xDim + ", " + yDim + ").");
        }
        this.table = new Double[xDim][yDim];
        rows = xDim;
        columns = yDim;
    }

    @Override
    public Double get(int i, int j) {
        if (i >= rows || j >= columns) {
            throw new RuntimeException("Matrix index out of range: " + i + " " + j);
        }
        return table[i][j];
    }

    @Override
    public void set(int i, int j, Double value) {
        if (i >= rows || j >= columns) {
            throw new RuntimeException("Matrix index out of range: " + i + " " + j);
        }
        table[i][j] = value;
    }

    public void transpose() {
        Double[][] newMat = new Double[columns][rows];
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newMat[j][i] = table[i][j];
            }
        }

        assign(new Matrix(newMat));
    }

    public static Matrix add(Matrix m1, Matrix m2) {
        if(!m1.getDimensions()[0].equals(m2.getDimensions()[0]) ||
                !m1.getDimensions()[1].equals(m2.getDimensions()[1])) {
            throw new RuntimeException("Cannot add matrices with" +
                    " not equal dimensions.");
        }
        int x = m1.getDimensions()[0], y = m1.getDimensions()[1];
        Matrix r = new Matrix(x, y);

        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                r.set(i, j, m1.get(i, j) + m2.get(i, j));
            }
        }

        return r;
    }

    public static Matrix multiply(Matrix matrix1, double number) {
        Matrix r = new Matrix(matrix1.getDimensions()[0],
                matrix1.getDimensions()[1]);
        for (int i = 0; i < matrix1.getDimensions()[0]; i++) {
            for (int j = 0; j < matrix1.getDimensions()[1]; j++) {
                r.set(i, j, matrix1.get(i, j) * number);
            }
        }
        return r;
    }

    public void multiply(Double value) {
        assign(Matrix.multiply(this, value));
    }

    public static Matrix subtract(Matrix m1, Matrix m2) {
        if(!m1.getDimensions()[0].equals(m2.getDimensions()[0]) ||
                !m1.getDimensions()[1].equals(m2.getDimensions()[1])) {
            throw new RuntimeException("Cannot subtract matrices with" +
                    " not equal dimensions.");
        }
        int x = m1.getDimensions()[0], y = m1.getDimensions()[1];
        Matrix r = new Matrix(x, y);

        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                r.set(i, j, m1.get(i, j) - m2.get(i, j));
            }
        }

        return r;
    }

    public static Matrix multiply(Matrix m1, Matrix m2) {
        if(!m1.getDimensions()[1].equals(m2.getDimensions()[0])) {
            throw new RuntimeException("Cannot multiply matrices with non" +
                    " equal dimensions.");
        }
        int x = m1.getDimensions()[0], y = m2.getDimensions()[1],
                INNER = m1.getDimensions()[1];

        Matrix r = new Matrix(x, y);

        for (int row = 0; row != x; ++row)
        {
            for (int col = 0; col != y; ++col)
            {
                int sum = 0;
                for (int inner = 0; inner != INNER; ++inner)
                {
                    sum += m1.get(row, inner) * m2.get(inner, col);
                }
                r.set(row, col, (double) sum);
            }
        }

        return r;
    }

    public void add(Matrix another) {
        try {
            assign(Matrix.add(this, another));
        }
        catch(Exception exc) {
            exc.printStackTrace();
            System.out.println("Error adding matrices.");
        }
    }

    public void subtract(Matrix another) {
        try {
            assign(Matrix.subtract(this, another));
        }
        catch(Exception exc) {
            exc.printStackTrace();
            System.out.println("Error subtracting matrices.");
        }
    }

    public void multiply(Matrix another) {
        try {
            assign(Matrix.multiply(this, another));
        }
        catch(Exception exc) {
            exc.printStackTrace();
            System.out.println("Error multiplying matrices.");
        }
    }

    @Override
    public String toString() {
            StringBuilder r = new StringBuilder();
        r.append("{\n");
        for (int i = 0; i < rows; i++) {
            r.append("{");
            for (int j = 0; j < columns; j++) {
                if (j == columns - 1) {
                    r.append(get(i, j));
                }
                else {
                    r.append(get(i, j)).append("\t\t");
                }
            }
            r.append("}\n");
        }
        r.append("}");
        return r.toString();
    }

    @Deprecated
    public void invert() {
        if(!determinable()) {
            throw new RuntimeException("Cannot invert not quadratic matrix.");
        }
        if(Determinant.evaluateDeterminant(this) == 0) {
            throw new RuntimeException("Matrix could not be inverted because" +
                    " its determinant is 0.");
        }
        Matrix temp = new Matrix(getTable());
        for (int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                Determinant d = new Determinant(temp.getTable());
                d = d.cofactor(i, j);
                Double p = Determinant.evaluateDeterminant(d);
                this.set(i, j, p);
            }
        }
        this.transpose();
        this.multiply(1.0 / Determinant.evaluateDeterminant(temp));
    }

    public void gaussian_invert() {
        if(!this.determinable() || Determinant.evaluateDeterminant(this) == 0) {
            throw new RuntimeException("Matrix could not be" +
                    " inverted because its determinant is 0 or it has no determinant.");
        }
        //matrix for finding rank
        Matrix temp = new Matrix(rows, columns * 2);

        //write the current matrix to first part of result
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                temp.set(i, j, get(i, j));
            }
        }

        //calculate the unary diagonal matrix
        Matrix L = Matrix.getEquivalentUnaryMatrix(this);
        for (int i = 0; i < rows; i++) {
            for (int j = columns; j < columns * 2; j++) {
                temp.set(i, j, L.get(i, j-columns));
            }
        }

        temp = getEquivalent(temp);

	for(int i = 0; i < columns; ++i) {
	    temp.deleteColumn(0);
	}
        assign(temp);
    }

    public static Matrix getEquivalentUnaryMatrix(Matrix m) {
        Matrix res = new Matrix(m.rows, m.columns);
        for (int i = 0; i < res.rows; i++) {
            for (int j = 0; j < res.columns; j++) {
//                System.out.println("setting");
                res.set(i, j, (i == j) ? 1.0 : 0.0);
            }
        }
        return res;
    }

    public static Matrix getEquivalent(Matrix m) {
        Matrix result = new Matrix();

        result.assign(m);


        for (int i = 0; i < result.rows; i++) { //
//            System.out.println("processing "+i+"th column:\n" + result);
            if(result.get(i, i) == 0) {
                throw new RuntimeException("Cannot invert matrix!");
            }
            if(result.get(i, i) != 1) {
//                System.out.println(i+"th and "+i+"th element != 1");
//                    код для деления текущей строки на такое число,
//                    чтобы получить все единицы на диагонали
                Double temp = result.get(i, i);
//                System.out.println("dividing this row by the "+result.get(i, i));
                for (int j = i; j < result.columns; ++j) {
//                    System.out.println("dividing "+result.get(i, j)+" by "+temp);
                    result.set(i, j, result.get(i, j) / temp);
                    if(checkForNaNAndInf(result)) {
                        throw new RuntimeException("Error in getting" +
                                " equivalence for rang search.");
                    }
                }
//                System.out.println("we have now the" +
//                        " diagonal element = 1\n"
//                        + result);
            }
            //код для обнуления всех элементов которые стоят
            // ниже или правее элемента ii

//            System.out.println(result);
//            System.out.println("then, work with rows");
            for (int j = 0; j < result.rows; j++) {
                if(j == i) continue;
//                System.out.println("so, the "+j+"th row");
                Double temp = -result.get(j, i);
//                System.out.println("the "+i+"th row will be multiplied by "+temp+
//                " and added to " + j + "th row");
                for(int k = i; k < result.columns; ++k) {
//                    System.out.print("multiply " + result.get(i, k) + " to "+
//                            temp + " and plus " + result.get(j, k)
//                            +".\nso we change the "
//                            + result.get(j, k) + " ");
                    result.set(j, k, result.get(i, k) * temp +
                            result.get(j, k));
//                    System.out.println(" to "+result.get(j, k));
                }
            }
//            System.out.println("so, we couped with "+ i +
//            "th column\n"+ result);

        }
//        System.out.println("Returning from getEquiv:\n" + result);
        return result;
    }
}
