package com.example.animalfood.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.animalfood.Model.BestSellerModel;
import com.example.animalfood.Model.CategoryModel;
import com.example.animalfood.Model.SliderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private static final String TAG = "LoadDataFromFirebase";
    private MutableLiveData<List<SliderModel>> _banner = new MutableLiveData<>();
    private MutableLiveData<String> _nameUser = new MutableLiveData<>();
    private MutableLiveData<List<CategoryModel>> _category = new MutableLiveData<>();
    private MutableLiveData<List<BestSellerModel>> _bestSeller = new MutableLiveData<>();
    public LiveData nameUser = _nameUser;
    public LiveData<List<SliderModel>> banners = _banner;
    public LiveData<List<CategoryModel>> categories = _category;
    public LiveData<List<BestSellerModel>> bestSellers = _bestSeller;


    public void loadBanners() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Banner");
        Log.d(TAG, "Calling addValueEventListener");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<SliderModel> lists = new ArrayList<>();
                for (DataSnapshot childrenSnapshot : snapshot.getChildren()) {
                    SliderModel sliderModel = childrenSnapshot.getValue(SliderModel.class);
                    if (sliderModel != null) {
                        lists.add(sliderModel);
                    }
                }
                _banner.setValue(lists);
                Log.d(TAG, "Succeed loaded! Number of items's banners: " + lists.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to load banner data from database: " + error.getMessage());
            }
        });
    }

    public void loadCategory() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Category");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<CategoryModel> list = new ArrayList();

                for (DataSnapshot childData : snapshot.getChildren()) {
                    CategoryModel model = childData.getValue(CategoryModel.class);
                    if (model != null) {
                        list.add(model);
                    }
                }
                _category.setValue(list);
                Log.d(TAG, "Succeed loaded! Number of items 's category: " + list.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to load category data from database: " + error.getMessage());
            }
        });
    }

    public void loadBestSeller() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Items");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<BestSellerModel> list = new ArrayList();
                for (DataSnapshot childData : snapshot.getChildren()) {
                    BestSellerModel model = childData.getValue(BestSellerModel.class);
                    if (model != null) {
                        list.add(model);
                    }
                }
                _bestSeller.setValue(list);
                Log.d(TAG, "Succeed loaded! Number of items 's BestSeller: " + list.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Failed to load BestSeller data from database: " + error.getMessage());
            }
        });
    }

    public void getName(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getUid();
        if (uid != null && !uid.isEmpty()){
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(uid);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("name").exists()) {
                        _nameUser.setValue(String.valueOf(snapshot.child("name").getValue()));
                    } else {
                        Log.e(TAG,"Fail to load username because name does not exist in the database for this user");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e(TAG,"Fail to load username due to"+ error.getMessage());
                }
            });
        }

    }
}
