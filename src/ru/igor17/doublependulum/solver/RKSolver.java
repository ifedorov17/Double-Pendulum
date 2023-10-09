package ru.igor17.doublependulum.solver;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RKSolver extends BaseSolver {

    @Override
    public void solve() {

        int steps = (int)(this.T / this.dt);

        for (int i = 1; i <= steps; i++) {
            double theta1C = theta1.get(i-1);
            double theta2C = theta2.get(i-1);
            double omega1C = omega1.get(i-1);
            double omega2C = omega2.get(i-1);

            double theta1K1 = theta1Derivative(omega1C);
            double theta2K1 = theta2Derivative(omega2C);
            double omega1K1 = omega1Derivative(theta1C, theta2C,omega1C, omega2C);
            double omega2K1 = omega2Derivative(theta1C, theta2C,omega1C, omega2C);

            double theta1K2 = theta1Derivative(omega1C+this.dt*omega1K1/2);
            double theta2K2 = theta1Derivative(omega2C+this.dt*omega2K1/2);
            double omega1K2 = omega1Derivative(theta1C+this.dt*theta1K1/2,theta2C+this.dt*theta2K1/2, omega1C+this.dt*omega1K1/2,omega2C+this.dt*omega2K1/2);
            double omega2K2 = omega2Derivative(theta1C+this.dt*theta1K1/2,theta2C+this.dt*theta2K1/2, omega1C+this.dt*omega1K1/2,omega2C+this.dt*omega2K1/2);

            double theta1K3 = theta1Derivative(omega1C+this.dt*omega1K2/2);
            double theta2K3 = theta2Derivative(omega2C+this.dt*omega2K2/2);
            double omega1K3 = omega1Derivative(theta1C+this.dt*theta1K2/2,theta2C+this.dt*theta2K2/2, omega1C+this.dt*omega1K2/2,omega2C+this.dt*omega2K2/2);
            double omega2K3 = omega2Derivative(theta1C+this.dt*theta1K2/2,theta2C+this.dt*theta2K2/2, omega1C+this.dt*omega1K2/2,omega2C+this.dt*omega2K2/2);

            double theta1K4 = theta1Derivative(omega1C+this.dt*omega1K3);
            double theta2K4 = theta2Derivative(omega2C+this.dt*omega2K3);
            double omega1K4 = omega1Derivative(theta1C+this.dt*theta1K3,theta2C+this.dt*theta2K3, omega1C+this.dt*omega1K3,omega2C+this.dt*omega2K3);
            double omega2K4 = omega2Derivative(theta1C+this.dt*theta1K3,theta2C+this.dt*theta2K3, omega1C+this.dt*omega1K3,omega2C+this.dt*omega2K3);

            double theta1 = theta1C + this.dt/6 * (theta1K1 + 2 * theta1K2 + 2 * theta1K3 + theta1K4);
            double theta2 = theta2C + this.dt/6 * (theta2K1 + 2 * theta2K2 + 2 * theta2K3 + theta2K4);
            double omega1 = omega1C + this.dt/6 * (omega1K1 + 2 * omega1K2 + 2 * omega1K3 + omega1K4);
            double omega2 = omega2C + this.dt/6 * (omega2K1 + 2 * omega2K2 + 2 * omega2K3 + omega2K4);

            this.theta1.add(theta1);
            this.theta2.add(theta2);
            this.omega1.add(omega1);
            this.omega2.add(omega2);

            this.energy.add(calcEnergy(theta1,theta2,omega1,omega2));

        }

    }

}
