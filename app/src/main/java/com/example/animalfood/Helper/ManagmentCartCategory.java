package com.example.animalfood.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.animalfood.Model.BestSellerModel;
import com.example.animalfood.Model.PetModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ManagmentCartCategory {

    private Context context;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public ManagmentCartCategory(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("CartPet", Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    public void insertPetToCart(PetModel model, String id) {
        String itemKey = getKey(model,id);
        if (!sharedPreferences.contains(itemKey)) {
            sharedPreferences.edit().putInt(itemKey, 1).apply();
        }
    }
    public boolean isAddedToCart(PetModel item, String id) {
        String itemKey = getKey(item,id);
        return sharedPreferences.contains(itemKey);
    }

    private String getKey(PetModel model, String id) {
        return model.getName() + "_" + id;
    }

    public void removeItem(String itemKey) {
        sharedPreferences.edit().remove(itemKey).apply();
    }

    public void removeItemById(PetModel model, String id) {
        String itemKey = getKey(model, id);
        sharedPreferences.edit().remove(itemKey).apply();
    }
    public int getCartSize() {
        return sharedPreferences.getAll().size();
    }
    public List<PetModel> getAllItems() {
        List<PetModel> itemList = new ArrayList<>();
        Map<String, ?> allItems = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allItems.entrySet()) {
            String[] keyParts = entry.getKey().split("_");
            String itemName = keyParts[0];
            String itemId = keyParts[1];
            PetModel model = new PetModel();
            model.setName(itemName);
            model.setId(itemId);
            itemList.add(model);
        }
        return itemList;
    }
}

