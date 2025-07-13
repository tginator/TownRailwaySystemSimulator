package edu.curtin.oose2024s2.assignment2.patterns.state;

import edu.curtin.oose2024s2.assignment2.towns.Railway;
import edu.curtin.oose2024s2.assignment2.towns.RailwayUpdater;
import edu.curtin.oose2024s2.assignment2.towns.Town;

public class SingleRailway implements RailwayState {

    private boolean direction = true;  // true: TownA -> TownB, false: TownB -> TownA

    private Status status = Status.SINGLE;

    @Override
    public void transportGoods(Town townA, Town townB) {

        if (direction) {
            if (townA.getStockpile() > 99) {
                // Transport from TownA to TownB.
                townA.decreaseStockpile();
                townB.increaseStockpile(100);
            }
        } else {
            if (townB.getStockpile() > 99) {
                // Transport from TownB to TownA.
                townB.decreaseStockpile();
                townA.increaseStockpile(100);
            }
        }
        // Alternate the direction for the next day.
        direction = !direction;
    }

    @Override
    public void progressConstruction(Railway r, RailwayUpdater u) {
        // do nothing
    }

    @Override
    public Status getType() {
        return status;
    }


}
