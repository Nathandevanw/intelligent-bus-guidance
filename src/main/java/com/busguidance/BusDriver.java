package com.busguidance;

public class BusDriver {

    private SystemController systemController;

    public BusDriver() {
        systemController = new SystemController();
    }

    public void viewSchedule() {
        System.out.println("Bus driver views assigned schedule.");
        systemController.retrieveSchedule();
    }

    public void requestUpdatedSchedule() {
        System.out.println("Bus driver requests updated schedule.");
        systemController.updateScheduleRequest();
    }
}
