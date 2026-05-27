package com.busguidance;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BusRepository {
// Add (), Update (), Retrieve (), Count () functions

    // Constructor for the BusRepsitory
    public BusRepository() {
        
    }

    //The Add method
    public boolean add(Bus bus) {
        
        return true;
    }

    //The Retrieve method
    public Bus Retrieve(String busId) {
        // Defines the file object
        File database = new File("buses.txt");

        // Opens the file object and checks if the busId is in the database
        try (Scanner reader = new Scanner(database)){
            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] entry = line.split(", ");      // Split the data using','

                // Checks if the first argument is the busID required.
                if (entry[0].equals(busId)) {
                    System.out.println("Record " + busId + " is found!");
                    Bus bus = new Bus(entry[0], Integer.parseInt(entry[1]), Double.parseDouble(entry[2]), entry[3]);
                    return bus;
                }
            }
        }
        catch(FileNotFoundException e) {
            // Returns an error message if the file does not exist in the project.
            System.out.println("File Not found in the project");
            return null;
        }
        
        return null;
    }
}
