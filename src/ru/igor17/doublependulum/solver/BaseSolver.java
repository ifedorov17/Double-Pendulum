package ru.igor17.doublependulum.solver;

import lombok.Getter;
import lombok.Setter;
import ru.igor17.doublependulum.model.DoublePendulum;

import java.util.ArrayList;

import static ru.igor17.doublependulum.App.G;

@Getter
@Setter
public abstract class BaseSolver {

	protected ArrayList<Double> theta1;

	protected ArrayList<Double> theta2;

	protected ArrayList<Double> omega1;

	protected ArrayList<Double> omega2;

	protected DoublePendulum doublePendulum;

	protected double T;

	protected double dt;

	public abstract void solve();
	public void setDoublePendulum(DoublePendulum doublePendulum) {
		this.doublePendulum = doublePendulum;
		this.theta1 = new ArrayList<>();
		this.theta2 = new ArrayList<>();
		this.omega1 = new ArrayList<>();
		this.omega2 = new ArrayList<>();

		this.theta1.add(doublePendulum.getFirstSeg().getTheta());
		this.theta2.add(doublePendulum.getSecondSeg().getTheta());
		this.omega1.add(doublePendulum.getFirstSeg().getOmega());
		this.omega2.add(doublePendulum.getSecondSeg().getOmega());

	}

  
	protected double omega1Derivative(double theta1, double theta2, double omega1, double omega2) {
		double m1 = this.doublePendulum.getFirstSeg().getMass();
		double m2 = this.doublePendulum.getSecondSeg().getMass();
		double l1 = this.doublePendulum.getFirstSeg().getLength();
		double l2 = this.doublePendulum.getSecondSeg().getLength();
		return (
				9 * omega1 * omega1 * l1 * m2 * Math.sin(2 * theta1 - 2 * theta2)
						+ 12 * m2 * l2 * omega2 * omega2 * Math.sin(theta1 - theta2)
						+ 12 * (3 * Math.sin(- 2 * theta2 + theta1) * m2 / 4 + Math.sin(theta1) * (m1 + 5 * m2 / 4))
						* G

		)
				/ (
				l1 * (9 * m2 * Math.cos(2 * theta1 - 2 * theta2) - 8 * m1 - 15 * m2)
		);
	}

	protected double omega2Derivative(double theta1, double theta2, double omega1, double omega2) {
		double m1 = this.doublePendulum.getFirstSeg().getMass();
		double m2 = this.doublePendulum.getSecondSeg().getMass();
		double l1 = this.doublePendulum.getFirstSeg().getLength();
		double l2 = this.doublePendulum.getSecondSeg().getLength();
		return (
				- 9 * G * Math.sin(2 * theta1 - theta2) * (m1 + 2 * m2)
						- 9 * m2 * l2 * omega2 * omega2 * Math.sin(2 * theta1 - 2 * theta2)
						- 12 * l1 * omega1 * omega1 * (m1 + 3 * m2) * Math.sin(theta1 - theta2)
						+ 3 * G * Math.sin(theta2) * (m1 + 6 * m2)
		)
				/ (
				l2 * (9 * m2 * Math.cos(2 * theta1 - 2 * theta2) - 8 * m1 - 15 * m2)
		);
	}

	protected double theta1Derivative(double omega1) {
		return omega1;
	}

	protected double theta2Derivative(double omega2) {
		return omega2;
	}

}
