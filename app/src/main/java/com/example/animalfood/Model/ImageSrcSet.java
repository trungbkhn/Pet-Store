package com.example.animalfood.Model;

import com.google.gson.annotations.SerializedName;

public class ImageSrcSet {
    @SerializedName("1.5x")
    private String _1_5x;
    @SerializedName("2x")
    private String _2x;

    public ImageSrcSet() {
    }

    public String get_1_5x() {
        return _1_5x;
    }

    public void set_1_5x(String _1_5x) {
        this._1_5x = _1_5x;
    }

    public String get_2x() {
        return _2x;
    }

    public void set_2x(String _2x) {
        this._2x = _2x;
    }
}
