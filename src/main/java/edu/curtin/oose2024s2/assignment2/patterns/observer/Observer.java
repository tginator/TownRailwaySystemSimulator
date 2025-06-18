package edu.curtin.oose2024s2.assignment2.patterns.observer;

public interface Observer {
    // our town class will implement this to update whenever notified by observable town network
    void update(String event, String details);
}
