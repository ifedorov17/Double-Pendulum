package ru.igor17.doublependulum.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Segment {

    private double mass;

    private double length;

    private double theta;
    
    private double omega;

}
