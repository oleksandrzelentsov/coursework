package com.testing;

import com.matrices.Determinant;
import com.matrices.Matrix;
import com.visualisation.Action;
import com.visualisation.Menu;

import java.util.Scanner;

public class Main {
    public static void main (String[] args) {
        Menu menu = new Menu("CHOOSE THE ACTION");

        Action addition = new Action() {
            @Override
            public void perform() {
                Matrix m1 = new Matrix();
                m1.input();
                Matrix m2 = new Matrix();
                m2.input();
                try {
                    System.out.println(Matrix.add(m1, m2));
                }
                catch (RuntimeException re) {
                    System.out.println(re.getMessage());
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public String getName() {
                return "Addition";
            }
        };

        Action subtraction = new Action() {
            @Override
            public void perform() {
                Matrix m1 = new Matrix();
                m1.input();
                Matrix m2 = new Matrix();
                m2.input();
                try {
                    System.out.println(Matrix.subtract(m1, m2));
                }
                catch (RuntimeException re) {
                    System.out.println(re.getMessage());
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public String getName() {
                return "Subtraction";
            }
        };

        Action multiplication_m = new Action() {
            @Override
            public void perform() {
                Matrix m1 = new Matrix();
                m1.input();
                Matrix m2 = new Matrix();
                m2.input();
                try {
                    System.out.println(Matrix.multiply(m1, m2));
                }
                catch (RuntimeException re) {
                    System.out.println(re.getMessage());
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public String getName() {
                return "Matrix * Matrix multiplication";
            }
        };

        Action multiplication_n = new Action() {
            @Override
            public void perform() {
                Matrix m1 = new Matrix();
                m1.input();
                Scanner sc = new Scanner(System.in);
                System.out.print("Now enter the number to multiply to: ");
                double d = sc.nextDouble();

                try {
                    System.out.println(Matrix.multiply(m1, d));
                }
                catch (RuntimeException re) {
                    System.out.println(re.getMessage());
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public String getName() {
                return "Matrix * number multiplication";
            }
        };

        Action division = new Action() {
            @Override
            public void perform() {
                Matrix m1 = new Matrix();
                m1.input();
                Matrix m2 = new Matrix();
                m2.input();
                try {
                    System.out.println(Matrix.divide(m1, m2));
                }
                catch (RuntimeException re) {
                    System.out.println(re.getMessage());
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public String getName() {
                return "Division";
            }
        };

        Action determinant = new Action() {
            @Override
            public void perform() {
                Matrix m1 = new Matrix();
                m1.input();

                try {
                    System.out.println(Determinant.evaluateDeterminant(m1));
                }
                catch (RuntimeException re) {
                    System.out.println(re.getMessage());
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public String getName() {
                return "Determination";
            }
        };

        menu.addAction(subtraction);
        menu.addAction(addition);
        menu.addAction(multiplication_m);
        menu.addAction(multiplication_n);
        menu.addAction(division);
        menu.addAction(determinant);

        menu.mainLoop();
    }
}
