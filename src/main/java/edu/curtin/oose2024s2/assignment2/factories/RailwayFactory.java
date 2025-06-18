package edu.curtin.oose2024s2.assignment2.factories;

import edu.curtin.oose2024s2.assignment2.patterns.state.RailwayState;

// Abstract class for creating railway states
public abstract class RailwayFactory {

    public abstract RailwayState createRailway();

}
