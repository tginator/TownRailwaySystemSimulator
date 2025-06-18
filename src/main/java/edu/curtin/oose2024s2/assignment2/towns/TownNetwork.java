package edu.curtin.oose2024s2.assignment2.towns;

import edu.curtin.oose2024s2.assignment2.Build;
import edu.curtin.oose2024s2.assignment2.patterns.observer.Observer;
import edu.curtin.oose2024s2.assignment2.patterns.observer.Observable;
import edu.curtin.oose2024s2.assignment2.patterns.state.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// All the towns within the system can be controlled from here
public class TownNetwork implements Observable {
    private Build<Town> towns = new Build<>();
    private Build<Town> observers = new Build<>();

    // Stockpile progressor
    public void progressDay() {

        for (Town t : towns.getList()) {
            t.progressDay();
        }

    }

    public void createTown(String name, int population) {

        for (Town t : towns.getList()) {
            if (t.getName().equals(name)) {
                return;
            }
        }

            Town newTown = new Town(name, population);
            towns.add(newTown);
            newTown.getStockpile();
            registerObserver(newTown);  // Register town as an observer.
            System.out.println("town-founded " + name);

        }

        public List<Town> getAllTowns() {
        return towns.getList();
        }


    public Town getTown(String name) {

        for (Town t : towns.getList()) {
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    public boolean townExists(String name) {
        for (Town t : towns.getList()) {
            if (t.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void printAllTowns() {
        for (Town town : towns.getList()) {
            town.printStatus();
        }
    }

    public void resetTransportedGoods() {
        for (Town t : towns.getList()) {
            t.resetTransported();
        }
    }

    @Override
    public void registerObserver(Observer observer) {
        if (observer instanceof Town) {
            Town town = (Town) observer;
            observers.add(town);
        }
    }

    // Used for updating population of a town
    @Override
    public void notifyObservers(String event, String details) {
        String[] parts = details.split(" ");
        String townName = parts[0];
        Observer observer = null;

        for (Town t : towns.getList()) {
            if (t.getName().equals(townName)) {
                observer = t;
            }
        }

        if (observer != null) {
            observer.update(event, parts[1]);
        }
    }
}
