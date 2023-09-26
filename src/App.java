import processing.core.*;
import controlP5.*;

import java.io.IOException;

public class App extends PApplet{

    public static int renderType = 0;

    public static PApplet processingRef;
    public PendulumSim simulation;
    public static double G = 9.81;
    public void settings() {
        size(2000, 1000);
    }

    public void setup() {
        background(0);
        frameRate(1000);
        processingRef = this;
        this.simulation = new PendulumSim("problem.json");
        if (this.simulation.isError()) return;
        this.simulation.solve();
    }

    public void draw() {
        if (!this.simulation.isError()) this.simulation.animate();
    }

    public void mouseClicked() {
        this.simulation.resetAnimationTick();
    }

    public static void main(String[] args) {
        PApplet.main("App");
    }
}
