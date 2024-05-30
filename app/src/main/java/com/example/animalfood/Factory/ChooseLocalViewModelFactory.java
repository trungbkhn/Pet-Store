package com.example.animalfood.Factory;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.animalfood.ViewModel.ChooseLocalViewModel;

public class ChooseLocalViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;

    public ChooseLocalViewModelFactory(Application application) {
        mApplication = application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ChooseLocalViewModel.class)) {
            return (T) new ChooseLocalViewModel(mApplication);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
