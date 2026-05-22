public class PassengerApp {

    private SystemController systemController;

    public PassengerApp() {
        systemController = new SystemController();
    }

    public void login(String username, String password) {
        System.out.println("Passenger enters login details.");

        boolean loginStatus = systemController.checkPassword(username, password);

        if (loginStatus) {
            System.out.println("Passenger login successful.");
            systemController.retrieveSchedule();
        } else {
            System.out.println("Passenger login failed.");
        }
    }
}
