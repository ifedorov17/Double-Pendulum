package ru.igor17.doublependulum.representation;

import ru.igor17.doublependulum.model.Dot;

import java.awt.*;

public class SimulationProperties {

	public static final int DIM_X_GRAPHICS = 800;

	public static final int DIM_Y_GRAPHICS = 300;

	public static final int SIMULATION_WIDTH = 600;

	public static final Dot FIRST_FIX_POINT = new Dot(SIMULATION_WIDTH/2, DIM_Y_GRAPHICS);

	public static final Color BORDER_COLOR = new Color(100, 100, 100);

	public static final Color WHITE_PLAIN_COLOR = new Color(255, 255, 255);

	public static final Color BLACK_PLAIN_COLOR = new Color(0, 0, 0, 0);

	public static final Color PENDULUM_DOT_COLOR = Color.RED;

	public static final Color SEGMENT_COLOR = new Color(170, 150, 170);

}
