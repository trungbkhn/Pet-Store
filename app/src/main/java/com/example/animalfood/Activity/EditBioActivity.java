package com.example.animalfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.animalfood.R;
import com.example.animalfood.ViewModel.EditBioViewModel;
import com.example.animalfood.databinding.ActivityEditBioBinding;

public class EditBioActivity extends AppCompatActivity {
    private ActivityEditBioBinding binding;
    private EditBioViewModel editBioViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        editBioViewModel = new ViewModelProvider(this).get(EditBioViewModel.class);
        String intentBio = getIntent().getStringExtra("Bio");
        if (intentBio != null) {
            binding.edtEditBio.setText(intentBio);
        }

        binding.btnBackEditBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.tvSaveEditBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bio = binding.edtEditBio.getText().toString();
                editBioViewModel.updateBio(bio);
                Toast.makeText(EditBioActivity.this, "Update bio successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
