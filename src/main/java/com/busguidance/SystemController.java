package com.busguidance;

public class SystemController {

    private Security security;
    private Schedule schedule;
    private Admin admin;

    public SystemController() {
        security = new Security();
        schedule = new Schedule();
        admin = new Admin();
    }

    public boolean checkPassword(String username, String password) {
        System.out.println("System verifies password.");
        return security.validatePassword(username, password);
    }

    public void retrieveSchedule() {
        System.out.println("System retrieves schedule.");
        schedule.displaySchedule();
    }

    public void updateScheduleRequest() {
        System.out.println("System requests schedule update.");
        admin.modifySchedule();
        schedule.displayUpdatedSchedule();
    }
}
