package com.example.animalfood.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.animalfood.BroadcastReceiver.NetworkBroadcastReceiver;
import com.example.animalfood.MyApplication;
import com.example.animalfood.databinding.ActivitySplashBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SplashActivity extends AppCompatActivity implements NetworkBroadcastReceiver.NetworkChangeListener {
    private ActivitySplashBinding binding;
    private FirebaseAuth firebaseAuth;
    private NetworkBroadcastReceiver networkBroadcastReceiver;
    private boolean hasNetworkChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        networkBroadcastReceiver = new NetworkBroadcastReceiver(this);
        registerReceiver(networkBroadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        if (MyApplication.getInstance().isConnected()) {
            handleNetworkConnected();
        } else {
            handleNetworkDisconnected();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkBroadcastReceiver);
    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        if (isConnected) {
            handleNetworkConnected();
        } else {
            handleNetworkDisconnected();
        }
    }

    private void handleNetworkConnected() {
        if (!hasNetworkChecked) {
            hasNetworkChecked = true;
            Toast.makeText(this, "Network connected", Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                clearCartData();
                checkUser();
            }
        }, 2000);
    }

    private void handleNetworkDisconnected() {
        if (!hasNetworkChecked) {
            hasNetworkChecked = true;
            Toast.makeText(this, "Network disconnected", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkUser() {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            FirebaseDatabase.getInstance().getReference("Users")
                    .child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            finish();
                        }
                    });
        }
    }

    private void clearCartData() {
        SharedPreferences sharedPreferences = getSharedPreferences("Cart", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = getSharedPreferences("CartPet", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences2 = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        editor.clear();
        editor1.clear();
        editor2.clear();
        editor.apply();
        editor1.apply();
        editor2.apply();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) return false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        } else {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
    }
}
