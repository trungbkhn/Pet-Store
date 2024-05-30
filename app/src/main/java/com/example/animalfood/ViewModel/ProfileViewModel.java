package com.example.animalfood.ViewModel;

import android.widget.ImageView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.animalfood.R;
import com.example.animalfood.Repository.UserRepository;

public class ProfileViewModel extends ViewModel {

    private UserRepository userRepository = new UserRepository();
    public MutableLiveData<String> _nameUser = new MutableLiveData<>();
    public LiveData<String> nameUser = _nameUser;
    public MutableLiveData<String> _imgUserUrl = new MutableLiveData<>();
    public LiveData<String> imgUserUrl = _imgUserUrl;

    public void setNameUser(String name) {
        _nameUser.postValue(name);
    }

    public void setImgUserUrl(String imgPath) {
        _imgUserUrl.postValue(imgPath);
    }

    public void loadInforUser(LifecycleOwner lifecycleOwner) {
        userRepository.loadInforUser().observe(lifecycleOwner, userModel -> {
            if (userModel != null) {
                setNameUser(userModel.getName());
                setImgUserUrl(userModel.getProfileImage());
            } else {
                // User is null
                setNameUser("Unknown");
                setImgUserUrl(String.valueOf(R.drawable.profile));
            }
        });
    }
}
