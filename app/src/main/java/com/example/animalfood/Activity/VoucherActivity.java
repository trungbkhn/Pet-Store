package com.example.animalfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.example.animalfood.Fragment.ExpiredVoucherFragment;
import com.example.animalfood.Fragment.ValidVoucherFragment;
import com.example.animalfood.R;
import com.example.animalfood.ViewModel.VoucherViewModel;
import com.example.animalfood.databinding.ActivityVoucherBinding;

public class VoucherActivity extends AppCompatActivity {
    private ActivityVoucherBinding binding;
    Fragment validVoucherFragment, expiredVoucherFragment;
    private VoucherViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //default setup
        validVoucherFragment = new ValidVoucherFragment();
        expiredVoucherFragment = new ExpiredVoucherFragment();
        viewModel = new ViewModelProvider(this).get(VoucherViewModel.class);
        viewModel.getListVoucher().observe(VoucherActivity.this, listModel -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, validVoucherFragment).commit();
        });

        binding.tvExpired.setBackgroundResource(R.color.white);
        binding.tvValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, validVoucherFragment).commit();
                binding.tvExpired.setBackgroundResource(R.color.white);
                binding.tvValid.setBackgroundResource(R.drawable.custom_underline_green);
            }
        });
        binding.tvExpired.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, expiredVoucherFragment).commit();
                binding.tvValid.setBackgroundResource(R.color.white);
                binding.tvExpired.setBackgroundResource(R.drawable.custom_underline_green);
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}