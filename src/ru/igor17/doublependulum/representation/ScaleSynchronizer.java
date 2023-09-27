package ru.igor17.doublependulum.representation;

import java.util.ArrayList;

public class ScaleSynchronizer {
    ArrayList<TimeGraph> syncedGraphs;

    ScaleSynchronizer() {
        this.syncedGraphs = new ArrayList<>();
    }

    void addGraph(TimeGraph timeGraph) {
        this.syncedGraphs.add(timeGraph);
    }

    void syncScale() {
        double maxY = 0;
        double minY = Double.MAX_VALUE;

        for (TimeGraph timeGraph : syncedGraphs) {
            double graphMinY = timeGraph.getMinY();
            double graphMaxY = timeGraph.getMaxY();
            if (maxY < graphMaxY) {
                maxY = graphMaxY;
            }
            if (minY > graphMinY) {
                minY = graphMinY;
            }
        }

        for (TimeGraph timeGraph : syncedGraphs) {
            timeGraph.setMaxY(maxY);
            timeGraph.setMinY(minY);
        }
    }
}