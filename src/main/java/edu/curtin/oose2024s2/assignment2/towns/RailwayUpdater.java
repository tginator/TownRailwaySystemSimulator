package edu.curtin.oose2024s2.assignment2.towns;

import edu.curtin.oose2024s2.assignment2.factories.*;
import edu.curtin.oose2024s2.assignment2.patterns.state.RailwayState;
import edu.curtin.oose2024s2.assignment2.patterns.state.SingleRailway;
import edu.curtin.oose2024s2.assignment2.patterns.state.Status;

import java.util.logging.Level;
import java.util.logging.Logger;

// Updates railway using factory and injecting it into our railway objects. (Manages state context)
public class RailwayUpdater {

    protected RailwayFactory factory;

    private static final Logger log = Logger.getLogger(RailwayUpdater.class.getName());


    // injects railway object with new state
    public void completeSingleTrackConstruction(Railway r) {

        factory = new CreateSingleRail();

        r.updateState(factory.createRailway());

        r.notifyObservers("railway-constructed", " ");
        log.log(Level.INFO, "Railway construction complete. Now operational as a single-track railway.");
    }

    public void upgradeToDualTrack(Railway r) {

        factory = new CreateConDual();

        r.updateState(factory.createRailway());

            System.out.println("railway-duplication " + r.getTownA() + " " + r.getTownB());
            log.log(Level.INFO, "Second railway has begun construction");
        }

    public void completeDualTrackConstruction(Railway r) {

        factory = new CreateDualRail();

        r.updateState(factory.createRailway());

        r.notifyObservers("railway-duplicated"," ");
        log.log(Level.INFO, "Dual railway construction completed.");
    }

    }
