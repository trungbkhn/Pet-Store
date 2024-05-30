package com.example.animalfood.Repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.animalfood.Model.CustomLatLng;
import com.example.animalfood.Model.LocalModel;
import com.example.animalfood.Model.Place;
import com.example.animalfood.Model.PlacesResponse;
import com.example.animalfood.WebService.ApiClient;
import com.example.animalfood.WebService.ApiService;
import com.example.animalfood.WebService.Constants;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocalDetailRepository {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public LocalDetailRepository() {
    }

    public void addNewLocal(String locationType, String name, String phoneNumber, CustomLatLng userLocation, String building, String bridge, String note) {
        String uid = firebaseAuth.getUid();
        if (uid == null) {
            Log.e("LocalRepository", "UID is null");
            return;
        }

        DatabaseReference ref = firebaseDatabase.getReference("Location").child(uid).child(locationType);

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("userName", name);
        hashMap.put("phoneNumber", phoneNumber);
        hashMap.put("userLocation", userLocation);
        hashMap.put("building", building);
        hashMap.put("bridge", bridge);
        hashMap.put("note", note);

        ref.setValue(hashMap)
                .addOnSuccessListener(aVoid -> Log.d("LocalRepository", "Thêm dữ liệu thành công"))
                .addOnFailureListener(e -> Log.d("LocalRepository", "Lỗi khi thêm dữ liệu: " + e.getMessage()));
    }

    public void addNewHomeLocal(String name, String phoneNumber, CustomLatLng userLocation, String building, String bridge, String note) {
        addNewLocal("Home", name, phoneNumber, userLocation, building, bridge, note);
    }

    public void addNewCompanyLocal(String name, String phoneNumber, CustomLatLng userLocation, String building, String bridge, String note) {
        addNewLocal("Company", name, phoneNumber, userLocation, building, bridge, note);
    }

    public LiveData<LocalModel> getHomeAddress() {
        return getAddress("Home");
    }

    public LiveData<LocalModel> getCompanyAddress() {
        return getAddress("Company");
    }


    private LiveData<LocalModel> getAddress(String locationType) {
        String uid = firebaseAuth.getUid();
        MutableLiveData<LocalModel> modelAddress = new MutableLiveData<>();
        if (uid == null) {
            Log.e("LocalRepository", "UID is null");
            return modelAddress;
        }

        DatabaseReference ref = firebaseDatabase.getReference("Location").child(uid).child(locationType);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LocalModel model = snapshot.getValue(LocalModel.class);

                if (model != null) {
                    modelAddress.postValue(model);
                    Log.d("LocalDetailRepository", "get " + locationType + "Name: " + model.getUserName());
                } else {
                    modelAddress.setValue(null);
                    Log.d("LocalDetailRepository", "No data found for " + locationType);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("LocalDetailRepository", "Fail to load because " + error.getMessage());
            }
        });
        return modelAddress;
    }
    public void deleteLocal(String locationType){
        String uid = firebaseAuth.getUid();
        DatabaseReference ref = firebaseDatabase.getReference("Location").child(uid).child(locationType);
        ref.removeValue().addOnSuccessListener(aVoid ->{
            Log.d("LocalDetailRepository", "Deleted local");
        }).addOnFailureListener(e->{
            Log.e("LocalDetailRepository", "Delete local fail due to "+e.getMessage());
            e.printStackTrace();
        });
    }
}





