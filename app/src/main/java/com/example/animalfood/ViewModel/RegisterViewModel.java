package com.example.animalfood.ViewModel;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.animalfood.Interface.RegistrationCallback;
import com.example.animalfood.Interface.RegistrationState;
import com.example.animalfood.Repository.UserRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import kotlin.Unit;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<RegistrationState> registrationState = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private UserRepository userRepository = new UserRepository();

    RegisterViewModel() {
    }

    public LiveData<RegistrationState> getRegistrationState() {
        return registrationState;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void createAccount(String email, String password, String name) {
        registrationState.setValue(RegistrationState.LOADING);
        userRepository.createAccount(email, password, name, new RegistrationCallback() {
            @Override
            public void onSuccess() {
                registrationState.postValue(RegistrationState.SUCCESS);
                errorMessage.postValue("Registration successful");
            }

            @Override
            public void onFailure(String errorMessage) {
                registrationState.postValue(RegistrationState.FAILURE);
                RegisterViewModel.this.errorMessage.postValue(errorMessage);
            }
        });
    }
}
