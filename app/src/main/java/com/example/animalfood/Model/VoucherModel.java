package com.example.animalfood.Model;

public class VoucherModel {
    private String title;
    private String expiry;
    private String description;
    private int voucherId;
    private String couponCode;

    public VoucherModel(String title, String expiry, String description, int voucherId) {
        this.title = title;
        this.expiry = expiry;
        this.description = description;
        this.voucherId = voucherId;
    }

    public VoucherModel() {
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }
}
