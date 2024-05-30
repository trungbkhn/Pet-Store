package com.example.animalfood.Model;

import java.util.List;

public class DogModel {
    private List<BreedDog> breeds;
    private String id;
    private String url;
    private int width;
    private int height;

    public DogModel() {
    }

    public DogModel(List<BreedDog> breeds, String id, String url, int width, int height) {
        this.breeds = breeds;
        this.id = id;
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public List<BreedDog> getBreeds() {
        return breeds;
    }

    public void setBreeds(List<BreedDog> breeds) {
        this.breeds = breeds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
