package com.example.animalfood.Repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.animalfood.Model.BirdModel;
import com.example.animalfood.Model.CatModel;
import com.example.animalfood.Model.DogModel;
import com.example.animalfood.Model.FishModel;
import com.example.animalfood.WebService.ApiClient;
import com.example.animalfood.WebService.ApiService;
import com.example.animalfood.WebService.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetRepository {
    public MutableLiveData<List<CatModel>> _listCats = new MutableLiveData<>();
    public MutableLiveData<List<DogModel>> _listDogs = new MutableLiveData<>();
    public MutableLiveData<List<BirdModel>> _listBirds = new MutableLiveData<>();
    public MutableLiveData<List<FishModel>> _listFishes = new MutableLiveData<>();
    public LiveData<List<CatModel>> listCats = _listCats;
    public LiveData<List<DogModel>> listDogs = _listDogs;
    public LiveData<List<BirdModel>> listBirds = _listBirds;
    public LiveData<List<FishModel>> listFishes = _listFishes;

    public LiveData<List<CatModel>> getCatModel() {
        ApiClient.getApiServiceCat().getCatModel("30", Constants.API_KEY_CAT).enqueue(new Callback<List<CatModel>>() {
            @Override
            public void onResponse(Call<List<CatModel>> call, Response<List<CatModel>> response) {
                List<CatModel> list = response.body();
                if (list != null && response.isSuccessful()) {
                    _listCats.setValue(list);
                    Log.d("PetRepository", "Load cat data success, size list " + list.size());
                }
            }

            @Override
            public void onFailure(Call<List<CatModel>> call, Throwable throwable) {
                Log.d("PetRepository", "Fail load cat data " + throwable.getMessage());
                _listCats.setValue(null);
            }
        });
        return listCats;
    }

    public LiveData<List<DogModel>> getDogModel() {
        ApiClient.getApiServiceDog().getDogModel("30", Constants.API_KEY_DOG).enqueue(new Callback<List<DogModel>>() {
            @Override
            public void onResponse(Call<List<DogModel>> call, Response<List<DogModel>> response) {
                List<DogModel> list = response.body();
                if (list != null && response.isSuccessful()) {
                    _listDogs.setValue(list);
                    Log.d("PetRepository", "Load cat data success, size list " + list.size());
                }
            }

            @Override
            public void onFailure(Call<List<DogModel>> call, Throwable throwable) {
                Log.d("PetRepository", "Fail load cat data " + throwable.getMessage());
                _listDogs.setValue(null);
            }
        });
        return listDogs;
    }
    public LiveData<List<BirdModel>> getBirdModel()
    {
        ApiClient.getApiServiceBird().getBirdModel().enqueue(new Callback<List<BirdModel>>() {
            @Override
            public void onResponse(Call<List<BirdModel>> call, Response<List<BirdModel>> response) {
                List<BirdModel> list = response.body();
                if (list != null && response.isSuccessful()){
                    _listBirds.setValue(list);
                    Log.d("PetRepository", "Load bird data success, size list " + list.size());
                } else {
                    Log.d("PetRepository", "Load bird data fail or null");
                }
            }

            @Override
            public void onFailure(Call<List<BirdModel>> call, Throwable throwable) {
                Log.d("PetRepository", "Fail load cat data " + throwable.getMessage());
                _listBirds.setValue(null);
            }
        });
        return listBirds;
    }

    public LiveData<List<FishModel>> getFishModel()
    {
        ApiClient.getApiServiceFish().getFishModel().enqueue(new Callback<List<FishModel>>() {
            @Override
            public void onResponse(Call<List<FishModel>> call, Response<List<FishModel>> response) {
                List<FishModel> list = response.body();
                if (list != null && response.isSuccessful()){
                    _listFishes.setValue(list);
                    Log.d("PetRepository", "Load fish data success, size list " + list.size());
                } else {
                    Log.d("PetRepository", "Load fish data fail or null");
                }
            }

            @Override
            public void onFailure(Call<List<FishModel>> call, Throwable throwable) {
                Log.d("PetRepository", "Fail load fish data " + throwable.getMessage());
                _listFishes.setValue(null);
            }
        });
        return listFishes;
    }

}
