package com.example.animalfood.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.animalfood.Repository.UserRepository;

public class EditPhoneNumberViewModel extends ViewModel {
    private UserRepository userRepository = new UserRepository();

    public EditPhoneNumberViewModel() {
    }
    public void updatePhoneNumber(String phoneNumber){
        userRepository.updatePhoneNumber(phoneNumber);
    }
}
