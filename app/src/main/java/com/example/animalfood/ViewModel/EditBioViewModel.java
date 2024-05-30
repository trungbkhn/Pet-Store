package com.example.animalfood.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.animalfood.Repository.UserRepository;

public class EditBioViewModel extends ViewModel {
    private UserRepository userRepository = new UserRepository();

    public EditBioViewModel() {
    }
    public void updateBio(String bio){
        userRepository.updateBio(bio);
    }
}

