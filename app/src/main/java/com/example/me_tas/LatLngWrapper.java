package com.example.me_tas;

public class LatLngWrapper {
    private double latitude;
    private double longitude;

    // Default constructor
    public LatLngWrapper() {}

    // Constructor with latitude and longitude
    public LatLngWrapper(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    // Getters and setters
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude, double v) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}