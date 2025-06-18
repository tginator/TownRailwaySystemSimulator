package edu.curtin.oose2024s2.assignment2.towns;

import edu.curtin.oose2024s2.assignment2.Build;
import edu.curtin.oose2024s2.assignment2.patterns.observer.Observable;
import edu.curtin.oose2024s2.assignment2.patterns.observer.Observer;
import edu.curtin.oose2024s2.assignment2.patterns.state.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


// Railway observes any changes and therefore notifies towns of creation of tracks (The state context)
public class Railway implements Observable {
    private Town townA;
    private Town townB;

    private RailwayState state;
    private Build<Observer> observers  = new Build<>();;
    private RailwayUpdater u = new RailwayUpdater();

    public Railway(Town townA, Town townB) {
        this.townA = townA;
        this.townB = townB;
        this.state = new ConstructingRailway(); // Initial state: under construction.
    }

    public void progressDay() {

        state.progressConstruction(this, u);

        state.transportGoods(townA, townB); // Progress railways then transport goods


    }

    public String getTownA() {
        return townA.getName();
    }

    public String getTownB() {
        return townB.getName();
    }


   public void updateState(RailwayState newState) {
        this.state = newState;
   }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers(String event, String details) {

        for (Observer o : observers.getList()) {
            o.update(event, details);
        }
    }

    // Handled within the state pattern classes
    public Status getType() {
        return state.getType();
    }
}
