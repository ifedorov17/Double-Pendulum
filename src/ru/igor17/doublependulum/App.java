package ru.igor17.doublependulum;

import processing.core.PApplet;
import ru.igor17.doublependulum.representation.PendulumSim;

public class App extends PApplet {

    public static PApplet processingRef;
    public PendulumSim simulation;
    public static double G = 9.81;
    public void settings() {
        size(2000, 1000);
    }

    @Override
    public void setup() {
        background(0);
        frameRate(1000);
        processingRef = this;
        this.simulation = new PendulumSim("resources/problem.json");
        if (this.simulation.isError()) return;
        this.simulation.solve();
    }

    @Override
    public void draw() {
        if (!this.simulation.isError()) this.simulation.animate();
    }

    @Override
    public void mouseClicked() {
        this.simulation.resetAnimationTick();
    }

    public static void main(String[] args) {
        PApplet.main("ru.igor17.doublependulum.App");
    }
}
