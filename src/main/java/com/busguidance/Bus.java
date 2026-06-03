package com.busguidance;

public class Bus {
    private String busID;
    private int capacity;
    private double fuelLevel;
    private String fuelType; // Diesel, Hybrid, Electricity

    private int age;
    private int expYears; 
    private String license;

    // Define the constructor for the Bus class.
    public Bus(String busID, int capacity, double fuelLevel, String fuelType) {
        this.busID = busID;
        this.capacity = capacity;
        this.fuelLevel = fuelLevel;
        this.fuelType = fuelType;
        this.age = 30;
        this.expYears = 5;
        this.license = "PublicTransport";
    }

    public String getBusID(){
        return busID;
    }
    
    public int getCapacity(){
        return capacity;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public String getFuelType(){
        return fuelType;
    }

    public int getAge() {
        return this.age;
    }

    public int getExpYears() {
        return expYears;
    }

    public String getLicense() {
        return license;
    }


    public boolean ValidBusID(){
        return busID != null && busID.matches("\\d{8}");
    }

    public boolean UpdateCapacity(int newCapacity) {
        return newCapacity <= this.capacity;   
    }

    public static boolean canDriveBus(int driverAge, int busCapacity) {
        return !(driverAge > 50 && busCapacity >=50);
    }

    public static boolean allowedElectricBus(int yearsofexperience, String fuelType) {
        if (fuelType.equalsIgnoreCase("Electricity")) {
            return yearsofexperience >= 5;
        }
        return true;
    }

    public static boolean ValidLicence(String licenceType, String fuelType) {
        if (fuelType.equalsIgnoreCase("Electricity") 
            || fuelType.equalsIgnoreCase("Hybrid")) {
        
            return licenceType.equalsIgnoreCase("Heavy")
            || licenceType.equalsIgnoreCase("PublicTransport");
            }
        return true;
    }
   
}

        

