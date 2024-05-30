package com.example.animalfood.ViewModel;

import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.animalfood.Model.Place;
import com.example.animalfood.Repository.ChooseLocalRepository;



import java.util.ArrayList;
import java.util.List;

public class ChooseLocalViewModel extends ViewModel {
    private final ChooseLocalRepository repository;
    private final MutableLiveData<Location> userLocation = new MutableLiveData<>();
    private final MediatorLiveData<List<Place>> nearbyPlaces = new MediatorLiveData<>();


    public ChooseLocalViewModel(@NonNull Application application) {
        this.repository = new ChooseLocalRepository(application);

        nearbyPlaces.addSource(userLocation, location -> {
            if (location != null) {
                LiveData<List<Place>> placesSource = repository.getNearbyPlaces(location.getLatitude(), location.getLongitude(), 1000); // bán kính 1000 mét
                nearbyPlaces.addSource(placesSource, nearbyPlaces::setValue);
            } else {
                nearbyPlaces.setValue(new ArrayList<>());
            }
        });
    }


    public void setUserLocation(Location location) {
        userLocation.setValue(location);
    }

    public LiveData<Location> getUserLocation() {
        return userLocation;
    }

    public LiveData<List<Place>> getNearbyPlaces() {
        return nearbyPlaces;
    }
}


