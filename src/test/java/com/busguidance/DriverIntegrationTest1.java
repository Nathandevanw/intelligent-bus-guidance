package com.busguidance;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class DriverIntegrationTest1 {
    private DriverRepository repository;
    private final String integrationFilePath = "data/test_drivers.txt";

    @BeforeEach
    public void setUp() {
        // Ensure the data directory exists within the project directory structure
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir();
        }
        
        // Point the repository handler directly to the physical test file instance
        repository = new DriverRepository(integrationFilePath);
    }

    @AfterEach
    public void tearDown() {
        // Completely clean out the testing file after each run to preserve a blank slate
        File file = new File(integrationFilePath);
        if (file.exists()) {
            file.delete();
        }
    }

    // ==========================================
    // INTEGRATION TEST 1: VALID STORAGE PERSISTENCE
    // ==========================================
    @Test
    public void testValidDriversAreStoredCorrectly() {
        // Set up the standard valid profile utilizing Nathan Van's baseline details
        Driver driver = new Driver("40362951AB", "Nathan Van", 4, "Heavy", "12|Piers St|Altona|VIC|AUS", "29-02-2000");
        
        // Attempt to write the driver object down into the flat file database
        boolean wasAdded = repository.addDriver(driver);
        assertTrue(wasAdded, "The repository system should allow valid drivers to be written to disk smoothly.");
        
        // Pull data directly back from the file stream to confirm real storage integrity
        Driver retrieved = repository.retrieveDriver("40362951AB");
        assertNotNull(retrieved, "The record should be retrievable from the physical plaintext storage.");
        assertEquals("Nathan Van", retrieved.getName(), "The persisted name string must perfectly match the saved entry data.");
    }

    // ==========================================
    // INTEGRATION TEST 2: INVALID DATA REJECTION
    // ==========================================
    @Test
    public void testInvalidDriversAreRejected() {
        // Instantiate a profile that intentionally breaks the out-of-bounds birth month boundary (14)
        Driver invalidDriver = new Driver("40362951AB", "Nathan Van", 4, "Heavy", "12|Piers St|Altona|VIC|AUS", "29-14-2000");
        
        // Run the insert logic and check that it blocks the data save
        boolean wasAdded = repository.addDriver(invalidDriver);
        assertFalse(wasAdded, "The file stream processing must catch rule breaks and drop write requests immediately.");
        
        // Double check that no rows were appended to the flat file database
        Driver retrieved = repository.retrieveDriver("40362951AB");
        assertNull(retrieved, "No records should be created or pulled out if internal validation drop filters trigger.");
    }

    // ==========================================
    // INTEGRATION TEST 3: UPDATE OPERATIONS PERSISTENCE
    // ==========================================
    @Test
    public void testUpdatesArePersistedCorrectly() {
        // Set up and save the baseline profile to the disk workspace
        Driver driver = new Driver("40362951AB", "Nathan Van", 4, "Heavy", "39|Davies St|Altona|VIC|AUS", "29-02-2000");
        repository.addDriver(driver);

        // Prepare an allowed update payload changing the address string block
        Driver updatedDriver = new Driver("40362951AB", "Nathan Van", 4, "Heavy", "23|Davies St|Deer Park|VIC|AUS", "29-02-2000");
        boolean wasUpdated = repository.updateDrivers(updatedDriver);
        assertTrue(wasUpdated, "The repository system must successfully handle normal attribute edits on matching IDs.");

        // Read the text file directly to check that the modification completely overwrote the old row
        Driver retrieved = repository.retrieveDriver("40362951AB");
        assertNotNull(retrieved);
        assertEquals("23|Davies St|Deer Park|VIC|AUS", retrieved.getAddress(), "The updated address values must overwrite old entries on disk.");
    }

    // ==========================================
    // INTEGRATION TEST 4: DYNAMIC RECORD COUNTS
    // ==========================================
    @Test
    public void testRecordCountsAreUpdatedCorrectly() {
        // The file should start fresh with no tracking data elements
        assertEquals(0, repository.countDrivers(), "The baseline record length for an empty text asset must report as zero.");

        // Append the first valid tracking payload row down to disk
        Driver firstDriver = new Driver("40362951AB", "Nathan Van", 4, "Heavy", "12|Piers St|Altona|VIC|AUS", "29-02-2000");
        repository.addDriver(firstDriver);
        assertEquals(1, repository.countDrivers(), "The dynamic record count should increment immediately upon a successful addition.");

        // Append a second separate valid row into the line track
        Driver secondDriver = new Driver("55!!@@!!CD", "Sarah Smith", 8, "Light", "10|High St|Geelong|VIC|AUS", "15-05-1990");
        repository.addDriver(secondDriver);
        assertEquals(2, repository.countDrivers(), "The repository count metric must adjust properly when stacking file strings.");
    }
}