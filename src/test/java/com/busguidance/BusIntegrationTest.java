package com.busguidance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BusIntegrationTest {
    @Test
    //This is a test!
    void test() {
        Bus bus = new Bus("12345678",70, 100, "Electricity");
        BusRepository busRepository = new BusRepository();
        
        assertEquals(busRepository.add(bus), 1);

    }

}
 