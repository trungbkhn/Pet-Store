package com.example.animalfood.Model;

public class LocationModel {
    private double latitude;
    private double longitude;

    public LocationModel() {
        // Constructor không đối số cần cho Firebase
    }

    public LocationModel(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
