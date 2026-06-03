package com.busguidance;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BusUnitTest {
   @Test
   void verifyPerfectValidBus() {

    Bus bus = new Bus("87654321", 50, 80.0, "Electricity");

    assertTrue(bus.ValidBusID());
    assertTrue(bus.UpdateCapacity(35));
    assertTrue(Bus.canDriveBus(38, 50));
    assertTrue(Bus.allowedElectricBus(6,"Electricity"));
    assertTrue(Bus.ValidLicence("Heavy", "Electricity"));
   } 

   @Test
   void duplicateBusID(){
    String existsBusID = "87654321";
    String newBusID = "87654321";

    assertEquals(existsBusID, newBusID);
   }

   @Test
   void busIDShort() {
    Bus bus = new Bus("7654321", 50, 80.0,"Electricty");

    assertFalse(bus.ValidBusID());
   }

   @Test
   void busIDLong() {
    Bus bus = new Bus("987654321", 50, 80.0, "Electricty");

    assertFalse(bus.ValidBusID());
   }

   @Test
   void busIDwithALetter(){
    Bus bus = new Bus("8765432a", 50, 80.0, "Electricity");

    assertFalse(bus.ValidBusID());
   }

   @Test
   void DecreaseCapacity() {
    Bus bus = new Bus("87654321", 44, 80.0, "Electricty");

    assertTrue(bus.UpdateCapacity(40));
   }

   @Test
   void IncreaseCapacity() {
    Bus bus = new Bus("87654321", 44, 80.0, "Electricty");

    assertFalse(bus.UpdateCapacity(48));
   }

   @Test
   void SameCapacity() {
    Bus bus = new Bus("87654321", 44, 80.0, "Electricty");

    assertTrue(bus.UpdateCapacity(44));
   }

   @Test
   void DriversAge50Capacity50() {
    assertTrue(Bus.canDriveBus(50, 50));
   }

   @Test
   void DriversAge38Capacity86() {
    assertTrue(Bus.canDriveBus(38, 86));
   }

   @Test
   void DriversAge67Capacity72() {
    assertFalse(Bus.canDriveBus(67, 72));
   }

   @Test
   void DriversAge64Capacity26() {
    assertTrue(Bus.canDriveBus(64, 26));
   }

   @Test
   void Experience7FuelElectricBus() {
    assertTrue(Bus.allowedElectricBus(7, "Electricity"));
   }

   @Test
   void Experience3FuelElectricBus() {
    assertFalse(Bus.allowedElectricBus(3, "Electricity"));
   }

   @Test
   void Experience5FuelElectricBus() {
    assertTrue(Bus.allowedElectricBus(5, "Electricity"));
   }

   @Test
   void HeavyLicenceFuelHybridBus() {
    assertTrue(Bus.ValidLicence("Heavy", "Hybrid"));
   }

   @Test
   void HeavyLicenceFuelElectricBus() {
    assertTrue(Bus.ValidLicence("Heavy", "Electricity"));
   }

   @Test
   void PublicTransportLicenseHybridBus() {
    assertTrue(Bus.ValidLicence("PublicTransport", "Hybrid"));
   }
}

