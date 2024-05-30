package com.example.animalfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.animalfood.Model.CustomLatLng;
import com.example.animalfood.R;
import com.example.animalfood.ViewModel.EditLocalViewModel;
import com.example.animalfood.databinding.ActivityEditLocalBinding;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class EditLocalActivity extends AppCompatActivity {
    private ActivityEditLocalBinding binding;
    private EditLocalViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditLocalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(EditLocalViewModel.class);
        handleIncomingIntent();
        loadAddress();
        binding.linearLayout4.setOnClickListener(v -> startActivityForResult(
                new Intent(EditLocalActivity.this, ChooseLocalActivity.class), 1));
        binding.btnBackLocalEditDetail.setOnClickListener(v -> finish());
        binding.btnUpdateLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
                Toast.makeText(EditLocalActivity.this, "Update your address successfully!", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnDeleteLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String typeLocal = intent.getStringExtra("typeLocal");
                if (typeLocal != null){
                    viewModel.deleteLocal(typeLocal);
                    Toast.makeText(EditLocalActivity.this,"Deleted ypur address!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditLocalActivity.this,"Cannot delete your address", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void validateData() {
        LatLng latLng = getIntent().getParcelableExtra("selected_location");
        if (binding.edtNameUserLocalEditDetail.getText().toString().isEmpty()) {
            Toast.makeText(EditLocalActivity.this, "Name must not be empty!", Toast.LENGTH_SHORT).show();
            return;
        } else if (binding.edtNumberPhoneLocalEditDetail.getText().toString().isEmpty()) {
            Toast.makeText(EditLocalActivity.this, "Phone number must not be empty!", Toast.LENGTH_SHORT).show();
            return;
        } else if (binding.edtAddEditLocal.getText().toString().isEmpty()) {
            Toast.makeText(EditLocalActivity.this, "Please choose your local!", Toast.LENGTH_SHORT).show();
            return;
        } else if (binding.edtAddEditBuilding.getText().toString().isEmpty()) {
            Toast.makeText(EditLocalActivity.this, "Add your building-floor or number of address!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            String name = binding.edtNameUserLocalEditDetail.getText().toString();
            String edtNumberPhoneLocalDetail = binding.edtNumberPhoneLocalEditDetail.getText().toString();
            String building = binding.edtAddEditBuilding.getText().toString();
            String bridge = binding.edtAddEditBridge.getText().toString();
            String note = binding.edtAddEditNote.getText().toString();
            Intent incomingIntent = getIntent();
            String typeLocal = incomingIntent.getStringExtra("typeLocal");
            Log.d("EditLocalActivity", "typeLocal1: " + typeLocal);
            if (typeLocal!= null){
                if (typeLocal.equals("Home")){
                    if (latLng != null){
                        CustomLatLng customLatLng = CustomLatLng.fromLatLng(latLng);
                        viewModel.addNewHomeLocal(name,edtNumberPhoneLocalDetail,customLatLng,building,bridge,note);
                    } else {
                        viewModel.getHomeLocal().observe(EditLocalActivity.this,homeLocal->{
                            viewModel.addNewHomeLocal(name,edtNumberPhoneLocalDetail,homeLocal.getUserLocation(),building,bridge,note);
                        });
                    }

                }
                if (typeLocal.equals("Company")){
                    if (latLng != null){
                        CustomLatLng customLatLng = CustomLatLng.fromLatLng(latLng);
                        viewModel.addNewHomeLocal(name,edtNumberPhoneLocalDetail,customLatLng,building,bridge,note);
                    } else {
                        viewModel.getHomeLocal().observe(EditLocalActivity.this,homeLocal->{
                            viewModel.addNewCompanyLocal(name,edtNumberPhoneLocalDetail,homeLocal.getUserLocation(),building,bridge,note);
                        });
                    }

                }
            }

        }

    }

    private void loadAddress() {
        Intent incomingIntent = getIntent();
        String typeLocal = incomingIntent.getStringExtra("typeLocal");
        Log.d("EditLocalActivity", "typeLocal: " + typeLocal);
        if (typeLocal != null) {
            if (typeLocal.equals("Home")) {
                viewModel.getHomeLocal().observe(EditLocalActivity.this, homeLocal ->{
                    if (homeLocal != null){
                        binding.edtNameUserLocalEditDetail.setText(homeLocal.getUserName());
                        binding.edtNumberPhoneLocalEditDetail.setText(homeLocal.getPhoneNumber());
                        binding.edtAddEditBuilding.setText(homeLocal.getBuilding());
                        binding.edtAddEditBridge.setText(homeLocal.getBridge());
                        binding.edtAddEditNote.setText(homeLocal.getNote());
                        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(homeLocal.getUserLocation().getLatitude(), homeLocal.getUserLocation().getLongitude(), 1);
                            if (addresses != null && !addresses.isEmpty()) {
                                String address = addresses.get(0).getAddressLine(0);
                                binding.edtAddEditLocal.setText(address);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d("EditLocalActivity", "Name: " + homeLocal.getUserName());
                    }

                });
                LatLng latLng = getIntent().getParcelableExtra("selected_location");
                if (latLng == null && binding.edtAddEditLocal.getText().toString() == null ) {
                    Toast.makeText(EditLocalActivity.this, "Location must not be null!", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
            if (typeLocal.equals("Company")) {
                viewModel.getCompanyLocal().observe(EditLocalActivity.this, companyLocal ->{
                    if (companyLocal != null){
                        binding.edtNameUserLocalEditDetail.setText(companyLocal.getUserName());
                        binding.edtNumberPhoneLocalEditDetail.setText(companyLocal.getPhoneNumber());
                        binding.edtAddEditBuilding.setText(companyLocal.getBuilding());
                        binding.edtAddEditBridge.setText(companyLocal.getBridge());
                        binding.edtAddEditNote.setText(companyLocal.getNote());
                        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(companyLocal.getUserLocation().getLatitude(), companyLocal.getUserLocation().getLongitude(), 1);
                            if (addresses != null && !addresses.isEmpty()) {
                                String address = addresses.get(0).getAddressLine(0);
                                binding.edtAddEditLocal.setText(address);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d("EditLocalActivity", "Name: " + companyLocal.getUserName());
                    }
                });
            }
        }
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
                binding.edtAddEditLocal.setText(address);
            } else {
                binding.edtAddEditLocal.setText("Unable to find address");
            }
        } catch (IOException e) {
            e.printStackTrace();
            binding.edtAddEditLocal.setText("Geocoder error: " + e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("selected_location")) {
                LatLng selectedLocation = data.getParcelableExtra("selected_location");
                if (selectedLocation != null) {
                    updateAddressFromLatLng(selectedLocation);
                    getIntent().putExtra("selected_location", selectedLocation);
                }
            }
        }
    }

}
