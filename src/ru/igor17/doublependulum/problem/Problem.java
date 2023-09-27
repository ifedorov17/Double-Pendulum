package ru.igor17.doublependulum.problem;

import lombok.Getter;
import lombok.Setter;
import ru.igor17.doublependulum.model.PendulumSegment;

import java.util.ArrayList;


@Getter
@Setter
public class Problem {

    private ArrayList<PendulumSegment> pendulumSegments;

    private SolveParameters solveParameters;

}
