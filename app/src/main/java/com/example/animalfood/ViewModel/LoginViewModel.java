package com.example.animalfood.ViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.animalfood.Interface.LoginCallback;
import com.example.animalfood.Interface.LoginState;
import com.example.animalfood.Interface.RegistrationState;
import com.example.animalfood.Repository.UserRepository;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.concurrent.Executor;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<Boolean> _isLoginSuccessful = new MutableLiveData<>();
    private MutableLiveData<String> _errorMessage = new MutableLiveData<>();
    private MutableLiveData<LoginState> _loginState = new MutableLiveData<>();
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private UserRepository userRepository = new UserRepository();
    //google
    private MutableLiveData<GoogleSignInAccount> _googleAccount = new MutableLiveData<>();


    public LoginViewModel() {
        callbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();
    }

    public LiveData<String> getErrorMessage() {
        return _errorMessage;
    }

    public LiveData<LoginState> getLoginState() {
        return _loginState;
    }



    public void checkUserLogin(String email, String password) {
        _loginState.setValue(LoginState.LOADING);
        userRepository.checkUserLogin(email, password, new LoginCallback() {
            @Override
            public void onSuccess() {
                _loginState.setValue(LoginState.SUCCESS);
                _errorMessage.postValue("Login successful");
            }

            @Override
            public void onFailure(String errorMessage) {
                _loginState.postValue(LoginState.FAILURE);
                LoginViewModel.this._errorMessage.postValue(errorMessage);
            }
        });
    }

    public LiveData<GoogleSignInAccount> getGoogleAccount() {
        return _googleAccount;
    }

    public LiveData<Boolean> getIsLoginSuccessful() {
        return _isLoginSuccessful;
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    public void handleFacebookAccessToken(AccessToken token) {
        if (token != null) {
            AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
            mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    _isLoginSuccessful.setValue(true);
                    Log.d("LoginActivity1", "Succeed login: ");
                    FirebaseUser user = mAuth.getCurrentUser();
                    userRepository.saveUserInformation(user.getDisplayName(), user.getEmail(), user.getPhotoUrl().toString());
                } else {
                    _isLoginSuccessful.setValue(false);
                    Log.d("LoginActivity1", "Error: " + task.getException());
                }
            }).addOnFailureListener(e -> {
                // Log the error here
                Log.d("LoginActivity1", "Error logging in: " + e.getMessage());
                _isLoginSuccessful.setValue(false);
            });
            ;
        }
    }

    //google
    public void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            _googleAccount.setValue(account);
        } catch (ApiException e) {
            Log.w("LoginActivity", "signInResult:failed code=" + e.getStatusCode());
            _isLoginSuccessful.setValue(false);
        }
    }

    public void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        _isLoginSuccessful.setValue(true);
                        FirebaseUser user = mAuth.getCurrentUser();
                        userRepository.saveUserInformation(user.getDisplayName(), user.getEmail(), user.getPhotoUrl().toString());
                    } else {
                        Log.w("LoginActivity", "signInWithCredential:failure", task.getException());
                        _isLoginSuccessful.setValue(false);
                    }
                });
    }


}
