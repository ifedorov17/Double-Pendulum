package ru.igor17.doublependulum.solver;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EuSolver extends BaseSolver {


    @Override
    public void solve() {
        int steps =(int)(this.T / this.dt);

        for (int i = 1; i <= steps; i++) {
            double theta1C = this.theta1.get(i-1);
            double theta2C = this.theta2.get(i-1);
            double omega1C = this.omega1.get(i-1);
            double omega2C = this.omega2.get(i-1);

            double theta1 = theta1C + this.dt * theta1Derivative(omega1C);
            double theta2 = theta2C + this.dt * theta2Derivative(omega2C);
            double omega1 = omega1C + this.dt * omega1Derivative(theta1C, theta2C, omega1C, omega2C);
            double omega2 = omega2C + this.dt * omega2Derivative(theta1C, theta2C, omega1C, omega2C);

            this.theta1.add(theta1);
            this.theta2.add(theta2);
            this.omega1.add(omega1);
            this.omega2.add(omega2);

            this.energy.add(calcEnergy(theta1, theta2, omega1, omega2));
        }
    }

}


