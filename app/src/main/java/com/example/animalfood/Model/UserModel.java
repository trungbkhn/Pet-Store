package com.example.animalfood.Model;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private String name;
    private String email;
    private String profileImage;
    private long timeStamp;
    private String uid;
    private String userType;
    private String phoneNumber;
    private String gender;
    private String bio;

    private ArrayList<Product> favoriteProduct;


    public UserModel() {
    }

    public ArrayList<Product> getFavoriteProduct() {
        return favoriteProduct;
    }

    public void setFavoriteProduct(ArrayList<Product> favoriteProduct) {
        this.favoriteProduct = favoriteProduct;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
