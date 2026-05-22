public class Tablet {

    private NavigationGPS navigationGPS;
    private TrafficData trafficData;
    private Hazards hazards;

    public Tablet() {
        navigationGPS = new NavigationGPS();
        trafficData = new TrafficData();
        hazards = new Hazards();
    }

    public void enterAddress() {
        System.out.println("Driver enters destination address.");

        navigationGPS.provideRoute();
        displayRouteInstructions();

        trafficData.displayTrafficAlerts();

        requestNewRoute();
        checkHazards();
        displayTripStatus();
        endTrip();
    }

    public void displayRouteInstructions() {
        System.out.println("Tablet displays route instructions.");
    }

    public void requestNewRoute() {
        System.out.println("Tablet requests a new route.");
        navigationGPS.provideNewRoute();
        displayRouteModification();
    }

    public void displayRouteModification() {
        System.out.println("Tablet displays route modification.");
    }

    public void checkHazards() {

        boolean hazardDetected = hazards.detectHazard();

        if (hazardDetected) {
            System.out.println("Hazard detected on route.");
            navigationGPS.provideSafeRoute();
            displayRouteModification();
        } else {
            System.out.println("No hazard detected. Continue trip.");
        }
    }

    public void displayTripStatus() {
        System.out.println("Tablet displays trip status.");
    }

    public void endTrip() {
        System.out.println("Trip ended successfully.");
    }
}
