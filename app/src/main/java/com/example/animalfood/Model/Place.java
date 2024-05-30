package com.example.animalfood.Model;

import com.google.gson.annotations.SerializedName;

public class Place {
    @SerializedName("name")
    String name;
    @SerializedName("vicinity")
    String vicinity;

    public String getName() {
        return name;
    }

    public String getVicinity() {
        return vicinity;
    }
}