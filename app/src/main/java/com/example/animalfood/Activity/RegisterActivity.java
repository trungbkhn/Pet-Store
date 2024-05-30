package com.example.animalfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.example.animalfood.R;
import com.example.animalfood.ViewModel.RegisterViewModel;
import com.example.animalfood.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private ProgressDialog progressDialog;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        binding.tvGoToLogin.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));

        binding.btnRegister.setOnClickListener(v -> validateData());

        viewModel.getRegistrationState().observe(this, state -> {
            switch (state) {
                case SUCCESS:
                    progressDialog.dismiss();
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(this, "Tao tai khoan thanh cong", Toast.LENGTH_SHORT).show();
                    Log.d("RegisterActivity","Tao tai khoan thanh cong");
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
    }

    private void validateData() {
        String email = binding.edtEmail.getText().toString().trim();
        String name = binding.edtUserName.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();
        String cpassword = binding.edtRepassword.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please Enter Your Name...", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid Email Format!", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Enter your password! ", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 8) {
            Toast.makeText(this, "Password should be at least 8 characters! ", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(cpassword)) {
            Toast.makeText(this, "Password doesn't match... ", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.createAccount(email, password, name);
        }
    }
}