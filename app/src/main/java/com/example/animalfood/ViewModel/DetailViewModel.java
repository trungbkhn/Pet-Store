package com.example.animalfood.ViewModel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.animalfood.Helper.ManagmentCart;
import com.example.animalfood.Model.BestSellerModel;
import com.example.animalfood.Model.Product;
import com.example.animalfood.Repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailViewModel extends ViewModel {
    private UserRepository userRepository;
    private MutableLiveData<BestSellerModel> _item;
    private MutableLiveData<String> _selectedSize = new MutableLiveData<>();
    private MutableLiveData<List<Product>> _listProduct = new MutableLiveData<>();
    private MutableLiveData<Boolean> _isFavorite = new MutableLiveData<>();
    public LiveData<Boolean> isFavorite = _isFavorite;

    private ManagmentCart managmentCart;

    public DetailViewModel(Application application, UserRepository userRepository) {
        _item = new MutableLiveData<>();
        managmentCart = new ManagmentCart(application);
        this.userRepository = userRepository;
    }

    public LiveData<BestSellerModel> getItem() {
        return _item;
    }

    public void setItem(BestSellerModel item) {
        this._item.setValue(item);
    }

    public LiveData<String> getSelectedSize() {
        return _selectedSize;
    }

    public void setSelectedSize(String size) {
        this._selectedSize.setValue(size);
    }
    public void checkIsFavoriteProduct(int productId) {
        userRepository.isFavoriteProduct(productId).observeForever(isFav -> {
            _isFavorite.setValue(isFav);
            Log.d("DetailViewModel","is fav " + isFav);
        });
    }
    public void insertProduct(int productId) {
        userRepository.insertFavoriteProduct(productId);
        userRepository.isFavoriteProduct(productId).observeForever(isFav -> {
            Log.d("DetailViewModel","is fav " + isFav);
            _isFavorite.setValue(true);
        });

    }
    public void removeProduct(int productId) {
        userRepository.removeFavoriteProduct(productId);
        userRepository.isFavoriteProduct(productId).observeForever(isFav -> {
            _isFavorite.setValue(isFav);
        });
    }
}



