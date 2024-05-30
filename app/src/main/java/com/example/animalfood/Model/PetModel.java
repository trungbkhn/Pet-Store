package com.example.animalfood.Model;

import java.io.Serializable;
import java.util.Objects;

public class PetModel implements Serializable {
    private String image;
    private String id;
    private String name;
    private String price;

    public PetModel() {
    }

    public PetModel(String image, String id, String name) {
        this.image = image;
        this.id = id;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetModel petModel = (PetModel) o;
        return Objects.equals(id, petModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
