package ru.igor17.doublependulum.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DoublePendulum {
    private Pendulum firstSeg;
    private Pendulum secondSeg;

    public void render() {
        this.firstSeg.render();
        this.secondSeg.render();
    }
}
