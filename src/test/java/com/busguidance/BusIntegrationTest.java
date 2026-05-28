package com.busguidance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BusIntegrationTest {
    @Test
    //This is a test!
    void AddTest() {
        Bus bus = new Bus("12345678",70, 100, "Electricity");
        BusRepository busRepository = new BusRepository();
        
        assertEquals(busRepository.add(bus), true);

    }

    @Test
    void RetrieveTest() {
        BusRepository busRepository = new BusRepository();
        Bus expectedBus = new Bus("12345678", 70, 100, "Hybrid");

        // Check that random buses not in the database are not retrieved
        assertNull(busRepository.Retrieve("87654321"));

        // Check that the correct Bus is retrieved
        assertEquals(expectedBus.getBusID(), (busRepository.Retrieve("12345678")).getBusID());
    }

    @Test
    void UpdateTest() {
        BusRepository busRepository = new BusRepository();

        // Verify that correct information are updated correctly
        assertEquals(true, busRepository.Update("12345678", 50, 100, "Hybrid"));

        // Verify taht increases in capacity during updates are not updated
        assertEquals(false, busRepository.Update("12345678", 71, 100, "Electricity"));
    }

    
}
 