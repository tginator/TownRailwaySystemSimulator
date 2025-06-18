package edu.curtin.oose2024s2.assignment2.towns;

import edu.curtin.oose2024s2.assignment2.Simulator;
import edu.curtin.oose2024s2.assignment2.patterns.observer.Observer;

import java.util.logging.Logger;

// For our individual town objects

public class Town implements Observer {
    private String name;
    private int population;
    private int stockpile;
    private int singleTracks;
    private int dualTracks;

    private int transported;

    private static final Logger log = Logger.getLogger(Town.class.getName());

    public Town(String name, int population) {
        this.name = name;
        this.population = population;
        this.stockpile = 0;
        this.singleTracks = 0;
        this.dualTracks = 0;
        this.transported = 0;
    }

    @Override
    public void update(String event, String details) {
        switch (event) {
            case "town-population":
                int newPopulation = Integer.parseInt(details);
                if (newPopulation > 0) {
                    this.population = newPopulation;
                    System.out.println("town-population " + name + " " + newPopulation);
                    log.info(()-> name + " updated population to " + newPopulation);
                }
                break;

            case "railway-constructed":
                addSingleTrack();
                log.info(() -> "New single-track railway connected to " + name);
                break;

            case "railway-duplicated":
                addDualTrack();
                log.info(() -> "Railway to " + name + " upgraded to dual-track");
                break;

            default:
                log.info(() -> name + " received unknown event: " + event);
        }
    }

    public void addSingleTrack() {
        singleTracks++;
    }

    public void addDualTrack() {
        dualTracks++;
    }

    public int getStockpile() {
        return this.stockpile;
    }

    public void decreaseStockpile() {
        this.stockpile = this.stockpile - 100;
        this.transported = this.transported + 100;
    }

    public void resetTransported() {
        transported = 0;
    }
    public void setStockpile() {
        this.stockpile += population;
    }

    public void printStatus() {
        System.out.printf(
                "%s | Population: %d | Stockpile: %d | Single-Tracks: %d | Dual-Tracks: %d | Transported: %d \n",
                name, population, stockpile, singleTracks, dualTracks, transported);
    }

    public String getName() {
        return name;
    }

    public void progressDay() {
        setStockpile();
    }
}
