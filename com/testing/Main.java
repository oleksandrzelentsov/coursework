package com.testing;

import com.matrices.Determinant;
import com.matrices.Matrix;
import com.visualisation.Action;
import com.visualisation.Menu;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        Action fileOutput = new Action() {

            @Override
            public void perform() {
                File f = new File("output.txt");
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                finally {
                    FileOutputStream f1 = null;
                    try {
                        f1 = new FileOutputStream(f);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                    finally {
                        Matrix m = new Matrix();
                        m.input();
                        try {
                            f1.write(m.toString().getBytes());
                            f1.close();
                        } catch (IOException | NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public String getName() {
                return "File output";
            }
        };

        Action fileInput = new Action() {
            @Override
            public void perform() {
                System.out.println("Specify the input file filename");
                String fn = new Scanner(System.in).next();
                InputStream sr = null;
                try {
                    sr = new FileInputStream(fn);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
                Matrix m = new Matrix();
                m.input(sr);
                System.out.println(m);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public String getName() {
                return "File input";
            }
        };

        Action exit = new Action() {
            @Override
            public void perform() {
                System.out.println("Bye");
                System.exit(0);
            }

            @Override
            public String getName() {
                return "Quit";
            }
        };

        Action[] actions = new Action[]{subtraction,
                addition, multiplication_m,
                multiplication_n, division, determinant,
                fileInput, fileOutput, exit};
        menu.addActions(actions);

        menu.mainLoop();
    }
}
