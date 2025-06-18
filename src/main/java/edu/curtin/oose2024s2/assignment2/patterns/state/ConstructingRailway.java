package edu.curtin.oose2024s2.assignment2.patterns.state;

import edu.curtin.oose2024s2.assignment2.towns.Railway;
import edu.curtin.oose2024s2.assignment2.towns.RailwayUpdater;
import edu.curtin.oose2024s2.assignment2.towns.Town;

public class ConstructingRailway implements RailwayState {
    private int daysRemaining;
    private Status status = Status.SINGLE_CONSTRUCTION;

    public ConstructingRailway() {
        this.daysRemaining = 5;
    }

    @Override
    public void transportGoods(Town townA, Town townB) {
        // do nothing
    }

    @Override
    public void progressConstruction(Railway r, RailwayUpdater u) {
        if (daysRemaining > 0) {
            daysRemaining--;
        }

        if (daysRemaining <= 0) {
            u.completeSingleTrackConstruction(r);
        }
    }

    @Override
    public Status getType() {
        return status;
    }
}
