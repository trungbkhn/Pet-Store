package com.example.animalfood.Factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.animalfood.Repository.PetRepository;
import com.example.animalfood.ViewModel.PetViewModel;

public class PetViewModelFactory implements ViewModelProvider.Factory{
    private PetRepository repository;

    public PetViewModelFactory(PetRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PetViewModel.class)) {
            return (T) new PetViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
