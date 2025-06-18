package edu.curtin.oose2024s2.assignment2.patterns.state;

import edu.curtin.oose2024s2.assignment2.towns.Railway;
import edu.curtin.oose2024s2.assignment2.towns.RailwayUpdater;
import edu.curtin.oose2024s2.assignment2.towns.Town;

public class ConstructingDual implements RailwayState {
    private int daysRemaining;
    private Status status = Status.DUAL_CONSTRUCTION;
    private boolean direction = true;  // Keep alternating directions.

    public ConstructingDual() {
        this.daysRemaining = 5;
    }

    @Override
    public void transportGoods(Town townA, Town townB) {
        if (direction) {

            if (townA.getStockpile() > 99) {
                // Transport from TownA to TownB.
                townA.decreaseStockpile();

            }
        } else {

            if (townB.getStockpile() > 99) {
                // Transport from TownB to TownA.
                townB.decreaseStockpile();

            }
        }
        // Alternate the direction for the next day.
        direction = !direction;
    }


    @Override
    public void progressConstruction(Railway r, RailwayUpdater u) {
        if (daysRemaining > 0) {
            daysRemaining--;
        }

        if (daysRemaining <= 0) {
            u.completeDualTrackConstruction(r);
        }
    }

    @Override
    public Status getType() {
        return status;
    }
}
