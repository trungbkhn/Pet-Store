package com.example.animalfood.Repository;

import static com.example.animalfood.WebService.Constants.apiKey;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.animalfood.Model.Place;
import com.example.animalfood.Model.PlacesResponse;
import com.example.animalfood.WebService.ApiClient;
import com.example.animalfood.WebService.ApiService;
import com.example.animalfood.WebService.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseLocalRepository {
    private FusedLocationProviderClient fusedLocationClient;
    private ApiService apiService;

    public ChooseLocalRepository(Application application) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(application);
        this.apiService = ApiClient.getNearbyPlaces();
    }

    public LiveData<Location> getLastLocation(Application application) {
        MutableLiveData<Location> locationData = new MutableLiveData<>();
        if (ActivityCompat.checkSelfPermission(application, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    Log.d("ChooseLocalRepository","location: "+ location.getLatitude());
                    locationData.setValue(location);
                } else {
                    Log.d("ChooseLocalRepository","location: null");
                }

            });
        }
        return locationData;
    }

    public LiveData<List<Place>> getNearbyPlaces(double latitude, double longitude, int radius) {
        MutableLiveData<List<Place>> nearbyPlacesLiveData = new MutableLiveData<>();
        String location = latitude + "," + longitude;
        apiService.getNearbyPlaces(location, radius, apiKey).enqueue(new Callback<PlacesResponse>() {
            @Override
            public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    nearbyPlacesLiveData.setValue(response.body().getResults());
                    Log.d("ChooseLocalRepository","getNearbyPlaces succeed!, data: "+nearbyPlacesLiveData.getValue());
                } else {
                    nearbyPlacesLiveData.setValue(new ArrayList<>());
                    Log.d("ChooseLocalRepository","getNearbyPlaces null");
                }
            }

            @Override
            public void onFailure(Call<PlacesResponse> call, Throwable t) {
                Log.e("ChooseLocalRepository","getNearbyPlaces fail: "+ t.getMessage());
                nearbyPlacesLiveData.setValue(new ArrayList<>());
            }
        });
        return nearbyPlacesLiveData;
    }
}
