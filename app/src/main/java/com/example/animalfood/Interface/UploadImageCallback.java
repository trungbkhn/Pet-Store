package com.example.animalfood.Interface;

public interface UploadImageCallback {
    void onSuccess(String imageUrl);
    void onFailure(String errorMessage);
}
