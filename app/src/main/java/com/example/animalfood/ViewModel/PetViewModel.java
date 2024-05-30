package com.example.animalfood.ViewModel;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.animalfood.Model.BirdModel;
import com.example.animalfood.Model.CatModel;
import com.example.animalfood.Model.DogModel;
import com.example.animalfood.Model.FishModel;
import com.example.animalfood.Repository.PetRepository;

import java.util.List;


public class PetViewModel extends ViewModel {

    private PetRepository repository;

    public PetViewModel(PetRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<CatModel>> getListCat() {
        return repository.getCatModel();
    }

    public LiveData<List<DogModel>> getListDog() {
        return repository.getDogModel();
    }

    public LiveData<List<BirdModel>> getListBird() {
        return repository.getBirdModel();
    }

    public LiveData<List<FishModel>> getListFish() {
        return repository.getFishModel();
    }
}

