package com.example.animalfood.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.animalfood.Model.VoucherModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VoucherRepository {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private MutableLiveData<List<VoucherModel>> _listVoucher = new MutableLiveData<>();


    public LiveData<List<VoucherModel>> getAllListVoucher() {

        DatabaseReference ref = firebaseDatabase.getReference("Voucher");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<VoucherModel> listData = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    VoucherModel model = data.getValue(VoucherModel.class);
                    Log.d("VoucherRepository", "Model title: " + model.getTitle());
                    if (model != null) {
                        listData.add(model);
                        Log.d("VoucherRepository", "Loaded data: " + listData);
                    }
                }
                _listVoucher.setValue(listData);
                Log.d("VoucherRepository", "succeed load data: " );
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("VoucherRepository", "Fail load Voucher data: " + error.getMessage());
            }
        });

        return _listVoucher;
    }
}
