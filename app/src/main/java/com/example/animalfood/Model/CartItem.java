package com.example.animalfood.Model;

public class CartItem {
    private String itemKey;
    private int quantity;

    public CartItem(String itemKey, int quantity) {
        this.itemKey = itemKey;
        this.quantity = quantity;
    }

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

