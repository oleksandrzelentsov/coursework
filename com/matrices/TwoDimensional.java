package com.matrices;

public interface TwoDimensional<T> {
    T get(int i, int j);

    void set(int i, int j, T value);

    boolean determinable();

    Integer[] getDimensions();

    void assign(TwoDimensional<T> another);

    void multiply(T value);

    T[][] getTable();

    void setTable(T[][] another);

    TwoDimensional<T> minor(int i, int j);
}
