package com.example.animalfood.Model;

import com.google.gson.annotations.SerializedName;

public class CategoryModel {
    private int id;
    private String picUrl;
    private String title;

    CategoryModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
    public String getPicUrl() {
        return picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
