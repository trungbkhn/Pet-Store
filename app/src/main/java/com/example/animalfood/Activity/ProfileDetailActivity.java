package com.example.animalfood.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.animalfood.R;
import com.example.animalfood.ViewModel.ProfileDetailViewModel;
import com.example.animalfood.databinding.ActivityProfileDetailBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ProfileDetailActivity extends AppCompatActivity {
    private ActivityProfileDetailBinding binding;
    private ProfileDetailViewModel profileDetailViewModel;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private static final int REQUEST_STORAGE_PERMISSION = 101;

    private Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        profileDetailViewModel = new ViewModelProvider(this).get(ProfileDetailViewModel.class);
        setupInit();
        initBinding();
    }

    @SuppressLint("ResourceAsColor")
    private void setupInit() {
        profileDetailViewModel.getModel(this);
        profileDetailViewModel.getUserModel().observe(this, userModel -> {
            Log.d("ProfileDetailActivity", "image: " + userModel.getProfileImage());
            if (userModel.getName() != null) {
                binding.tvNameEditProfile.setTextColor(R.color.black);
                binding.tvNameEditProfile.setText(userModel.getName());
            }
            if (userModel.getProfileImage() != null) {
                Glide.with(this)
                        .load(userModel.getProfileImage())
                        .apply(new RequestOptions().circleCrop())
                        .placeholder(R.drawable.avatar)
                        .into(binding.imgProFile);
            }
            if (userModel.getBio() != null) {
                binding.tvBioEditProfile.setTextColor(R.color.black);
                binding.tvBioEditProfile.setText(userModel.getBio());
            }
            if (!userModel.getEmail().isEmpty() && userModel.getEmail() != null) {
                binding.tvEmailEditProfile.setText(userModel.getEmail());
            }
            if (userModel.getGender() != null) {
                binding.tvGenderEditProfile.setText(userModel.getGender());
            }
            if (userModel.getPhoneNumber() != null) {
                binding.tvBioEditProfile.setTextColor(R.color.black);
                binding.tvNumberPhoneEditProfile.setText(userModel.getPhoneNumber().toString());
            }
            profileDetailViewModel.getProfileImageUrl().observe(this, imageUrl -> {
                if (imageUrl != null) {
                    Glide.with(this).load(imageUrl).apply(new RequestOptions().circleCrop()).into(binding.imgProFile);
                }
            });

            profileDetailViewModel.getUploadError().observe(this, errorMessage -> {
                if (errorMessage != null) {
                    Toast.makeText(this, "Failed to upload image: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @SuppressLint("ResourceAsColor")
    private void initBinding() {
        binding.btnBackEditProfile.setOnClickListener(v -> finish());

        binding.imgProFile.setOnClickListener(v -> showImagePickerDialog());

        binding.layoutGender.setOnClickListener(v -> {
            final String[] items = {"Male", "Female", "Another"};
            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileDetailActivity.this);
            builder.setTitle("Gender");
            builder.setItems(items, (dialog, which) -> {
                binding.tvGenderEditProfile.setTextColor(R.color.green);
                binding.tvGenderEditProfile.setText(items[which]);
            });
            builder.show();
        });

        binding.layoutName.setOnClickListener(v -> startActivity(new Intent(ProfileDetailActivity.this, EditNameActivity.class)));

        binding.layoutBio.setOnClickListener(v -> startActivity(new Intent(ProfileDetailActivity.this, EditBioActivity.class)));

        binding.layoutNumberPhone.setOnClickListener(v -> startActivity(new Intent(ProfileDetailActivity.this, EditNumberPhoneActivity.class)));

        binding.btnSaveProfileDetail.setOnClickListener(v -> {
            if (!binding.tvNameEditProfile.getText().equals("Set up now")) {
                profileDetailViewModel.updateName(binding.tvNameEditProfile.getText().toString());
            }
            if (!binding.tvBioEditProfile.getText().equals("Set up now")) {
                profileDetailViewModel.updateBio(binding.tvBioEditProfile.getText().toString());
            }
            if (!binding.tvGenderEditProfile.getText().equals("Set up now")) {
                profileDetailViewModel.updateGender(binding.tvGenderEditProfile.getText().toString());
            }
            if (!binding.tvNumberPhoneEditProfile.getText().equals("Set up now")) {
                profileDetailViewModel.updatePhoneNumber(binding.tvNumberPhoneEditProfile.getText().toString());
            }
            if (!binding.tvEmailEditProfile.getText().equals("Set up now")) {
                profileDetailViewModel.updateEmail(binding.tvEmailEditProfile.getText().toString());
            }
            Toast.makeText(ProfileDetailActivity.this, "Update completed!", Toast.LENGTH_SHORT).show();
            finish();
        });

    }

    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileDetailActivity.this);
        builder.setTitle("Choose an option");
        builder.setItems(new CharSequence[]{"Choose from Gallery", "Take a Photo"}, (dialog, which) -> {
            switch (which) {
                case 0:
                    choosePhotoFromGallery();
                    break;
                case 1:
                    takePhotoFromCamera();
                    break;
            }
        });
        builder.show();
    }

    private void choosePhotoFromGallery() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_PICK_PHOTO);
        }
    }

    private void takePhotoFromCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                try {
                    File photoFile = createImageFile();
                    if (photoFile != null) {
                        photoURI = FileProvider.getUriForFile(this, "com.example.animalfood.fileprovider", photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Glide.with(this)
                        .load(photoURI)
                        .apply(new RequestOptions().circleCrop())
                        .into(binding.imgProFile);

                profileDetailViewModel.uploadProfileImage(photoURI);
            } else if (requestCode == REQUEST_PICK_PHOTO) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    Glide.with(this)
                            .load(selectedImageUri)
                            .apply(new RequestOptions().circleCrop())
                            .into(binding.imgProFile);

                    profileDetailViewModel.uploadProfileImage(selectedImageUri);
                }
            }
        }
    }
}


