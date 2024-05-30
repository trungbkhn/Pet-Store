package com.example.animalfood.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BestSellerModel implements Parcelable  {
    private String description;
    private int productId;
    private ArrayList<String> picUrl;
    private Double price;
    private Double rating;
    private String sellerName;
    private String sellerPic;
    private String sellerTell;
    private Integer numberInCart ;
    private String title;
    private ArrayList<String> size;
    private String selectedSize;



    public BestSellerModel() {
    }
    public BestSellerModel(BestSellerModel model) {
        this.description = model.description;
        this.picUrl = model.picUrl;
        this.price = model.price;
        this.rating = model.rating;
        this.sellerName = model.sellerName;
        this.sellerPic = model.sellerPic;
        this.sellerTell = model.sellerTell;
        this.numberInCart = model.numberInCart;
        this.title = model.title;
        this.size = model.size;
        this.selectedSize = model.selectedSize;
        this.productId = model.productId;

    }
    public BestSellerModel(Parcel in) {
        productId = in.readInt();
        description = in.readString();
        picUrl = in.createStringArrayList();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readDouble();
        }
        sellerName = in.readString();
        sellerPic = in.readString();
        if (in.readByte() == 0) {
            sellerTell = null;
        } else {
            sellerTell = in.readString();
        }
        if (in.readByte() == 0) {
            numberInCart = null;
        } else {
            numberInCart = in.readInt();
        }
        title = in.readString();
        size = in.createStringArrayList();
    }

    public static final Creator<BestSellerModel> CREATOR = new Creator<BestSellerModel>() {
        @Override
        public BestSellerModel createFromParcel(Parcel in) {
            return new BestSellerModel(in);
        }

        @Override
        public BestSellerModel[] newArray(int size) {
            return new BestSellerModel[size];
        }
    };

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getSelectedSize() {
        return selectedSize;
    }

    public void setSelectedSize(String selectedSize) {
        this.selectedSize = selectedSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(ArrayList<String> picUrl) {
        this.picUrl = picUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerPic() {
        return sellerPic;
    }

    public void setSellerPic(String sellerPic) {
        this.sellerPic = sellerPic;
    }

    public String getSellerTell() {
        return sellerTell;
    }

    public void setSellerTell(String sellerTell) {
        this.sellerTell = sellerTell;
    }

    public Integer getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(Integer numberInCart) {
        this.numberInCart = numberInCart;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getSize() {
        return size;
    }

    public void setSize(ArrayList<String> size) {
        this.size = size;
    }
    public String getFirstPicUrl() {
        if (picUrl != null && !picUrl.isEmpty()) {
            return picUrl.get(0);
        }
        return null;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(productId);
        dest.writeString(description);
        dest.writeStringList(picUrl);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(price);
        }
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rating);
        }
        dest.writeString(sellerName);
        dest.writeString(sellerPic);
        if (sellerTell == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(sellerTell);
        }
        if (numberInCart == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(numberInCart);
        }
        dest.writeString(title);
        dest.writeStringList(size);
    }
}
