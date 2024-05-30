package com.example.animalfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.animalfood.Adapter.SuggestLocalAdapter;
import com.example.animalfood.R;
import com.example.animalfood.databinding.ActivitySearchLocalBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchLocalActivity extends AppCompatActivity {
    private ActivitySearchLocalBinding binding;
    private SuggestLocalAdapter suggestLocalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchLocalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        suggestLocalAdapter = new SuggestLocalAdapter(new ArrayList<>());
        binding.rcvSearchLocal.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvSearchLocal.setAdapter(suggestLocalAdapter);

        binding.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // updateLocalSuggestions(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


}