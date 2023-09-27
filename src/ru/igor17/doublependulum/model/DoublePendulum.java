package ru.igor17.doublependulum.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DoublePendulum {

    private PendulumSegment firstSeg;

    private PendulumSegment secondSeg;

}
