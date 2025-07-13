package edu.curtin.oose2024s2.assignment2.patterns.state;

import edu.curtin.oose2024s2.assignment2.towns.Railway;
import edu.curtin.oose2024s2.assignment2.towns.RailwayUpdater;
import edu.curtin.oose2024s2.assignment2.towns.Town;

public class DualRailway implements RailwayState {

    private Status status = Status.DUAL;
    @Override
    public void transportGoods(Town townA, Town townB) {
        if (townA.getStockpile() > 99) {
            townA.decreaseStockpile();
            townB.increaseStockpile(100);
        }
        if (townB.getStockpile() > 99) {
            townB.decreaseStockpile();
            townA.increaseStockpile(100);
        }
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
