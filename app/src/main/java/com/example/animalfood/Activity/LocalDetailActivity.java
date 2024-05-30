package com.example.animalfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.animalfood.Model.CustomLatLng;
import com.example.animalfood.ViewModel.LocalDetailViewModel;
import com.example.animalfood.databinding.ActivityLocalDetailBinding;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocalDetailActivity extends AppCompatActivity {
    private ActivityLocalDetailBinding binding;
    private FirebaseAuth firebaseAuth;
    private LocalDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocalDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        viewModel = new ViewModelProvider(this).get(LocalDetailViewModel.class);

        handleIncomingIntent();

        binding.btnBackLocalDetail.setOnClickListener(v -> finish());

        binding.linearLayout4.setOnClickListener(v -> startActivityForResult(
                new Intent(LocalDetailActivity.this, ChooseLocalActivity.class), 1));

        binding.btnAddNewAddress.setOnClickListener(v -> validateData());
    }

    private void handleIncomingIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selected_location")) {
            LatLng selectedLocation = intent.getParcelableExtra("selected_location");
            if (selectedLocation != null) {
                updateAddressFromLatLng(selectedLocation);
            }
        }
    }

    private void updateAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                String address = addresses.get(0).getAddressLine(0);
                binding.edtAddLocal.setText(address);
            } else {
                binding.edtAddLocal.setText("Unable to find address");
            }
        } catch (IOException e) {
            e.printStackTrace();
            binding.edtAddLocal.setText("Geocoder error: " + e.getMessage());
        }
    }

    private void validateData() {
        if (binding.edtNameUserLocalDetail.getText().toString().isEmpty()) {
            Toast.makeText(LocalDetailActivity.this, "Name must not be empty!", Toast.LENGTH_SHORT).show();
            return;
        } else if (binding.edtNumberPhoneLocalDetail.getText().toString().isEmpty()) {
            Toast.makeText(LocalDetailActivity.this, "Phone number must not be empty!", Toast.LENGTH_SHORT).show();
            return;
        } else if (binding.edtAddLocal.getText().toString().isEmpty()) {
            Toast.makeText(LocalDetailActivity.this, "Please choose your local!", Toast.LENGTH_SHORT).show();
            return;
        } else if (binding.edtAddBuilding.getText().toString().isEmpty()) {
            Toast.makeText(LocalDetailActivity.this, "Add your building-floor or number of address!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            String name = binding.edtNameUserLocalDetail.getText().toString();
            String edtNumberPhoneLocalDetail = binding.edtNumberPhoneLocalDetail.getText().toString();
            String userLocation = binding.edtAddLocal.getText().toString();
            String building = binding.edtAddBuilding.getText().toString();
            String bridge = binding.edtAddBridge.getText().toString();
            String note = binding.edtAddNote.getText().toString();
            LatLng latLng = getIntent().getParcelableExtra("selected_location");

            if (latLng == null) {
                Toast.makeText(LocalDetailActivity.this, "Location must not be null!", Toast.LENGTH_SHORT).show();
                return;
            }
            CustomLatLng customLatLng = CustomLatLng.fromLatLng(latLng);

            Intent incomingIntent = getIntent();
            String typeLocal = incomingIntent.getStringExtra("localType");
            Log.d("LocalDetailActivity", "typeLocal: "+typeLocal);
            if (typeLocal != null){
                if (typeLocal.equals("Home")){
                    addUserHomeLocal(name, edtNumberPhoneLocalDetail, customLatLng, building, bridge, note);
                }
                if (typeLocal.equals("Company")) {
                    addUserCompanyLocal(name, edtNumberPhoneLocalDetail, customLatLng, building, bridge, note);
                }
            }
        }
    }

    private void addUserHomeLocal(String name, String phoneNumber, CustomLatLng userLocation, String building, String bridge, String note) {
        if (userLocation == null) {
            Log.d("LocalDetailActivity", "User location is null");
            Toast.makeText(LocalDetailActivity.this, "User location is null!", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d("LocalDetailActivity", "Name: " + name);
        Log.d("LocalDetailActivity", "Phone Number: " + phoneNumber);
        Log.d("LocalDetailActivity", "Location: " + userLocation.toLatLng().toString());
        Log.d("LocalDetailActivity", "Building: " + building);
        Log.d("LocalDetailActivity", "Bridge: " + bridge);
        Log.d("LocalDetailActivity", "Note: " + note);

        viewModel.addNewHomeLocal(name, phoneNumber, userLocation, building, bridge, note);
        viewModel.getIsLocalAdded().observe(this, isAdded -> {
            if (Boolean.TRUE.equals(isAdded)) {
                Toast.makeText(LocalDetailActivity.this, "Address added successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addUserCompanyLocal(String name, String phoneNumber, CustomLatLng userLocation, String building, String bridge, String note) {
        if (userLocation == null) {
            Log.d("LocalDetailActivity", "User location is null");
            Toast.makeText(LocalDetailActivity.this, "User location is null!", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d("LocalDetailActivity", "Name: " + name);
        Log.d("LocalDetailActivity", "Phone Number: " + phoneNumber);
        Log.d("LocalDetailActivity", "Location: " + userLocation.toLatLng().toString());
        Log.d("LocalDetailActivity", "Building: " + building);
        Log.d("LocalDetailActivity", "Bridge: " + bridge);
        Log.d("LocalDetailActivity", "Note: " + note);

        viewModel.addNewCompanyLocal(name, phoneNumber, userLocation, building, bridge, note);
        viewModel.getIsLocalAdded().observe(this, isAdded -> {
            if (Boolean.TRUE.equals(isAdded)) {
                Toast.makeText(LocalDetailActivity.this, "Address added successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("selected_location")) {
                LatLng selectedLocation = data.getParcelableExtra("selected_location");
                if (selectedLocation != null) {
                    updateAddressFromLatLng(selectedLocation);
                    getIntent().putExtra("selected_location", selectedLocation);  // Lưu vào Intent để sử dụng sau này
                }
            }
        }
    }
}

