package com.example.animalfood.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import android.Manifest;

import com.example.animalfood.Adapter.SuggestLocalAdapter;
import com.example.animalfood.Factory.ChooseLocalViewModelFactory;
import com.example.animalfood.R;
import com.example.animalfood.ViewModel.ChooseLocalViewModel;
import com.example.animalfood.databinding.ActivityChooseLocalBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChooseLocalActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    private ActivityChooseLocalBinding binding;
    private SuggestLocalAdapter suggestLocalAdapter;
    private ChooseLocalViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseLocalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        ChooseLocalViewModelFactory factory = new ChooseLocalViewModelFactory(getApplication());
        viewModel = new ViewModelProvider(this, factory).get(ChooseLocalViewModel.class);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getLastLocation();

        binding.btnBackChooseLocal.setOnClickListener(v -> {
            Location selectedLocation = viewModel.getUserLocation().getValue();
            if (selectedLocation != null) {
                LatLng latLng = new LatLng(selectedLocation.getLatitude(), selectedLocation.getLongitude());
                Intent intent = new Intent();
                intent.putExtra("selected_location", latLng);
                setResult(RESULT_OK, intent);
            }
            finish();
        });
        binding.btnLocal.setOnClickListener(v -> startActivity(new Intent(ChooseLocalActivity.this, SearchLocalActivity.class)));

        binding.btnGotoUserLocal.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(ChooseLocalActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ChooseLocalActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            } else {
                Log.d("ChooseLocalActivity", "Start get last location");
                getLastLocation();
            }
        });
        suggestLocalAdapter = new SuggestLocalAdapter(new ArrayList<>());
        viewModel.getNearbyPlaces().observe(this, places -> {
            if (places != null) {
                suggestLocalAdapter.updateData(places);
            }
        });
        binding.rcvNearLocal.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvNearLocal.setAdapter(suggestLocalAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                Log.d("ChooseLocalActivity", "Last known location found");
                viewModel.setUserLocation(location);
                updateMapWithLocation(location);
            } else {
                Log.d("ChooseLocalActivity", "Last known location not found, requesting location updates");
                requestLocationUpdates();
            }
        });
    }

    private void requestLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(5000);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
            return;
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                fusedLocationClient.removeLocationUpdates(this);
                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        Log.d("ChooseLocalActivity", "Location update received");
                        viewModel.setUserLocation(location);
                        updateMapWithLocation(location);
                    } else {
                        Log.d("ChooseLocalActivity", "Location update not found");
                        Toast.makeText(ChooseLocalActivity.this, "Location not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, getMainLooper());
    }

    private void updateMapWithLocation(Location location) {
        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15)); // Adjust zoom level as needed

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                String address = addresses.get(0).getAddressLine(0);
                Toast.makeText(this, "Your Location: " + address, Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ChooseLocalActivity", "Geocoder failed: " + e.getMessage());
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng hanoi = new LatLng(21.0278, 105.8342);
        googleMap.addMarker(new MarkerOptions().position(hanoi).title("Hà Nội"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hanoi, 12));

        googleMap.setOnMapClickListener(latLng -> {
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(latLng).title("Vị trí của bạn"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

            Geocoder geocoder = new Geocoder(ChooseLocalActivity.this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    String address = addresses.get(0).getAddressLine(0);
                    Toast.makeText(ChooseLocalActivity.this, "Vị trí mới: " + address, Toast.LENGTH_LONG).show();
                    Location newLocation = new Location("");
                    newLocation.setLatitude(latLng.latitude);
                    newLocation.setLongitude(latLng.longitude);
                    viewModel.setUserLocation(newLocation);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}



