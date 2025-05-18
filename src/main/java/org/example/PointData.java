package org.example;

public class PointData {
    private final double x;
    private final double y;
    private final boolean white;

    public PointData(double x, double y, boolean white) {
        this.x = x;
        this.y = y;
        this.white = white;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isWhite() {
        return white;
    }
}