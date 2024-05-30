package com.example.animalfood.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.animalfood.Model.BestSellerModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagmentCart {
    private Context context;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public ManagmentCart(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("Cart", Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    public void insertItem(BestSellerModel item, String size) {
        String itemKey = getKey(item, size);
        if (sharedPreferences.contains(itemKey)) {
            increaseQuantity(item, size);
        } else {
            sharedPreferences.edit().putInt(itemKey, 1).apply();
        }
    }

    public boolean isAddedToCart(BestSellerModel item, String size) {
        String itemKey = getKey(item, size);
        return sharedPreferences.contains(itemKey);
    }

    private String getKey(BestSellerModel item, String size) {
        return item.getTitle() + "_" + size;
    }


    public void increaseQuantity(BestSellerModel item, String size) {
        String itemKey = getKey(item, size);
        if (sharedPreferences.contains(itemKey)) {
            int currentQuantity = sharedPreferences.getInt(itemKey, 0);
            sharedPreferences.edit().putInt(itemKey, currentQuantity + 1).apply();
        }
    }

    public void decreaseQuantity(BestSellerModel item, String size) {
        String itemKey = getKey(item, size);
        if (sharedPreferences.contains(itemKey)) {
            int currentQuantity = sharedPreferences.getInt(itemKey, 0);
            if (currentQuantity > 1) {
                sharedPreferences.edit().putInt(itemKey, currentQuantity - 1).apply();
            } else {
                removeItem(itemKey);
            }
        }
    }

    private void removeItem(String itemKey) {
        sharedPreferences.edit().remove(itemKey).apply();
    }

    public int getNumberOfItems() {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        return allEntries.size();
    }
    public Map<String, Integer> getAllItems() {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        Map<String, Integer> allItems = new HashMap<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            allItems.put(entry.getKey(), (Integer) entry.getValue());
        }
        return allItems;
    }

    public List<BestSellerModel> getItemsWithSelectedSize() {
        List<BestSellerModel> items = new ArrayList<>();
        Map<String, ?> allItems = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allItems.entrySet()) {
            String[] parts = entry.getKey().split("_");
            String title = parts[0];
            String selectedSize = parts[1];
            BestSellerModel model = new BestSellerModel(); // Tạo một BestSellerModel mới
            model.setTitle(title); // Gán title
            model.setSelectedSize(selectedSize); // Gán selectedSize
            items.add(model);
        }
        return items;
    }

    public int getTotalQuantity(BestSellerModel item, String size) {
        String itemKey = getKey(item, size);
        if (sharedPreferences.contains(itemKey)) {
            return sharedPreferences.getInt(itemKey, 0);
        }
        return 0;
    }

}






