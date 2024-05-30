package com.example.animalfood.WebService;

import com.example.animalfood.Model.BirdModel;
import com.example.animalfood.Model.CatModel;
import com.example.animalfood.Model.DogModel;
import com.example.animalfood.Model.FishModel;
import com.example.animalfood.Model.PlacesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("v1/images/search?has_breeds=true&order=ASC")
    Call<List<CatModel>> getCatModel(@Query("limit") String limit,
                                     @Query("api_key") String api_key);

    @GET("v1/images/search?has_breeds=true&order=ASC")
    Call<List<DogModel>> getDogModel(@Query("limit") String limit,
                                     @Query("api_key") String api_key);

    @GET("Birds")
    Call<List<BirdModel>> getBirdModel();

    @GET("fishes")
    Call<List<FishModel>> getFishModel();

    @GET("maps/api/place/nearbysearch/json")
    Call<PlacesResponse> getNearbyPlaces(@Query("location") String location, @Query("radius") int radius, @Query("key") String apiKey);
}
