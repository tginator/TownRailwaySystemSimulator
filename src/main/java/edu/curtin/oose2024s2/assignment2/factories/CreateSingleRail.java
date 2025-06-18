package edu.curtin.oose2024s2.assignment2.factories;

import edu.curtin.oose2024s2.assignment2.patterns.state.RailwayState;
import edu.curtin.oose2024s2.assignment2.patterns.state.SingleRailway;

public class CreateSingleRail extends RailwayFactory {


    @Override
    public RailwayState createRailway() {
        return new SingleRailway();
    }
}
