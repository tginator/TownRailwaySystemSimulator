package edu.curtin.oose2024s2.assignment2.factories;


import edu.curtin.oose2024s2.assignment2.patterns.state.ConstructingDual;
import edu.curtin.oose2024s2.assignment2.patterns.state.RailwayState;

// Constructing dual railway state
public class CreateConDual extends RailwayFactory {


    @Override
    public RailwayState createRailway() {
        return new ConstructingDual();
    }
}
