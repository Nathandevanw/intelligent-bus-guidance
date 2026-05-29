package com.busguidance;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BusUnitTest {

    // ==========================================
    // RULE 1: BUS ID FORMATTING
    // ==========================================

    @Test
    public void testInvalidBusIDLength() {
        BusRepository repo = new BusRepository();
        // ID is only 7 characters instead of 8
        Bus bus = new Bus("1234567", 40, 0.5, "Diesel");
        
        assertFalse(repo.Add(bus), "System should reject a Bus ID that is not exactly 8 characters long.");
    }

    @Test
    public void testInvalidBusIDCharacters() {
        BusRepository repo = new BusRepository();
        // ID contains letters instead of only digits
        Bus bus = new Bus("1234ABCD", 40, 0.5, "Diesel");
        
        assertFalse(repo.Add(bus), "System should reject a Bus ID that contains non-numeric characters.");
    }

    // ==========================================
    // RULE 2: AGE & CAPACITY RESTRICTIONS
    // ==========================================

    @Test
    public void testOlderDriverCapacityRestriction() {
        BusRepository repo = new BusRepository();
        Bus bus = new Bus("22223333", 55, 0.5, "Diesel");
        bus.setAge(55); // Driver is older than 50
        
        assertFalse(repo.Add(bus), "System should reject drivers over 50 operating buses with a capacity of 50 or more.");
    }

    // ==========================================
    // RULE 3: FUEL LEVEL & TYPE
    // ==========================================

    @Test
    public void testInvalidFuelLevel() {
        BusRepository repo = new BusRepository();
        // Fuel level is 1.5 (must be between 0.0 and 1.0)
        Bus bus = new Bus("33334444", 40, 1.5, "Diesel"); 
        
        assertFalse(repo.Add(bus), "System should reject fuel levels greater than 1.0.");
    }

    @Test
    public void testInvalidFuelType() {
        BusRepository repo = new BusRepository();
        // Fuel type is Petrol (must be Diesel, Hybrid, or Electricity)
        Bus bus = new Bus("44445555", 40, 0.5, "Petrol"); 
        
        assertFalse(repo.Add(bus), "System should reject invalid fuel types like Petrol.");
    }

    // ==========================================
    // RULE 4: LICENSE & EXPERIENCE LOCKS
    // ==========================================

    @Test
    public void testElectricBusExperienceRestriction() {
        BusRepository repo = new BusRepository();
        Bus bus = new Bus("55556666", 40, 0.8, "Electricity");
        bus.setExpYears(3); // Less than the required 5 years
        bus.setLicense("Heavy");
        
        assertFalse(repo.Add(bus), "System should reject drivers with less than 5 years experience from driving Electric buses.");
    }

    @Test
    public void testInvalidLicenseForHybrid() {
        BusRepository repo = new BusRepository();
        Bus bus = new Bus("66667777", 40, 0.8, "Hybrid");
        bus.setLicense("Light"); // Light license cannot operate Hybrid
        
        assertFalse(repo.Add(bus), "System should reject Hybrid bus assignments for drivers with only a Light license.");
    }

    // ==========================================
    // RULE 5: UPDATE RESTRICTIONS
    // ==========================================

    @Test
    public void testUpdateCapacityIncreaseRejected() {
        BusRepository repo = new BusRepository();
        
        // Attempting to increase capacity (which violates the decrease-only rule)
        // Note: Relies on "12345678" existing from the teammate's Reset() method
        assertFalse(repo.Update("12345678", 99, 1.0, "Hybrid"), "System should reject attempts to increase bus capacity during an update.");
    }
}