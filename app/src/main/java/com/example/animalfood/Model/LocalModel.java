package com.example.animalfood.Model;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

public class LocalModel {
    private String userName;
    private String phoneNumber;
    private CustomLatLng userLocation;
    private String building;
    private String bridge;
    private String note;

    public LocalModel() {
    }

    public LocalModel(String userName, String phoneNumber, CustomLatLng userLocation, String building, String bridge, String note) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.userLocation = userLocation;
        this.building = building;
        this.bridge = bridge;
        this.note = note;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CustomLatLng getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(CustomLatLng userLocation) {
        this.userLocation = userLocation;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getBridge() {
        return bridge;
    }

    public void setBridge(String bridge) {
        this.bridge = bridge;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}






