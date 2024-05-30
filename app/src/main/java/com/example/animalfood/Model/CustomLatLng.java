package com.example.animalfood.Model;

import com.google.android.gms.maps.model.LatLng;

public class CustomLatLng {
    private double latitude;
    private double longitude;

    public CustomLatLng() {
        // Required for Firebase
    }

    public CustomLatLng(double latitude, double longitude) {
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

    public LatLng toLatLng() {
        return new LatLng(latitude, longitude);
    }

    public static CustomLatLng fromLatLng(LatLng latLng) {
        return new CustomLatLng(latLng.latitude, latLng.longitude);
    }
}
