package com.busguidance;

public class Main {

    public static void main(String[] args) {

        PassengerApp app = new PassengerApp();
        BusDriver driver = new BusDriver();

        app.login("passenger01", "password123");
        driver.viewSchedule();
        driver.requestUpdatedSchedule();
    }
}
