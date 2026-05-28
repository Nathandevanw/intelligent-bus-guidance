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

    // Define the getter functions for the Bus class.
    public String getBusID(){
        return this.busID;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public double getFuelLevel() {
        return this.fuelLevel;
    }
    
    public String getFuelType() {
        return this.fuelType;
    }

    public int getAge() {
        return this.age;
    }

    public int getExpYears() {
        return this.expYears;
    }

    public String getLicense() {
        return this.license;
    }

    // Define the setter functions for the Bus Class
    public void setBusID(String busID) {
        this.busID = busID;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setFuelLevel(double fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setExpYears(int expYears) {
        this.expYears = expYears;
    }

    public void setLicense(String license) {
        this.license = license;
    }

}

