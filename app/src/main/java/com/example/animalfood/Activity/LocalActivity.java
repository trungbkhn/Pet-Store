package com.example.animalfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.example.animalfood.Factory.LocalViewModelFactory;
import com.example.animalfood.Model.CustomLatLng;
import com.example.animalfood.Repository.LocalDetailRepository;
import com.example.animalfood.ViewModel.LocalViewModel;
import com.example.animalfood.databinding.ActivityLocalBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocalActivity extends AppCompatActivity {
    private ActivityLocalBinding binding;
    private LocalViewModel viewModel;
    private LocalDetailRepository localDetailRepository = new LocalDetailRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LocalViewModelFactory viewModelFactory = new LocalViewModelFactory(localDetailRepository);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(LocalViewModel.class);
        initButton();
        loadAddress();
    }

    private void loadAddress() {
        viewModel.getHomeLocal().observe(this, homeLocal -> {
            if (homeLocal != null) {
                binding.linearLayoutLocal2.setVisibility(View.GONE);
                updateAddressFromLatLng(homeLocal.getUserLocation(), binding.tvAddressHome, homeLocal.getBuilding(), homeLocal.getBridge());
                binding.tvNameAndPhoneHome.setText(homeLocal.getUserName() + "   " + homeLocal.getPhoneNumber());
            } else {
                binding.layoutEditAddress.setVisibility(View.GONE);
                binding.linearLayoutLocal2.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getCompanyLocal().observe(this, companyLocal -> {
            if (companyLocal != null) {
                binding.linearLayoutLocal3.setVisibility(View.GONE);
                updateAddressFromLatLng(companyLocal.getUserLocation(), binding.tvAddressCompany, companyLocal.getBuilding(), companyLocal.getBridge());
                binding.tvNameAndPhoneCompany.setText(companyLocal.getUserName() + "   " + companyLocal.getPhoneNumber());
            } else {
                binding.layoutEditCompanyAddress.setVisibility(View.GONE);
                binding.linearLayoutLocal3.setVisibility(View.VISIBLE);
            }
        });
    }

    private void updateAddressFromLatLng(CustomLatLng customLatLng, TextView addressTextView, String building, String bridge) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(customLatLng.getLatitude(), customLatLng.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                String address = addresses.get(0).getAddressLine(0);
                addressTextView.setText("[" + building + ", " + bridge + "] " + address);
            } else {
                addressTextView.setText("Unable to find address");
            }
        } catch (IOException e) {
            e.printStackTrace();
            addressTextView.setText("Geocoder error: " + e.getMessage());
        }
    }

    private void initButton() {
        binding.btnBackLocal.setOnClickListener(v -> finish());
        Intent intent = new Intent(LocalActivity.this, LocalDetailActivity.class);
        Intent intentEdit = new Intent(LocalActivity.this, EditLocalActivity.class);

        binding.linearLayoutLocal2.setOnClickListener(v -> {
            intent.putExtra("localType", "Home");
            startActivity(intent);
        });

        binding.linearLayoutLocal3.setOnClickListener(v -> {
            intent.putExtra("localType", "Company");
            startActivity(intent);
        });
        binding.linearLayoutEditHomeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentEdit.putExtra("typeLocal", "Home");
                startActivity(intentEdit);
            }
        });
        binding.layoutEditCompanyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentEdit.putExtra("typeLocal", "Company");
                startActivity(intentEdit);
            }
        });
    }
}
