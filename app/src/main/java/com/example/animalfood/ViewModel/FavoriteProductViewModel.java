package com.example.animalfood.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.animalfood.Model.Product;
import com.example.animalfood.Repository.UserRepository;

import java.util.List;

public class FavoriteProductViewModel extends ViewModel {
    private UserRepository userRepository;
    private MutableLiveData<List<Product>> _listProduct = new MutableLiveData<>();
    public LiveData<List<Product>> listProduct = _listProduct;

    public FavoriteProductViewModel(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public LiveData<List<Product>> getListProduct(){
        userRepository.getFavoriteProduct().observeForever(new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                _listProduct.setValue(products);
            }
        });
        return _listProduct;
    }


    public void insertListProduct(int productId){
        userRepository.insertFavoriteProduct(productId);
    }
}
