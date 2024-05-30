package com.example.animalfood.ViewModel;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.animalfood.Interface.UploadImageCallback;
import com.example.animalfood.Model.UserModel;
import com.example.animalfood.Repository.UserRepository;

public class ProfileDetailViewModel extends ViewModel {
    private UserRepository userRepository = new UserRepository();
    MutableLiveData<UserModel> _userModel = new MutableLiveData<>();
    MutableLiveData<String> _profileImageUrl = new MutableLiveData<>();
    MutableLiveData<String> _uploadError = new MutableLiveData<>();

    public ProfileDetailViewModel() {
    }

    public LiveData<UserModel> getUserModel() {
        return _userModel;
    }

    public void getModel(LifecycleOwner lifecycleOwner) {
        userRepository.loadInforUser().observe(lifecycleOwner, model -> {
            Log.d("ProfileDetailViewModel", "name: " + model.getName());
            if (model != null) {
                _userModel.setValue(model);
            }
        });
    }

    public LiveData<String> getProfileImageUrl() {
        return _profileImageUrl;
    }

    public LiveData<String> getUploadError() {
        return _uploadError;
    }

    public void updateName(String name) {
        userRepository.updateName(name);
    }

    public void updateBio(String bio) {
        userRepository.updateBio(bio);
    }

    public void updatePhoneNumber(String phoneNumber) {
        userRepository.updateBio(phoneNumber);
    }

    public void updateGender(String gender) {
        userRepository.updateGender(gender);
    }

    public void updateEmail(String gmail) {
        userRepository.updateEmail(gmail);
    }

    public void uploadProfileImage(Uri imageUri) {
        userRepository.uploadProfileImage(imageUri, new UploadImageCallback() {
            @Override
            public void onSuccess(String imageUrl) {
                _profileImageUrl.setValue(imageUrl);
            }

            @Override
            public void onFailure(String errorMessage) {
                _uploadError.setValue(errorMessage);
            }
        });
    }


}
