package com.example.animalfood.Model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class FishModel {
    private int id;
    private String name;
    private String url;
    @SerializedName("img_src_set")
    private JsonElement imgSrcSet;

    private Meta meta;

    public FishModel(int id, String name, String url, JsonObject imgSrcSet, Meta meta) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.imgSrcSet = imgSrcSet;
        this.meta = meta;
    }

    public FishModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JsonElement getImgSrcSet() {
        return imgSrcSet;
    }

    public void setImgSrcSet(JsonElement imgSrcSet) {
        this.imgSrcSet = imgSrcSet;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public boolean hasValidImageSrcSet() {
        return imgSrcSet != null && imgSrcSet.isJsonObject() && imgSrcSet.getAsJsonObject().has("1.5x") && imgSrcSet.getAsJsonObject().has("2x");
    }

    public String getImageUrl() {
        if (hasValidImageSrcSet()) {
            return imgSrcSet.getAsJsonObject().get("1.5x").getAsString();
        } else if (imgSrcSet.isJsonPrimitive()) {
            return imgSrcSet.getAsString();
        } else {
            return "Not available";
        }
    }
}


