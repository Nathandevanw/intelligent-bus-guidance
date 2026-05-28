package com.busguidance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BusIntegrationTest {
    /*
    @Test
    void RetrieveTest() {
        BusRepository busRepository = new BusRepository();
        Bus expectedBus = new Bus("12345678", 70, 1, "Hybrid");

        // Check that random buses not in the database are not retrieved
        assertNull(busRepository.Retrieve("87654320"));

        // Check that the correct Bus is retrieved
        assertEquals(expectedBus.getBusID(), (busRepository.Retrieve("12345678")).getBusID());
    }

    @Test
    void UpdateTest() {
        BusRepository busRepository = new BusRepository();

        // Verify that correct information are updated correctly
        assertEquals(true, busRepository.Update("12345678", 50, 1, "Hybrid"));

        // Verify taht increases in capacity during updates are not updated
        assertEquals(false, busRepository.Update("12345678", 71, 1, "Electricity"));
    }
    */
   
    @Test
    //This is a test!
    void AddTest() {
        Bus newBus = new Bus("87654321", 50, 1, "Electricity");
        Bus matchBus = new Bus("12345678", 70, 0.9, "Electricity");
        Bus wrongBus = new Bus("87654321", 50, 1, "Petrol");
        BusRepository busRepository = new BusRepository();
        
        // Verify valid buses are stored correctly.
        assertEquals(true, busRepository.Add(newBus));

        // Verify invlaid buses are rejected due to matching ID
        assertEquals(false, busRepository.Add(matchBus));

        // Verify invalid buses are rejected due to invalid fuel type
        assertEquals(false, busRepository.Add(wrongBus));
    }
    
}
 