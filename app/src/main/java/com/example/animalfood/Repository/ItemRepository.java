package com.example.animalfood.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.animalfood.Model.BestSellerModel;
import com.example.animalfood.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ItemRepository {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public LiveData<List<BestSellerModel>> getItemList(){
        MutableLiveData<List<BestSellerModel>> liveData = new MutableLiveData<>();
        firebaseDatabase.getReference("Items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<BestSellerModel> list = new ArrayList<>();
                for (DataSnapshot childData : snapshot.getChildren()) {
                    BestSellerModel model = childData.getValue(BestSellerModel.class);
                    if (model != null) {
                        list.add(model);
                    }
                }
                liveData.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ItemRepository", "Database error: " + error.getMessage());
            }
        });
        return liveData;
    }
}

