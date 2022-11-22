package me.frandma.box.utils;

public class Cube {
    private int x1;
    private int y1;
    private int z1;
    private int x2;
    private int y2;
    private int z2;

    public Cube(int x1, int y1, int z1, int x2, int y2, int z2) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;

        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }

    public Cube(String cube) {
        String[] cubeS = cube.split(";");


        for (String s : cubeS) {
            if (!isInteger(s)) throw new IllegalArgumentException();
        }

        x1 = Integer.parseInt(cubeS[0]);
        y1 = Integer.parseInt(cubeS[1]);
        z1 = Integer.parseInt(cubeS[2]);

        x2 = Integer.parseInt(cubeS[3]);
        y2 = Integer.parseInt(cubeS[4]);
        z2 = Integer.parseInt(cubeS[5]);
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getZ1() {
        return z1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public int getZ2() {
        return z2;
    }

    public String toString() {
        return x1 + ";" + y1 + ";" + z1 + ";" + x2 + ";" + y2 + ";" + z2 + ";";
    }
}
