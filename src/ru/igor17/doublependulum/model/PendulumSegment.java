package ru.igor17.doublependulum.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PendulumSegment {
    private Dot fixPoint;
    private double mass;
    private double length;
    private double theta;
    private double omega;

    public PendulumSegment(double mass, double length, double theta, double omega) {
        this.mass = mass;
        this.length = length;
        this.theta = theta;
        this.omega = omega;
    }

    public PendulumSegment(Dot fixPoint, double mass, double length, double theta, double omega) {
        this(mass, length, theta, omega);
        this.fixPoint = fixPoint;
    }

    public Dot getEndPoint() {
        return new Dot(this.fixPoint.getX() + this.length*Math.sin(this.theta), this.fixPoint.getY() + this.length*Math.cos(this.theta));
    }

}
