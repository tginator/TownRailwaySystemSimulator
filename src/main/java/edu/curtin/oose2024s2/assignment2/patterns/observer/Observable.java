package edu.curtin.oose2024s2.assignment2.patterns.observer;

public interface Observable {
    // used for our town network and railway which notify the town of any changes.

    void registerObserver(Observer observer);

    void notifyObservers(String event, String details);


}
