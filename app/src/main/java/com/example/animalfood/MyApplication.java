package com.example.animalfood;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.example.animalfood.BroadcastReceiver.NetworkBroadcastReceiver;
import com.facebook.FacebookSdk;
import com.google.firebase.FirebaseApp;

public class MyApplication extends Application implements NetworkBroadcastReceiver.NetworkChangeListener{

    private static MyApplication instance;
    private boolean isConnected = false;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        FacebookSdk.sdkInitialize(getApplicationContext());
        FirebaseApp.initializeApp(this);
        NetworkBroadcastReceiver networkBroadcastReceiver = new NetworkBroadcastReceiver(this);
        registerReceiver(networkBroadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        NetworkBroadcastReceiver networkBroadcastReceiver = new NetworkBroadcastReceiver(this);
        unregisterReceiver(networkBroadcastReceiver);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        this.isConnected = isConnected;
        if (isConnected) {
            Toast.makeText(this, "Network connected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Network disconnected", Toast.LENGTH_SHORT).show();
        }
    }
}
