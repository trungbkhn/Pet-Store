package com.example.animalfood.Activity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.example.animalfood.Interface.FacebookLoginHandler;
import com.example.animalfood.R;
import com.example.animalfood.ViewModel.LoginViewModel;
import com.example.animalfood.databinding.ActivityLoginBinding;
import com.facebook.AccessToken;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements FacebookLoginHandler {
    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.btnLogin.setOnClickListener(v -> validateData());

        binding.tvGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        binding.btnFaceBook.setOnClickListener(v -> loginWithFacebook());
        LoginManager.getInstance().registerCallback(viewModel.getCallbackManager(),
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        viewModel.handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Đăng nhập bị hủy", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(@NonNull FacebookException e) {
                        Log.d("LoginActivity", e.getMessage());
                        Toast.makeText(LoginActivity.this, "Đăng nhập bị lỗi:" + e.getMessage(), Toast.LENGTH_LONG).show();
                        if (e instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                            }
                        }
                    }
                });

        binding.btnGoogle.setOnClickListener(v -> loginWithGoogle());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        observeGoogleAccount();

        viewModel.getLoginState().observe(this, state ->{
            switch (state) {
                case SUCCESS:
                    progressDialog.dismiss();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    Log.d("LoginActivity","Dang nhap thanh cong");
                    break;
                case FAILURE:
                    progressDialog.dismiss();
                    String errorMessage = viewModel.getErrorMessage().getValue();
                    if (errorMessage != null && !errorMessage.isEmpty()) {
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case LOADING:
                    progressDialog.setMessage("Please waiting ...");
                    progressDialog.show();
                    break;
            }
        });
        viewModel.getIsLoginSuccessful().observe(this, isSuccessful -> {
            if (isSuccessful != null && isSuccessful) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Đăng nhâp thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        ;

    }

    private void validateData() {
        String email = binding.edtUserName.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid Email Format!", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Enter your password! ", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.checkUserLogin(email, password);
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        viewModel.getCallbackManager().onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            viewModel.handleGoogleSignInResult(task);
        }
    }

    @Override
    public void loginWithFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
    }

    @Override
    public void loginWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void observeGoogleAccount() {
        viewModel.getGoogleAccount().observe(this, googleAccount -> {
            if (googleAccount != null) {
                viewModel.firebaseAuthWithGoogle(googleAccount.getIdToken());
            }
        });
    }

}