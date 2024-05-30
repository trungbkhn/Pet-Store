package com.example.animalfood.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.animalfood.Model.CustomLatLng;
import com.example.animalfood.Repository.LocalDetailRepository;
import com.google.android.gms.maps.model.LatLng;

public class LocalDetailViewModel extends ViewModel {
    private final LocalDetailRepository repository;
    private final MutableLiveData<Boolean> isLocalAdded = new MutableLiveData<>();

    public LocalDetailViewModel() {
        this.repository = new LocalDetailRepository();
    }

    public LiveData<Boolean> getIsLocalAdded() {
        return isLocalAdded;
    }

    public void addNewHomeLocal(String name, String phoneNumber, CustomLatLng userLocation, String building, String bridge, String note) {
        repository.addNewHomeLocal(name, phoneNumber, userLocation, building, bridge, note);
        isLocalAdded.setValue(true);
    }

    public void addNewCompanyLocal(String name, String phoneNumber, CustomLatLng userLocation, String building, String bridge, String note) {
        repository.addNewCompanyLocal(name, phoneNumber, userLocation, building, bridge, note);
        isLocalAdded.setValue(true);
    }
}




