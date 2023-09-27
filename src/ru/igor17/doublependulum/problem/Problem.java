package ru.igor17.doublependulum.problem;

import ru.igor17.doublependulum.model.Segment;
import ru.igor17.doublependulum.solver.Solver;

public class Problem {
    private String title;
    private Segment[] segments;
    private Solver solver;
    public Segment[] getSegments() {
        return segments;
    }

    public Solver getSolver() {
        return solver;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSegments(Segment[] segments) {
        this.segments = segments;
    }

    public void setSolver(Solver solver) {
        this.solver = solver;
    }
}
