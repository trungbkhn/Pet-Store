package com.example.animalfood.ViewModel;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.animalfood.Helper.ManagmentCart;
import com.example.animalfood.Model.BestSellerModel;
import com.example.animalfood.Repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartViewModel extends ViewModel {
    private MutableLiveData<List<BestSellerModel>> _listModel = new MutableLiveData<List<BestSellerModel>>();
    public LiveData<List<BestSellerModel>> listModel = _listModel;
    private MutableLiveData<Double> _totalFee = new MutableLiveData<>();
    public LiveData<Double> totalFee = _totalFee;
    private ItemRepository itemRepository = new ItemRepository();


    CartViewModel() {
    }

    public LiveData<Double> getTotalFee(){
        return _totalFee;
    }


    public void setListModel(LifecycleOwner lifecycleOwner) {
        itemRepository.getItemList().observe(lifecycleOwner, listModel -> {
            _listModel.postValue(listModel);
        });
    }

    public LiveData<List<BestSellerModel>> getListModel() {
        return itemRepository.getItemList();
    }

    public void getTotalAllFee(LifecycleOwner lifecycleOwner,ManagmentCart managmentCart) {
        LiveData<List<BestSellerModel>> models = getListModel();
        models.observe(lifecycleOwner, new Observer<List<BestSellerModel>>() {
            @Override
            public void onChanged(List<BestSellerModel> bestSellerModels) {
                double totalFee = 0.0;
                if (bestSellerModels != null) {
                    Map<String, ?> allItems = managmentCart.getAllItems();
                    for (Map.Entry<String, ?> item : allItems.entrySet()) {
                        String itemKey = item.getKey();
                        String itemTitle = itemKey.split("_")[0];
                        int quantity = (Integer) item.getValue();
                        for (BestSellerModel model : bestSellerModels) {
                            String modelTitle = model.getTitle();
                            if (itemTitle.equals(modelTitle)) {
                                totalFee += model.getPrice() * quantity;
                            }
                        }
                    }
                } else {
                    Log.d("CartViewModel","Model size is null");
                }
                _totalFee.setValue(totalFee);
                // Now you can use totalFee
                Log.d("CartViewModel","Total fee: " + totalFee);
            }
        });
    }
}
