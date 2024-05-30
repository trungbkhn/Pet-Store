package com.example.animalfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.animalfood.R;
import com.example.animalfood.ViewModel.ProfileViewModel;
import com.example.animalfood.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity  {
    private ActivityProfileBinding binding;
    private FirebaseAuth firebaseAuth;
    private ProfileViewModel profileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        initButton();
        initUserInfo();
        initBinding();
    }

    private void initBinding() {
        binding.layoutVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,VoucherActivity.class));
            }
        });

        binding.layoutVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,VoucherActivity.class));
            }
        });
        binding.layoutLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,LocalActivity.class));
            }
        });

    }

    private void initUserInfo() {
        profileViewModel.loadInforUser(this);
        profileViewModel.nameUser.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String name) {
                binding.tvNameUser.setText(name);
            }
        });
        profileViewModel.imgUserUrl.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String imgUrl) {
                if (!imgUrl.isEmpty()) {
                    Glide.with(ProfileActivity.this)
                            .load(imgUrl)
                            .into(binding.imgUser);
                } else {
                    binding.imgUser.setImageResource(R.drawable.profile);
                }
            }
        });

    }

    private void initButton() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firebaseAuth.getCurrentUser() != null){
                    new AlertDialog.Builder(ProfileActivity.this)
                            .setTitle("Log out")
                            .setMessage("Are you sure you want to log out?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    firebaseAuth.signOut();
                                    startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(R.drawable.ic_alert_green)
                            .show();
                }else {
                    startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                }
            }
        });
        binding.layoutPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, PayActivity.class));
            }
        });
        binding.imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,ProfileDetailActivity.class));
            }
        });

        binding.tvNameUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,ProfileDetailActivity.class));
            }
        });
        binding.layoutInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, InviteFriendActivity.class));
            }
        });
    }
}