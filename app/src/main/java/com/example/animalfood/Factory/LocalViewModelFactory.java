package com.example.animalfood.Factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.animalfood.Repository.LocalDetailRepository;
import com.example.animalfood.ViewModel.LocalViewModel;

public class LocalViewModelFactory implements ViewModelProvider.Factory {
    private LocalDetailRepository localDetailRepository;

    public LocalViewModelFactory(LocalDetailRepository localDetailRepository) {
        this.localDetailRepository = localDetailRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LocalViewModel.class)) {
            return (T) new LocalViewModel(localDetailRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
