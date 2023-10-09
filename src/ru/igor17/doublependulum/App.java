package ru.igor17.doublependulum;

import processing.core.PApplet;
import ru.igor17.doublependulum.representation.PendulumSim;

import static ru.igor17.doublependulum.representation.SimulationProperties.DIM_X_GRAPHICS;
import static ru.igor17.doublependulum.representation.SimulationProperties.DIM_Y_GRAPHICS;
import static ru.igor17.doublependulum.representation.SimulationProperties.SIMULATION_HEIGHT;
import static ru.igor17.doublependulum.representation.SimulationProperties.SIMULATION_WIDTH;

public class App extends PApplet {

    public static PApplet processingRef;
    public PendulumSim simulation;
    public static final double G = 9.81;

    @Override
    public void settings() {
        size(SIMULATION_WIDTH + DIM_X_GRAPHICS, SIMULATION_HEIGHT);
    }

    @Override
    public void setup() {
        background(0);
        frameRate(60);
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
