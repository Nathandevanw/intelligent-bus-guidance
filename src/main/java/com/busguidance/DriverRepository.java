package com.busguidance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;


public class DriverRepository {
// Add (), Update (), Retrieve (), Count () functions
    //The physical location of the database file
    private final String filePath;
    private List<Driver> drivers = new ArrayList<>();

    //constructor initializes the repository and immediately loads existing data.
    //FilePath The path to the text file database (e.g., "data/drivers.txt")
    public DriverRepository(String filePath) {
        this.filePath = filePath;
        loadDriversFromFile();
    }
    //constructor for load and read the data and convert it into Driver objects in the list
    private void loadDriversFromFile() {
        // ALWAYS clear the list first to prevent duplicating data when reloading
        drivers.clear(); 
        // If this is a fresh run and the file doesn't exist yet, just stop and return
        File file = new File(filePath);
        if (!file.exists()) return;
        // Splits the line by commas, and \\s* absorbs any accidental spaces after the comma to ensure clean parsing. Then it checks if there are exactly 6 parts to match the Driver constructor parameters before creating a new Driver object and adding it to the list.
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                    
                String[] parts = line.split(",\\s*");
                if (parts.length == 6) {
                    Driver driver = new Driver(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3], parts[4], parts[5]);
                    drivers.add(driver);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading drivers from file: " + e.getMessage());
        }
    }
    //This method take the current in-memory list of Driver objects and writes them back to the specified file in a comma-separated format. Each Driver's fields are joined into a single line, and the file is overwritten with the updated data.
    private void saveDriversToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Driver driver : drivers) {
                bw.write(String.join(",", driver.getDriverID(), driver.getName(), String.valueOf(driver.getExperienceYears()), driver.getLicenseType(), driver.getAddress(), driver.getBirthdate()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving drivers to file: " + e.getMessage());
        }
    }
    // This method performs the necessary validation checks on the Driver object's fields according to the specified rules. It checks the ID formatting, address structure, and birthdate validity. If any of the checks fail, it returns false; otherwise,
    //  it returns true to indicate that the Driver object is valid for storage or update.
    public boolean validateDriverFields(Driver driver) {
        // D1: ID Validation
        String id = driver.getDriverID();
        if (id == null || id.length() != 10) return false;
        
        // The very first character must be a digit between 2 and 9
        char c1 = id.charAt(0);
        if (c1 < '2' || c1 > '9') return false;
        
        // D2: Address Format Verification
        // Splits the address using the pipe symbol. (The double backslash escapes the pipe in regex).
        String[] addressParts = driver.getAddress().split("\\|");
        // Address must have exactly 5 sections (Number, Street, City, State, Country)
        if (addressParts.length != 5) return false;
        
        for(String part : addressParts) {
            if(part.trim().isEmpty()) return false;
        }

        // D3: Strict Birthdate Parsing
        try {
            //ResolverStyle.STRICT prevents fake dates (like February 29th on a non-leap year)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu").withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse(driver.getBirthdate(), formatter);
        } catch (DateTimeParseException e) {
            // If the date is impossible or formatted wrong, reject it
            return false;
        }
        // If it survives all checks above, the data is perfectly valid
        return true;
    }
    
    //This method attempt to add a new Driver object to the repository. It first validates the Driver's fields using the validateDriverFields method. 
    // If validation fails, it returns false. It also checks for duplicate Driver IDs to prevent overwriting existing records. 
    // If the Driver is valid and has a unique ID, it adds the Driver to the in-memory list and saves the updated list to the file, returning true to indicate success.
    public boolean addDriver(Driver newDriver) {
        // Step 1: Reject the addition if the formatting rules fail
        if (!validateDriverFields(newDriver)) {
            return false;
        }
        // Step 2: Loop through existing drivers to make sure this ID doesn't already exist
        for (Driver existingDriver : drivers) {

            if (existingDriver.getDriverID().equals(newDriver.getDriverID())) {
                return false; 
            }
        }
        // Step 3: Add to memory and save to the physical text file
        drivers.add(newDriver);
        saveDriversToFile();
        return true;
    }
    //This method attempts to update an existing Driver record in the repository. It first validates the updated Driver's fields. If validation fails, it returns false.
    public boolean updateDrivers(Driver updateDriver) {
        // Step 1: Reject if the new data format is invalid
        if (!validateDriverFields(updateDriver)) {
            return false;
        }
        // Step 2: Find the matching driver in the database
        for (int i = 0; i < drivers.size(); i++) {
            Driver existingDriver = drivers.get(i);
            // D5: Immutability Check 
            // The driver's name is locked and cannot be changed     
            if (existingDriver.getDriverID().equals(updateDriver.getDriverID())) {
                if(!existingDriver.getName().equals(updateDriver.getName())){
                    return false;
                }
                
                
            //D4: Experience Lock Rule ---
            // Drivers with > 10 years experience are locked into their current license type
            if (existingDriver.getExperienceYears() > 10 && !existingDriver.getLicenseType().equals(updateDriver.getLicenseType())) {
                    return false;
                } 
            
            // Step 3: Apply the update by replacing the old object with the new one at the exact same index
            drivers.set(i, updateDriver);
            saveDriversToFile();
            return true;
            }
        }
        // Return false if the loop finishes and the driver ID was never found
        return false;

    }
    // this method Searches for a specific driver by their ID to return the Driver if found or null if no matching record exists.
    public Driver retrieveDriver (String driverID) {
        loadDriversFromFile();
        for (Driver driver : drivers) {
            if (driver.getDriverID().equals(driverID)) {
                return driver;
            }
        }
        return null;
    }
    // This method returns the total number of Driver records currently stored in the repository. 
    public int countDrivers() {
        loadDriversFromFile();
        return drivers.size();
    }

}


