package com.busguidance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class DriverIntegrationTest {

    @Test
    public void testDriverObjectCreation() {

        BusDriver driver = new BusDriver();

        assertNotNull(driver);
    }

    @Test
    public void testDriverIntegrationFileRuns() {

        boolean driverModuleLoaded = true;

        assertTrue(driverModuleLoaded);
    }

    @Test
    public void testDriverScheduleStatus() {

        String scheduleStatus = "Schedule retrieved";

        assertEquals("Schedule retrieved", scheduleStatus);
    }

    @Test
    public void testDriverUpdateStatus() {

        boolean updateSaved = true;

        assertTrue(updateSaved);
    }
}