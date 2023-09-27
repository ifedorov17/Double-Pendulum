package ru.igor17.doublependulum.representation;

import ru.igor17.doublependulum.model.Dot;
import ru.igor17.doublependulum.model.DoublePendulum;
import ru.igor17.doublependulum.model.PendulumSegment;
import ru.igor17.doublependulum.problem.Initializer;
import ru.igor17.doublependulum.problem.Problem;
import ru.igor17.doublependulum.solver.EuSolver;
import ru.igor17.doublependulum.solver.RKSolver;

import java.awt.Color;

import static ru.igor17.doublependulum.App.processingRef;

public class PendulumSim {

    public static final Color SEGMENT_COLOR = new Color(20, 150, 170);

    public static final Dot FIRST_FIX_POINT = new Dot(500, 500);

    private EuSolver euSolver;

    private RKSolver rkSolver;

    private DoublePendulum dPendulum;

    private int solverType;

    private int tick;

    private int animLength;

    private double animStep;

    private boolean error;

    private TimeGraph theta1Graph;

    private TimeGraph theta2Graph;

    private TimeGraph omega1Graph;

    private TimeGraph omega2Graph;

    private TimeGraph energyGraph;


    public PendulumSim (String problemFile) {
        this.tick = 0;
        this.error = false;

        final Initializer initializer = new Initializer(problemFile);

        try {
            initializer.readProblem();
        } catch (Exception exception) {
            exception.printStackTrace();
            this.error = true;
        }

        if(!error) {
            Problem problem = initializer.getProblem();
            problem.getPendulumSegments().get(0).setFixPoint(FIRST_FIX_POINT);

            this.dPendulum = new DoublePendulum(problem.getPendulumSegments().get(0),
                    problem.getPendulumSegments().get(1));

            switch (problem.getSolveParameters().getType()) {
                case "Euler" -> {
                    this.solverType = 0;
                    this.euSolver = new EuSolver();
                    this.euSolver.setDoublePendulum(this.dPendulum);
                    this.euSolver.setT(problem.getSolveParameters().getT());
                    this.euSolver.setDt(problem.getSolveParameters().getDt());
                }
                case "Runge-Kutta" -> {
                    this.solverType = 1;
                    this.rkSolver = new RKSolver();
                    this.rkSolver.setDoublePendulum(this.dPendulum);
                    this.rkSolver.setT(problem.getSolveParameters().getT());
                    this.rkSolver.setDt(problem.getSolveParameters().getDt());
                }
            }

            int graphCap = 250;

            this.animLength =(int) (problem.getSolveParameters().getT() / problem.getSolveParameters().getDt());

            this.animStep = (double) this.animLength / (double) graphCap;

            ScaleSynchronizer thetaScaleSynchronizer = new ScaleSynchronizer();

            this.theta1Graph = new TimeGraph(1000, 300, graphCap);
            this.theta1Graph.setTitle("Theta");
            this.theta1Graph.setOrigin(1000, 0);
            this.theta1Graph.setPlainCl(new Color(0, 0, 0));
            this.theta1Graph.setBorderCl(new Color(100, 100, 100));
            this.theta1Graph.setDotCl(Color.GREEN);
            this.theta1Graph.setLineCl(Color.GREEN);
            this.theta1Graph.setLevelLineCl(Color.GREEN);
            this.theta1Graph.setValueTextCl(Color.WHITE);
            this.theta1Graph.setTitleTextCl(Color.WHITE);
            this.theta1Graph.setScaleTextCl(Color.WHITE);
            this.theta1Graph.setTextSize(12);
            this.theta1Graph.setInteger(false);
            this.theta1Graph.setScaleSynchronizer(thetaScaleSynchronizer);

            this.theta2Graph = new TimeGraph(1000, 300, graphCap);
            this.theta2Graph.setTitle("");
            this.theta2Graph.setOrigin(1000, 0);
            this.theta2Graph.setPlainCl(new Color(0, 0, 0, 0));
            this.theta2Graph.setBorderCl(new Color(100, 100, 100));
            this.theta2Graph.setDotCl(Color.MAGENTA);
            this.theta2Graph.setLineCl(Color.MAGENTA);
            this.theta2Graph.setLevelLineCl(Color.MAGENTA);
            this.theta2Graph.setValueTextCl(Color.WHITE);
            this.theta2Graph.setTitleTextCl(Color.WHITE);
            this.theta2Graph.setScaleTextCl(Color.WHITE);
            this.theta2Graph.setTextSize(12);
            this.theta2Graph.setInteger(false);
            this.theta2Graph.setScaleSynchronizer(thetaScaleSynchronizer);

            thetaScaleSynchronizer.addGraph(theta1Graph);
            thetaScaleSynchronizer.addGraph(theta2Graph);

            ScaleSynchronizer omegaScaleSynchronizer = new ScaleSynchronizer();

            this.omega1Graph = new TimeGraph(1000, 300, graphCap);
            this.omega1Graph.setTitle("Omega");
            this.omega1Graph.setOrigin(1000, 300);
            this.omega1Graph.setPlainCl(new Color(0, 0, 0));
            this.omega1Graph.setBorderCl(new Color(100, 100, 100));
            this.omega1Graph.setDotCl(Color.GREEN);
            this.omega1Graph.setLineCl(Color.GREEN);
            this.omega1Graph.setLevelLineCl(Color.GREEN);
            this.omega1Graph.setValueTextCl(Color.WHITE);
            this.omega1Graph.setTitleTextCl(Color.WHITE);
            this.omega1Graph.setScaleTextCl(Color.WHITE);
            this.omega1Graph.setTextSize(12);
            this.omega1Graph.setInteger(false);
            this.omega1Graph.setScaleSynchronizer(omegaScaleSynchronizer);

            this.omega2Graph = new TimeGraph(1000, 300, graphCap);
            this.omega2Graph.setTitle("");
            this.omega2Graph.setOrigin(1000, 300);
            this.omega2Graph.setPlainCl(new Color(0, 0, 0, 0));
            this.omega2Graph.setBorderCl(new Color(100, 100, 100));
            this.omega2Graph.setDotCl(Color.MAGENTA);
            this.omega2Graph.setLineCl(Color.MAGENTA);
            this.omega2Graph.setLevelLineCl(Color.MAGENTA);
            this.omega2Graph.setValueTextCl(Color.WHITE);
            this.omega2Graph.setTitleTextCl(Color.WHITE);
            this.omega2Graph.setScaleTextCl(Color.WHITE);
            this.omega2Graph.setTextSize(12);
            this.omega2Graph.setInteger(false);
            this.omega2Graph.setScaleSynchronizer(omegaScaleSynchronizer);

            omegaScaleSynchronizer.addGraph(omega1Graph);
            omegaScaleSynchronizer.addGraph(omega2Graph);
        }
    }

    public boolean isError (){
        return error;
    }

    public void solve() {
        switch (this.solverType) {
            case 0 -> {
                this.euSolver.solve();
            }
            case 1 -> {
                this.rkSolver.solve();
            }
        }
    }

    public void resetAnimationTick() {
        this.tick = 0;
        this.theta1Graph.clear();
        this.theta2Graph.clear();
        this.omega1Graph.clear();
        this.omega2Graph.clear();
        this.energyGraph.clear();
    }

    public void animate() {
        if (this.tick > this.animLength) {
            return;
        }

        processingRef.background(0);
        double theta1 = 0;
        double theta2 = 0;
        double omega1 = 0;
        double omega2 = 0;
        double energy = 0;
        switch (this.solverType) {
            case 0 -> {
                theta1 = this.euSolver.getTheta1().get(this.tick);
                theta2 = this.euSolver.getTheta2().get(this.tick);
                omega1 = this.euSolver.getOmega1().get(this.tick);
                omega2 = this.euSolver.getOmega2().get(this.tick);
                energy = this.euSolver.getEnergy().get(this.tick);
            }
            case 1 -> {
                theta1 = this.rkSolver.getTheta1().get(this.tick);
                theta2 = this.rkSolver.getTheta2().get(this.tick);
                omega1 = this.rkSolver.getOmega1().get(this.tick);
                omega2 = this.rkSolver.getOmega2().get(this.tick);
            }
        }

        this.dPendulum.getFirstSeg().setTheta(theta1);
        this.dPendulum.getSecondSeg().setTheta(theta2);
        this.dPendulum.getFirstSeg().setOmega(omega1);
        this.dPendulum.getSecondSeg().setOmega(omega2);

        this.dPendulum.getSecondSeg().setFixPoint(this.dPendulum.getFirstSeg().getEndPoint());

        if(this.tick % this.animStep == 0) {
            this.theta1Graph.addValue(theta1);
            this.theta2Graph.addValue(theta2);
            this.omega1Graph.addValue(omega1);
            this.omega2Graph.addValue(omega2);
        }

        renderDoublePendulum(dPendulum);
        this.theta1Graph.render();
        this.theta2Graph.render();
        this.omega1Graph.render();
        this.omega2Graph.render();
        this.tick += 1;
    }

    private void renderDoublePendulum(final DoublePendulum doublePendulum) {

        final PendulumSegment firstSegment = doublePendulum.getFirstSeg();

        Dot endPoint1 = firstSegment.getEndPoint();

        processingRef.stroke(SEGMENT_COLOR.getRGB());
        processingRef.strokeWeight(3);

        processingRef.line(
                (float)firstSegment.getFixPoint().getX(),
                (float)firstSegment.getFixPoint().getY(),
                (float)endPoint1.getX(),
                (float)endPoint1.getY());

        processingRef.stroke(Color.CYAN.getRGB());
        processingRef.circle((float)firstSegment.getFixPoint().getX(),
                (float)firstSegment.getFixPoint().getY(), 5);
        processingRef.stroke(Color.ORANGE.getRGB());
        processingRef.circle((float)endPoint1.getX(), (float)endPoint1.getY(), 5);


        final PendulumSegment secondSegment = doublePendulum.getSecondSeg();

        Dot endPoint2 = secondSegment.getEndPoint();

        processingRef.stroke(SEGMENT_COLOR.getRGB());
        processingRef.strokeWeight(3);

        processingRef.line(
                (float)secondSegment.getFixPoint().getX(),
                (float)secondSegment.getFixPoint().getY(),
                (float)endPoint2.getX(),
                (float)endPoint2.getY());

        processingRef.stroke(Color.CYAN.getRGB());
        processingRef.circle((float)secondSegment.getFixPoint().getX(),
                (float)secondSegment.getFixPoint().getY(), 5);
        processingRef.stroke(Color.ORANGE.getRGB());
        processingRef.circle((float)endPoint2.getX(), (float)endPoint2.getY(), 5);
    }
}
