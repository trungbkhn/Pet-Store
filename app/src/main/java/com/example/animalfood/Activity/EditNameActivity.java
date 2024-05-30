package com.example.animalfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.animalfood.R;
import com.example.animalfood.ViewModel.EditNameViewModel;
import com.example.animalfood.databinding.ActivityEditNameBinding;

public class EditNameActivity extends AppCompatActivity {
    private ActivityEditNameBinding binding;
    private EditNameViewModel editNameViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditNameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        editNameViewModel = new ViewModelProvider(this).get(EditNameViewModel.class);
        binding.btnBackEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.tvSaveEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.edtEditName.getText().toString();
                if (name.isEmpty() || name == null){
                    Toast.makeText(EditNameActivity.this,"Name must be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    editNameViewModel.updateName(name);
                    Toast.makeText(EditNameActivity.this,"Update name successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}