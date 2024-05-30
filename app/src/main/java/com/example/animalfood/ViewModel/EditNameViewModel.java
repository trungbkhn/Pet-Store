package com.example.animalfood.ViewModel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.animalfood.Model.UserModel;
import com.example.animalfood.Repository.UserRepository;

public class EditNameViewModel extends ViewModel {
    private UserRepository userRepository = new UserRepository();

    public EditNameViewModel() {
    }
    public void updateName(String name){
        userRepository.updateName(name);
    }
}

