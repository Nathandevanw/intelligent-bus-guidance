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
    public boolean Add(Bus bus) {
        // Check if the busID is 8 characters long
        if (bus.getBusID().length() != 8) {
            System.out.println("Error: busID not 8 characters.");
            return false;
        }
        
        // Check if the busID is made up of digits.
        try {
            Integer.parseInt(bus.getBusID());
        }
        catch (NumberFormatException e) {
            System.out.println("Error: ID characters must be digits.");
            return false;
        }

        
        // Check if the busID already exists.
        File database = new File("buses.txt");
        try (Scanner reader = new Scanner(database)){
            // Reads all the lines of the database.
            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] entry = line.split(", ");      // Split the data using','

                // If the busID matches the parameter, it's not added.
                if (entry[0].equals(bus.getBusID())) {
                    System.out.println("Error: Matching busID.");
                    return false;
                }
            }
        }
        catch(FileNotFoundException e) {
            // Returns an error message if the file does not exist in the project.
            System.out.println("Error: File Not found in the project");
            return false;
        }

        // Check bus capacity restriction
        if (bus.getAge() > 50 && bus.getCapacity() >= 50) {
            System.out.println("Error: Bus drivers older than 50 cannot have capacity more than 50.");
            return false;
        }
        
        // Check the fuel level is valid
        if (bus.getFuelLevel() < 0 || bus.getFuelLevel() > 1) {
            System.out.println("Error: Fuel level should be between 0 to 1.");
            return false;
        }

        // Check the fuel type is valid
        if (!bus.getFuelType().equals("Diesel") && !bus.getFuelType().equals("Hybrid") && !bus.getFuelType().equals("Electricity")) {
            System.out.println("Error: Fuel type should be Diesel, Hybrid or Electricity");
            return false;
        }
        
        // Check Electric bus restriction
        if (bus.getExpYears() < 5 && bus.getFuelType().equals("Electricity")) {
            System.out.println("Error: Bus drivers with less than 5 years experience cannot drive electric buses");
            return false;
        }

        // Check driver license restriction for fuel type 
        if ((bus.getLicense().equals("Light") || bus.getLicense().equals("Medium")) && (bus.getFuelType().equals("Electricity") || bus.getFuelType().equals("Hybrid"))) {
            System.out.println("Error: Only bus drivers with a heavy or public transport license can operate electric or hybrid buses.");
            return false;
        }

        // Append the valid Bus details to the database
        try (FileWriter writer = new FileWriter("buses.txt", true)) {
            String newLine = bus.getBusID() + ", " + String.valueOf(bus.getCapacity()) + ", " + String.valueOf(bus.getFuelLevel()) + ", " + String.valueOf(bus.getFuelType());
            writer.write(newLine + "\n");
            System.out.println("Bus " + bus.getBusID() + " is added.");
            return true;
        } 
        catch (IOException e) {
            System.out.println("Error: File not found.");
            return false;
        }

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
                String[] entry = line.split(", ");      // Split the data using', '

                // If the busID matches the parameter, it changes the values accordingly
                if (entry[0].equals(busId)) {
                    // Checks that only the capacity is being changed and that it is changed to decrease in value
                    if (Double.parseDouble(entry[2]) == fuelLevel && entry[3].equals(fuelType) && Integer.parseInt(entry[1]) >= capacity) {
                        String newLine = busId + ", " + String.valueOf(capacity) + ", " + String.valueOf(fuelLevel) + ", " + fuelType;
                        inputStorage.add(newLine);
                    }
                    else {
                        System.out.println("Error: Only change capacity to decrease.");
                        return false;
                    }
                    wasIdFound += 1;    // Keeps track of the fact that the ID was found the database and therefore updated.
                }
                // If the busID does not match, it does nto change the values.
                else {
                    inputStorage.add(line);
                }
            }
        }
        catch(FileNotFoundException e) {
            // Returns an error message if the file does not exist in the project.
            System.out.println("Error: File Not found in the project");
            return false;
        }

        // Rewrites the entire file as it was, with only the changes passed through the parameter.
        try (FileWriter writer = new FileWriter("buses.txt")) {
            for (int i = 0; i < inputStorage.size(); i++) {
                writer.write(inputStorage.get(i));
                writer.write("\n");
            }
            // Only toggles the update as successful if the ID was found the changed in the database.
            if (wasIdFound == 1) {
                System.out.println("The update was successful.");
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
                    System.out.println("Record " + busId + " is found.");
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
        
        System.out.println("Error: Record " + busId + " is not found.");
        return null;
    }

    // Only used for the unit testing, returns a databse with only the first entry.
    public void Reset() {
        // Opens the database and deletes everything before rewriting the first entry
        try (FileWriter writer = new FileWriter("buses.txt")) {
            writer.write("12345678, 70, 1.0, Hybrid\n");
        }
        catch (IOException e) {
            System.out.println("Non-accounted error.");
        }
    }
}
