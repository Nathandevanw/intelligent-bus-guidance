package com.busguidance;

public class Main {

    public static void main(String[] args) {

        PassengerApp app = new PassengerApp();
        //Driver driver = new Driver();

        app.login("passenger01", "password123");
        //driver.viewSchedule();
        //driver.requestUpdatedSchedule();
    }
}
