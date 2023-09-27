package ru.igor17.doublependulum.problem;

import lombok.Getter;
import lombok.Setter;
import ru.igor17.doublependulum.model.Segment;
import ru.igor17.doublependulum.solver.Solver;

@Getter
@Setter
public class Problem {

    private Segment[] segments;

    private Solver solver;

}
