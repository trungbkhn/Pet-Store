package com.example.animalfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.animalfood.R;
import com.example.animalfood.ViewModel.EditBioViewModel;
import com.example.animalfood.ViewModel.EditPhoneNumberViewModel;
import com.example.animalfood.databinding.ActivityEditNumberPhoneBinding;

public class EditNumberPhoneActivity extends AppCompatActivity {
    private ActivityEditNumberPhoneBinding binding;
    private EditPhoneNumberViewModel editPhoneNumberViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditNumberPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        editPhoneNumberViewModel = new ViewModelProvider(this).get(EditPhoneNumberViewModel.class);
        String intentPhoneNumber = getIntent().getStringExtra("PhoneNumber");
        if (intentPhoneNumber != null) {
            binding.edtEditPhoneNumber.setText(intentPhoneNumber);
        }

        binding.btnBackEditPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.tvSavePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = binding.edtEditPhoneNumber.getText().toString();
                editPhoneNumberViewModel.updatePhoneNumber(phoneNumber);
                Toast.makeText(EditNumberPhoneActivity.this, "Update bio successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}