package edu.curtin.oose2024s2.assignment2.patterns.state;

import edu.curtin.oose2024s2.assignment2.towns.Railway;
import edu.curtin.oose2024s2.assignment2.towns.RailwayUpdater;
import edu.curtin.oose2024s2.assignment2.towns.Town;

public interface RailwayState {
    void transportGoods(Town a, Town b);

    void progressConstruction(Railway r, RailwayUpdater u);

    Status getType();

    }
