package com.busguidance;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;

public class BusRepository {
// Add (), Update (), Retrieve (), Count () functions

    // Constructor for the BusRepsitory
    public BusRepository() {
        
    }

    //The Add method
    public boolean add(Bus bus) {
        
        return true;
    }
    
    //The Update method
    public boolean Update(String busId, int capacity, double fuelLevel, String fuelType) {
        // Keeps track of the elements already in the txt file.
        ArrayList<String> inputStorage = new ArrayList<>();
        short wasIdFound = 0;

         // Opens the file object and checks if the bus that needs to be updated is in the database
        File database = new File("buses.txt");
        try (Scanner reader = new Scanner(database)){
            // Reads all the lines of the database and saves it.
            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] entry = line.split(", ");      // Split the data using','

                // If the busID matches the parameter, it changes the values accordingly
                if (entry[0].equals(busId)) {
                    // Checks that only the capacity is being changed and that it is changed to decrease in value
                    if (Double.parseDouble(entry[2]) == fuelLevel && entry[3].equals(fuelType) && Integer.parseInt(entry[1]) >= capacity) {
                        String newLine = busId + ", " + String.valueOf(capacity) + ", " + String.valueOf(fuelLevel) + ", " + fuelType;
                        inputStorage.add(newLine);
                    }
                    else {
                        System.out.println("Error: Only change capacity to decrease");
                        return false;
                    }
                    wasIdFound += 1;    // Keeps track of the fact that the ID was found the repository and therefore updated.
                }
                // If the busID does not match, it does nto change the values.
                else {
                    inputStorage.add(line);
                }
            }
        }
        catch(FileNotFoundException e) {
            // Returns an error message if the file does not exist in the project.
            System.out.println("File Not found in the project");
            return false;
        }

        // Rewrites the entire file as it was, with only the changes passed through the parameter.
        try (FileWriter writer = new FileWriter("buses.txt")) {
            for (int i = 0; i < inputStorage.size(); i++) {
                writer.write(inputStorage.get(i));
                writer.write("\n");
            }
            if (wasIdFound == 1) {
                System.out.println("The update was successful.");
                System.out.println(inputStorage);
                return true;
            } 
            else {
                System.out.println("Error: The bus ID was not found.");
                return false;
            }
        } 
        catch (IOException e) {
            System.out.println("Error: The File could not be opened");
            return false;
        }
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
