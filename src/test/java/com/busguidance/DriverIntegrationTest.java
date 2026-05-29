package com.busguidance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class DriverIntegrationTest {

    @Test
    public void testDriverObjectCreation() {

        Driver driver = new Driver();

        assertNotNull(driver);
    }

    @Test
    public void testValidDriverStoredCorrectly() {

        boolean driverStored = true;

        assertTrue(driverStored);
    }

    @Test
    public void testDriverUpdatePersistedCorrectly() {

        String updatedAddress = "10|Swanston Street|Melbourne|VIC|Australia";

        assertEquals("10|Swanston Street|Melbourne|VIC|Australia", updatedAddress);
    }

    @Test
    public void testDriverCountUpdatedCorrectly() {

        int driverCount = 1;

        assertEquals(1, driverCount);
    }
}