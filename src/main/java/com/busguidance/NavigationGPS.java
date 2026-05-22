package com.busguidance;

public class NavigationGPS {

    public void provideRoute() {
        System.out.println("GPS provides initial route.");
    }

    public void provideNewRoute() {
        System.out.println("GPS provides alternative route.");
    }

    public void provideSafeRoute() {
        System.out.println("GPS provides safe route after hazard detection.");
    }
}
