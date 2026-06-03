package com.busguidance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DriverUnitTest {
    private DriverRepository driverRepository;
    private final String testPath = "data/test_drivers.txt";

    @BeforeEach
    public void setUp() {
        // 1. Check if the data folder exists, if not create it
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir();
        }

        // 2. DELETE the old test file FIRST to guarantee a clean slate
        File file = new File(testPath);
        if(file.exists()) {
            file.delete();
        }

        // 3. THEN initialize the repository so it starts completely empty
        driverRepository = new DriverRepository(testPath);
    }

    // ==========================================
    // CONDITION D1: ID FORMATTING TESTS
    // ==========================================
    
    // Test 1: ID Formatting Test (normal case)
    @Test
    public void testValidDriverID() {
        Driver driver = new Driver("40362951AB", "Nathan Van", 4, "Heavy", "12|Pier St|Altona|VIC|AUS", "29-02-2000");
        assertTrue(driverRepository.validateDriverFields(driver), "System should return the success message to confirm driver with valid ID format");
    }

    // Test 2: ID Formatting Test (invalid case - leading digit)
    @Test
    public void testInvalidDriverIDLeadingDigit(){
        Driver driver = new Driver("10362951AB", "Nathan Van", 4, "Heavy", "12|Pier St|Altona|VIC|AUS", "29-02-2000");
        assertFalse(driverRepository.validateDriverFields(driver), "System should return the error message to confirm driver with invalid leading ID digit format");
    }

    // Test 3: ID Formatting Test (edge case - length)
    @Test
    public void testInvalidDriverIDLength(){
        // FIXED: Changed ID to an 8-character string so it actually violates the 10-character minimum threshold rule
        Driver driver = new Driver("40362951", "Nathan Van", 4, "Heavy", "12|Pier St|Altona|VIC|AUS", "29-02-2000");
        assertFalse(driverRepository.validateDriverFields(driver), "System should return the error message to confirm driver with invalid ID length < 10 characters");
    }

    // ==========================================
    // CONDITION D2: ADDRESS FORMATTING TESTS
    // ==========================================

    // Test 1: Address Formatting Test (normal case)
    @Test
    public void testValidAddressFormat() {
        Driver driver = new Driver("40362951AB", "Nathan Van", 4, "Heavy", "12|Pier St|Altona|VIC|AUS", "29-02-2000");
        assertTrue(driverRepository.validateDriverFields(driver), "System should return the success message to confirm driver with valid address format using '|' as separator");
    }

    // Test 2: Address Formatting Test (invalid case - missing '|' separators)
    @Test
    public void testInvalidAddressFormat_missingPipes() {
        Driver driver = new Driver("40362951AB", "Nathan Van", 4, "Heavy", "23, Hemsley Drive, Deer Park, VIC, AUS", "29-02-2000");
        assertFalse(driverRepository.validateDriverFields(driver), "System should return the error message to confirm driver with invalid address format missing '|' separators");
    }

    // Test 3: Address Formatting Test (edge case - missing fields)
    @Test
    public void testInvalidAddressFormat_missingFields() {
        Driver driver = new Driver("40362951AB", "Nathan Van", 4, "Heavy", "12|Pier St|Altona|VIC|", "29-02-2000");
        assertFalse(driverRepository.validateDriverFields(driver), "System should return the error message to confirm driver with invalid address format missing fields");
    }

    // ==========================================
    // CONDITION D3: BIRTHDATE FORMATTING TESTS
    // ==========================================

    // Test 1: Birthdate Formatting Test (normal case)
    @Test
    public void testValidBirthdateCalendar() {
        Driver driver = new Driver("40362951AB", "Nathan Van", 4, "Heavy", "12|Pier St|Altona|VIC|AUS", "29-02-2000");
        assertTrue(driverRepository.validateDriverFields(driver), "System should return the success message to confirm driver with valid birthdate format and calendar date");
    }

    // Test 2: Birthdate Formatting Test (invalid case - month out of range)
    @Test
    public void testInvalidBirthdateMonthOutOfRange() {
        Driver driver = new Driver("40362951AB", "Nathan Van", 4, "Heavy", "12|Pier St|Altona|VIC|AUS", "29-14-2000");
        assertFalse(driverRepository.validateDriverFields(driver), "System should return the error message to confirm driver with invalid birthdate format with month out of range");
    }

    // Test 3: Birthdate Formatting Test (edge case - non leap year February 29)
    @Test
    public void testInvalidBirthdateNonLeapYearFebruary29() {
        Driver driver = new Driver("40362951AB", "Nathan Van", 4, "Heavy", "12|Pier St|Altona|VIC|AUS", "29-02-2001");
        assertFalse(driverRepository.validateDriverFields(driver), "System should return the error message to confirm driver with invalid birthdate format with non-leap year February 29");
    }

    // ==========================================
    // CONDITION D4: LICENSE EXPIRY / UPDATE TESTS
    // ==========================================

    // Test 1: License Prohibition Test (invalid case - license shift over 10 years of experience)
    @Test
    public void testProhibitLicenseShiftOverTenYears() {
        Driver driver = new Driver("40362951AB", "Nathan Van", 12, "Heavy", "12|Pier St|Altona|VIC|AUS", "29-02-2000");
        driverRepository.addDriver(driver);
        Driver updatedDriver = new Driver("40362951AB", "Nathan Van", 12, "Public Transport", "12|Pier St|Altona|VIC|AUS", "29-02-2000");
        // FIXED: Changed to call updateDriver to properly target updating logic rules
        assertFalse(driverRepository.updateDrivers(updatedDriver), "System should return the error message to confirm driver with valid license shift over 10 years of experience is prohibited");
    }

    // Test 2: License Prohibition Test (normal case - license shift under 10 years of experience)
    @Test
    public void testAllowLicenseShiftUnderTenYears() {
        Driver driver = new Driver("40362951AB", "Nathan Van", 9, "Medium", "12|Pier St|Altona|VIC|AUS", "29-02-2000");
        driverRepository.addDriver(driver);
        Driver updatedDriver = new Driver("40362951AB", "Nathan Van", 9, "Heavy", "12|Pier St|Altona|VIC|AUS", "29-02-2000");
        // FIXED: Changed to call updateDriver to properly target updating logic rules
        assertTrue(driverRepository.updateDrivers(updatedDriver), "System should return the success message to confirm driver with valid license shift under 10 years of experience is allowed");
    }

    // Test 3: License Prohibition Test (edge case - license shift at exactly 10 years of experience)
    @Test
    public void testExactTenYearExperienceLicenseUpdate() {
        Driver driver = new Driver("40362951AB", "Nathan Van", 10, "Medium", "12|Pier St|Altona|VIC|AUS", "29-02-2000");
        driverRepository.addDriver(driver);
        Driver updatedDriver = new Driver("40362951AB", "Nathan Van", 10, "Heavy", "12|Pier St|Altona|VIC|AUS", "29-02-2000");
        // FIXED: Changed to updateDriver and flipped assertion to assertTrue because exactly 10 years is allowed
        assertTrue(driverRepository.updateDrivers(updatedDriver), "System should allow the modification at the exact 10-year experience milestone");
    }

    // ==========================================
    // CONDITION D5: IMMUTABILITY TESTS
    // ==========================================
    
    // Test 1: UPDATE IMMUTABILITY TEST (normal case - update address)
    @Test
    public void testAllowUpdateToSecondaryFields() {
        Driver driver = new Driver("40362951AB", "Nathan Van", 4, "Heavy", "12|Pier St|Altona|VIC|AUS", "29-02-2000");
        driverRepository.addDriver(driver);
        Driver updatedDriver = new Driver("40362951AB", "Nathan Van", 4, "Heavy", "23|Hemsley Drive|Deer Park|VIC|AUS", "29-02-2000");
        // FIXED: Changed to call updateDriver to properly target updating logic rules
        assertTrue(driverRepository.updateDrivers(updatedDriver), "System should return the success message to confirm driver with valid update to secondary fields is allowed");
    }

    // Test 2: UPDATE IMMUTABILITY TEST (invalid case - update Driver's name)
    @Test
    public void testProhibitUpdateToPrimaryFields() {
        Driver driver = new Driver("40362951AB", "Nathan Van", 4, "Heavy", "12|Pier St|Altona|VIC|AUS", "29-02-2000");
        driverRepository.addDriver(driver);
        Driver updatedDriver = new Driver("40362951AB", "Duc Van", 4, "Heavy", "12|Pier St|Altona|VIC|AUS", "29-02-2000");
        // FIXED: Changed to call updateDriver to properly target updating logic rules
        assertFalse(driverRepository.updateDrivers(updatedDriver), "System should return the error message to confirm driver with invalid update to primary fields is prohibited");
    }
    
    // Test 3: UPDATE IMMUTABILITY TEST (edge case - update Driver's ID)
    @Test
    public void testProhibitUpdateToDriverID() {
        Driver driver = new Driver("40362951AB", "Nathan Van", 4, "Heavy", "12|Pier St|Altona|VIC|AUS", "29-02-2000");
        driverRepository.addDriver(driver);
        Driver updatedDriver = new Driver("40362952XX", "Nathan Van", 4, "Heavy", "12|Pier St|Altona|VIC|AUS", "29-02-2000");
        assertFalse(driverRepository.updateDrivers(updatedDriver), "System should return the error message to confirm driver with invalid update to Driver ID is prohibited");
    }
}