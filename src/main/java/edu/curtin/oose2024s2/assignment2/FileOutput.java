package edu.curtin.oose2024s2.assignment2;

import edu.curtin.oose2024s2.assignment2.towns.Railway;
import edu.curtin.oose2024s2.assignment2.towns.Town;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FileOutput {

    // Method to generate the DOT file based on the current town and railway data
    public static void writeDotFile(String filename, List<Town> towns, List<Railway> railways) {
        try (FileWriter writer = new FileWriter(filename)) {
            // Start of the DOT file
            writer.write("graph Towns {\n");

            // Write town names
            for (Town town : towns) {
                writer.write("    " + town.getName() + "\n");
            }
            writer.write("\n");

            // Write railway connections intot the file
            for (Railway railway : railways) {
                String connection = generateRailwayConnection(railway);
                writer.write("    " + connection + "\n");
            }

            // End of DOT file
            writer.write("}\n");
        } catch (IOException e) {
            System.err.println("Error writing DOT file: " + e.getMessage());
        }
    }

    // Helper method to generate the correct syntax for each railway connection in oursystem
    private static String generateRailwayConnection(Railway railway) {
        String townA = railway.getTownA();
        String townB = railway.getTownB();
        StringBuilder connection = new StringBuilder(townA + " -- " + townB);

        switch (railway.getType()) {
            case SINGLE_CONSTRUCTION:
                connection.append(" [style=\"dashed\"]");
                break;
            case SINGLE:
                break;
            case DUAL_CONSTRUCTION:
                connection.append(" [style=\"dashed\",color=\"black:black\"]");
                break;
            case DUAL:
                connection.append(" [color=\"black:black\"]");
                break;
        }

        return connection.toString();
    }
}
