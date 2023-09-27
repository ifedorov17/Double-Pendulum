package ru.igor17.doublependulum.model;

public class Dot {
    private double x;
    private double y;

    public Dot() {
        this.x = 0;
        this.y = 0;
    }

    public Dot(double x, double y) {
        this.x = x;
        this.y = y;
    }

    //Getters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    //Setters
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setXY(Dot dot) {
        this.x = dot.getX();
        this.y = dot.getY();
    }

    //Methods


}
