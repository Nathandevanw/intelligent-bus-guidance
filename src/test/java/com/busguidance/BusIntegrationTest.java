package com.busguidance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BusIntegrationTest {

    // This tests the Retrieve method
    @Test
    void RetrieveTest() {
        BusRepository busRepository = new BusRepository();
        Bus expectedBus = new Bus("12345678", 70, 1, "Hybrid");

        // Check that random buses not in the database are not retrieved
        assertNull(busRepository.Retrieve("87654320"));

        // Check that the correct Bus is retrieved
        assertEquals(expectedBus.getBusID(), (busRepository.Retrieve("12345678")).getBusID());
    }

    // This tests the Update method
    @Test
    void UpdateTest() {
        BusRepository busRepository = new BusRepository();

        // Verify that correct information are updated correctly
        assertEquals(true, busRepository.Update("12345678", 50, 1, "Hybrid"));

        // Verify taht increases in capacity during updates are not updated
        assertEquals(false, busRepository.Update("12345678", 71, 1, "Electricity"));
    }
   
    // This tests the Add method
    @Test
    void AddTest() {
        Bus newBus = new Bus("87654321", 50, 1, "Electricity");
        Bus matchBus = new Bus("12345678", 70, 0.9, "Electricity");
        Bus wrongBus = new Bus("87654320", 50, 1, "Petrol");
        BusRepository busRepository = new BusRepository();
        
        // Verify valid buses are stored correctly.
        assertEquals(true, busRepository.Add(newBus));

        // Verify invlaid buses are rejected due to matching ID
        assertEquals(false, busRepository.Add(matchBus));

        // Verify invalid buses are rejected due to invalid fuel type
        assertEquals(false, busRepository.Add(wrongBus));

        // Returns the database to the way it was after finishing the Add tests
        busRepository.Reset();
    }

    // This tests the Count method
    @Test
    void CountTest() {
        Bus newBus = new Bus("87654321", 50, 1, "Electricity");
        BusRepository busRepository = new BusRepository();
        
        // Verify record coutns are updated correctly for update function
        busRepository.Update("12345678", 50, 1, "Hybrid");
        assertEquals(1, busRepository.Count());

        // Verify record counts are updated correctly for add function
        busRepository.Add(newBus);
        assertEquals(2, busRepository.Count());

        // Returns the database to the way it was after finishing the Add tests
        busRepository.Reset();
    }
}   
 