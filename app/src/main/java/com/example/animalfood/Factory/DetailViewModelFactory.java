package com.example.animalfood.Factory;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.animalfood.Repository.UserRepository;
import com.example.animalfood.ViewModel.DetailViewModel;

public class DetailViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private UserRepository userRepository;

    public DetailViewModelFactory(Application application) {
        mApplication = application;
        userRepository = new UserRepository();
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new DetailViewModel(mApplication, userRepository);
    }
}

