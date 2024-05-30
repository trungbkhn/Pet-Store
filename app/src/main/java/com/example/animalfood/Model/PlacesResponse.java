package com.example.animalfood.Model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlacesResponse {
    @SerializedName("results")
    List<Place> results;

    public List<Place> getResults() {
        return results;
    }
}
