package edu.curtin.oose2024s2.assignment2;

import edu.curtin.oose2024s2.assignment2.patterns.state.Status;
import edu.curtin.oose2024s2.assignment2.towns.RailwayUpdater;
import edu.curtin.oose2024s2.assignment2.towns.TownNetwork;
import edu.curtin.oose2024s2.assignment2.towns.Railway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Simulator {
    private TownNetwork townNetwork;
    private TownsInput input; // Input source for events
    private int day = 0;

    private Build<Railway> railways = new Build<>();

    private static final Logger log = Logger.getLogger(Simulator.class.getName());

    public Simulator() {
        this.townNetwork = new TownNetwork();
        this.input = new TownsInput(); // Initialize the event source
    }

    public void run() {

        // input.setErrorProbability(0.5);
        input.setErrorProbability(0);

        System.out.println("Starting the simulation...");
        log.info("Simulation started.");

        try {
            while (System.in.available() == 0) { // Run until Enter is pressed
                day++;

                printDayHeader();

                townNetwork.resetTransportedGoods();
                townNetwork.progressDay(); // Goods are accumulated at the start of day.
                progressRailways(); // Progress railways then transport goods
                processMessages(); // Process all input messages like population change, new town (goods generated on same day), railway creations
                
                // Ensure at least 2 towns exist for railway construction
                ensureMinimumTowns();
                
                System.out.println(" ");
                townNetwork.printAllTowns(); // Print status of all towns

                log.info(() -> "Day " + day + " completed.");
                FileOutput.writeDotFile("simoutput.dot", townNetwork.getAllTowns(), railways.getList());

                simulateOneDay(); // Simulate one second as one day
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "Error reading input", e);
        }
    }

    private void printDayHeader() {
        System.out.println("\n------------------------");
        System.out.println("Day " + day + ":\n");
    }

    private void progressRailways() {
        for (Railway r : railways.getList()) {
            r.progressDay();
        }
    }

    private void processMessages() {
        // method to process incoming message from input which then calls event specific method.
        String message;
        while ((message = input.nextMessage()) != null) {
            String[] parts = message.split(" ");
            try {
                switch (parts[0]) {
                    case "town-founding":
                        handleTownFounding(parts);
                        break;
                    case "town-population":
                        handleTownPopulation(parts);
                        break;
                    case "railway-construction":
                        handleRailwayConstruction(parts);
                        break;
                    case "railway-duplication":
                        handleRailwayDuplication(parts);
                        break;
                    default:
                        String errMessage = message;
                        log.log(Level.WARNING, () -> "Unknown message: " + errMessage);
                        break;
                }
            } catch (InputMismatchException | IllegalArgumentException e) {
                log.log(Level.SEVERE, () -> "Invalid input: " + e);
            } catch (NoSuchElementException e) {
                log.log(Level.WARNING, () -> "Missing element: " + e);
            }
        }
    }

    // Method for creating a new town
    private void handleTownFounding(String[] parts) {
        try {
                if (townNetwork.townExists(parts[1])) {
                    throw new IllegalArgumentException("Town already exists: " + parts[1]);
                }

                int population = Integer.parseInt(parts[2]);
                townNetwork.createTown(parts[1], population);
                log.info(() -> "Town founded: " + parts[1]);

        } catch (NumberFormatException e) {
            log.log(Level.WARNING, () ->"Invalid population format: " + parts[2]);
        } catch (ArrayIndexOutOfBoundsException e) {
            log.log(Level.SEVERE, () ->"Invalid input format for town-founding: " + String.join(" ", parts));
        }
    }

    // Method for updating population
    private void handleTownPopulation(String[] parts) {
        try {
            if (!townNetwork.townExists(parts[1])) {
                throw new NoSuchElementException("Town not found: " + parts[1]);
            }

            int population = Integer.parseInt(parts[2]);

            if (population < 0) {
                throw new IllegalArgumentException("Population cannot be negative: " + population);
            }

            townNetwork.notifyObservers("town-population", parts[1] + " " + parts[2]);
            log.finer(() -> "Population updated for " + parts[1] + " to " + parts[2]);

        } catch (NumberFormatException e) {
            log.severe(() -> "Invalid population format: " + parts[2]);
        } catch (NoSuchElementException e) {
            log.warning(() -> e.getMessage());
        } catch (IllegalArgumentException e) {
            log.severe(() -> e.getMessage());
        }
    }


    private void handleRailwayConstruction(String[] parts) {
        try {
            // Validate that both towns exist
            if (!townNetwork.townExists(parts[1]) || !townNetwork.townExists(parts[2])) {
                throw new NoSuchElementException("One or both towns not found: " + parts[1] + ", " + parts[2]);
            }

            // Check if a railway between the towns already exists
            if (railwayExists(parts[1], parts[2])) {
                log.warning(() -> "Railway already exists between " + parts[1] + " and " + parts[2]);
                return; // Do not add a duplicate railway
            }

            // Create the new railway
            Railway r = new Railway(townNetwork.getTown(parts[1]), townNetwork.getTown(parts[2]));
            r.registerObserver(townNetwork.getTown(parts[1]));
            r.registerObserver(townNetwork.getTown(parts[2]));
            railways.add(r);
            System.out.println("railway-construction" + " " + parts[1] + " " + parts[2]);

            log.info(() -> "Railway constructed between " + parts[1] + " and " + parts[2]);
        } catch (ArrayIndexOutOfBoundsException e) {
            log.severe(() ->"Invalid input format for railway-construction");
        }
    }


    private void handleRailwayDuplication(String[] parts) {
        try {
            for (Railway r : railways.getList()) {
                if (r.getTownA().equals(parts[1]) && r.getTownB().equals(parts[2])) {
                    if (r.getType().equals(Status.SINGLE)) {
                        new RailwayUpdater().upgradeToDualTrack(r);
                        log.info(() -> "Railway duplicated between " + parts[1] + " and " + parts[2]);
                        return;
                    } else {
                        throw new UnsupportedOperationException("Railway not in state to duplicate: " + parts[1] + " " + parts[2]);
                    }
                }
            }
        } catch (UnsupportedOperationException e) {
            log.log(Level.WARNING, "Attempted to duplicate a dual-track railway", e);
        } catch (ArrayIndexOutOfBoundsException e) {
            log.log(Level.SEVERE, "Invalid input format for railway-duplication", e);
        }
    }

    private boolean railwayExists(String townA, String townB) {
        for (Railway r : railways.getList()) {
            // Check if the railway exists in either direction (A <-> B)
            if ((r.getTownA().equals(townA) && r.getTownB().equals(townB)) ||
                    (r.getTownA().equals(townB) && r.getTownB().equals(townA))) {
                return true; // Railway already exists
            }
        }
        return false;
    }



    private void ensureMinimumTowns() {
        // Ensure at least 2 towns exist for railway construction
        if (townNetwork.getAllTowns().size() < 2) {
            // Force generation of towns by calling nextMessage until we have at least 2
            while (townNetwork.getAllTowns().size() < 2) {
                String message = input.nextMessage();
                if (message != null) {
                    String[] parts = message.split(" ");
                    try {
                        if ("town-founding".equals(parts[0])) {
                            handleTownFounding(parts);
                        }
                    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                        // Ignore invalid messages, just continue trying
                        log.fine(() -> "Ignoring invalid message during town generation: " + e.getMessage());
                    }
                } else {
                    // If no message available, create a default town
                    if (townNetwork.getAllTowns().size() == 0) {
                        townNetwork.createTown("DefaultTown1", 500);
                    } else if (townNetwork.getAllTowns().size() == 1) {
                        townNetwork.createTown("DefaultTown2", 500);
                    }
                    break;
                }
            }
        }
    }

    private void simulateOneDay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.log(Level.WARNING, "Simulation interrupted", e);
        }
    }
}
