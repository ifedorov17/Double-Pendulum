package ru.igor17.doublependulum.model;

import lombok.Getter;
import lombok.Setter;

import java.awt.Color;

import static ru.igor17.doublependulum.App.processingRef;

@Getter
@Setter
public class Pendulum {
    private Dot fixPoint;
    private double mass;
    private double length;
    private double theta;
    private double omega;
    private Color color;

    public Pendulum() {
        this.fixPoint = new Dot();
        this.mass = 1;
        this.length = 1;
        this.theta = 0;
        this.omega = 0;
        this.color = new Color(210, 10, 10);
    }

    public Pendulum (double mass, double length, double theta, double omega) {
        this.mass = mass;
        this.length = length;
        this.theta = theta;
        this.omega = omega;
        this.color = new Color(210, 10, 10);
    }

    public Pendulum (Dot fixPoint, double mass, double length, double theta, double omega) {
        this(mass, length, theta, omega);
        this.fixPoint = fixPoint;
    }

    public Dot getEndPoint() {
        return new Dot(this.fixPoint.getX() + this.length*Math.sin(this.theta), this.fixPoint.getY() + this.length*Math.cos(this.theta));
    }

    public void render() {
        Dot endPoint = getEndPoint();

        processingRef.stroke(this.color.getRGB());
        processingRef.strokeWeight(3);
        processingRef.line((float)this.fixPoint.getX(), (float)this.fixPoint.getY(), (float)endPoint.getX(), (float)endPoint.getY());
        processingRef.stroke(Color.CYAN.getRGB());
        processingRef.circle((float)this.fixPoint.getX(), (float)this.fixPoint.getY(), 5);
        processingRef.stroke(Color.ORANGE.getRGB());
        processingRef.circle((float)endPoint.getX(), (float)endPoint.getY(), 5);
    }


}
