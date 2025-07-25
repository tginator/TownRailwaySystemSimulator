package edu.curtin.oose2024s2.assignment2.factories;

import edu.curtin.oose2024s2.assignment2.patterns.state.ConstructingRailway;
import edu.curtin.oose2024s2.assignment2.patterns.state.RailwayState;

public class CreateConSingle extends RailwayFactory {

    @Override
    public RailwayState createRailway() {
        return new ConstructingRailway();
    }

}
