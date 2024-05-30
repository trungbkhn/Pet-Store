package com.example.animalfood.Repository;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.animalfood.Interface.LoginCallback;
import com.example.animalfood.Interface.RegistrationCallback;
import com.example.animalfood.Interface.UploadImageCallback;
import com.example.animalfood.Model.BestSellerModel;
import com.example.animalfood.Model.Product;
import com.example.animalfood.Model.UserModel;
import com.example.animalfood.ViewModel.ProfileViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserRepository {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

    public void checkUserLogin(String email, String password, LoginCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
            callback.onSuccess();
            Log.d("LoginActivity", "Login succeed");
        }).addOnFailureListener(e -> {
            callback.onFailure(e.getMessage());
        });
    }

    public void createAccount(String email, String password, String name, RegistrationCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    updateUserInfor(name, email, callback);
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e.getMessage());
                });
    }

    private void updateUserInfor(String name, String email, RegistrationCallback callback) {
        String uid = firebaseAuth.getUid();
        long timeStamp = System.currentTimeMillis();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("name", name);
        hashMap.put("email", email);
        hashMap.put("profileImage", "");
        hashMap.put("userType", "user");
        hashMap.put("favoriteProduct", "");
        hashMap.put("timeStamp", timeStamp);


        firebaseDatabase.getReference("Users").child(uid)
                .setValue(hashMap)
                .addOnSuccessListener(aVoid -> {
                    callback.onSuccess();
                    Log.d("RegisterActivity", "Create account succeed");
                })
                .addOnFailureListener(e -> {
                    callback.onFailure("Failed saving user info: " + e.getMessage());
                    Log.d("RegisterActivity", "Failed saving user info:" + e.getMessage());
                });
    }

    public LiveData<UserModel> loadInforUser() {
        String uid = firebaseAuth.getCurrentUser().getUid();
        MutableLiveData<UserModel> userMutableLiveData = new MutableLiveData<>();
        Log.d("UserRepository", "uid la" + uid.toString());
        firebaseDatabase.getReference("Users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserModel model = dataSnapshot.getValue(UserModel.class);
                        if (model != null) {
                            userMutableLiveData.setValue(model);
                            Log.d("UserRepository", "Succeed");
                        } else {
                            userMutableLiveData.setValue(null);
                            Log.d("UserRepository", "Khong dung dinh dang");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("UserRepository", "Database error: " + databaseError.getMessage());
                        userMutableLiveData.setValue(null);
                    }
                });
        return userMutableLiveData;
    }


    public void saveUserInformation(String name, String email, String profileImage) {
        String uid = firebaseAuth.getUid();
        long timeStamp = System.currentTimeMillis();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("name", name);
        hashMap.put("email", email);
        hashMap.put("profileImage", profileImage);
        hashMap.put("favoriteProduct", "");
        hashMap.put("userType", "user");
        hashMap.put("timeStamp", timeStamp);

        firebaseDatabase.getReference("Users").child(uid)
                .setValue(hashMap)
                .addOnSuccessListener(aVoid -> {
                    Log.d("UserRepository", "User information saved successfully");
                })
                .addOnFailureListener(e -> {
                    Log.d("UserRepository", "Failed saving user info:" + e.getMessage());
                });
    }

    public void updateName(String name) {
        String uid = firebaseAuth.getUid();
        firebaseDatabase.getReference("Users").child(uid).child("name").setValue(name)
                .addOnSuccessListener(aVoid -> {
                    Log.d("UserRepository","Update name successfully");
                }).addOnFailureListener(e -> {
                    Log.e("UserRepository","Update name error "+ e.getMessage());
                });
    }
    public void updateBio(String bio) {
        String uid = firebaseAuth.getUid();
        firebaseDatabase.getReference("Users").child(uid).child("bio").setValue(bio)
                .addOnSuccessListener(aVoid -> {
                    Log.d("UserRepository","Update bio successfully");
                }).addOnFailureListener(e -> {
                    Log.e("UserRepository","Update bio error "+ e.getMessage());
                });
    }

    public void updateGender(String gender) {
        String uid = firebaseAuth.getUid();
        firebaseDatabase.getReference("Users").child(uid).child("gender").setValue(gender)
                .addOnSuccessListener(aVoid -> {
                    Log.d("UserRepository","Update gender successfully");
                }).addOnFailureListener(e -> {
                    Log.e("UserRepository","Update gender error "+ e.getMessage());
                });
    }

    public void updatePhoneNumber(String phoneNumber) {
        String uid = firebaseAuth.getUid();
        firebaseDatabase.getReference("Users").child(uid).child("phoneNumber").setValue(phoneNumber)
                .addOnSuccessListener(aVoid -> {
                    Log.d("UserRepository","Update phoneNumber successfully");
                }).addOnFailureListener(e -> {
                    Log.e("UserRepository","Update phoneNumber error "+ e.getMessage());
                });
    }

    public void updateEmail(String email) {
        String uid = firebaseAuth.getUid();
        firebaseDatabase.getReference("Users").child(uid).child("email").setValue(email)
                .addOnSuccessListener(aVoid -> {
                    Log.d("UserRepository","Update email successfully");
                }).addOnFailureListener(e -> {
                    Log.e("UserRepository","Update email error "+ e.getMessage());
                });
    }
    public void uploadProfileImage(Uri imageUri, UploadImageCallback callback) {
        String uid = firebaseAuth.getUid();
        StorageReference storageRef = firebaseStorage.getReference().child("profile_images/" + uid + ".jpg");
        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    firebaseDatabase.getReference("Users").child(uid).child("profileImage").setValue(imageUrl)
                            .addOnSuccessListener(aVoid -> {
                                callback.onSuccess(imageUrl);
                                Log.d("UserRepository", "Profile image URL saved successfully");
                            })
                            .addOnFailureListener(e -> {
                                callback.onFailure(e.getMessage());
                                Log.e("UserRepository", "Failed to save profile image URL: " + e.getMessage());
                            });
                }))
                .addOnFailureListener(e -> {
                    callback.onFailure(e.getMessage());
                    Log.e("UserRepository", "Failed to upload profile image: " + e.getMessage());
                });
    }
    private LiveData<String> getName(){
        MutableLiveData<String> liveDataName = new MutableLiveData<>();
        String uid = firebaseAuth.getUid();
        firebaseDatabase.getReference("Users").child(uid).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                liveDataName.postValue(name);
                Log.d("UserRepository","get Name: "+ name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                liveDataName.postValue(null);
                Log.e("UserRepository","Fail to getName: "+error.getMessage());
            }
        });
        return liveDataName;
    }

    public LiveData<List<Product>> getFavoriteProduct() {
        String uid = firebaseAuth.getUid();
        MutableLiveData<List<Product>> list = new MutableLiveData<>();
        firebaseDatabase.getReference("Users").child(uid).child("favoriteProduct").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Product> productList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Product product = dataSnapshot.getValue(Product.class);
                    if (product != null) {
                        productList.add(product);
                    }
                }
                list.setValue(productList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return list;
    }


    public void insertFavoriteProduct(int productId) {
        String uid = firebaseAuth.getUid();
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Items");
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    if (productId == data.child("productId").getValue(Integer.class)) {
                        String title = data.child("title").getValue(String.class);
                        double rating = data.child("rating").getValue(Double.class);
                        double price = data.child("price").getValue(Double.class);
                        ArrayList<String> picUrl = (ArrayList<String>) data.child("picUrl").getValue();
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("productId", productId);
                        hashMap.put("title", title);
                        hashMap.put("rating", rating);
                        hashMap.put("price", price);
                        hashMap.put("picUrl", picUrl);

                        firebaseDatabase.getReference("Users").child(uid).child("favoriteProduct").child(String.valueOf(productId))
                                .setValue(hashMap).addOnSuccessListener(success -> {
                                    Log.d("UserRepository", "Update Favorite Product succeed");
                                }).addOnFailureListener(e -> {
                                    Log.e("UserRepository", "Failed update Favorite Product: " + e.getMessage());
                                });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("UserRepository", "Failed to load BestSeller data from database: " + error.getMessage());
            }
        });
    }

    public void removeFavoriteProduct(int productId) {
        String uid = firebaseAuth.getUid();
        firebaseDatabase.getReference("Users").child(uid).child("favoriteProduct").child(String.valueOf(productId))
                .removeValue().addOnSuccessListener(success -> {
                    Log.d("UserRepository", "Remove Favorite Product succeed");
                }).addOnFailureListener(e -> {
                    Log.e("UserRepository", "Failed Remove Favorite Product: " + e.getMessage());
                });
    }

    public LiveData<Boolean> isFavoriteProduct(int productId) {
        String uid = firebaseAuth.getUid();
        MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();
        firebaseDatabase.getReference("Users").child(uid).child("favoriteProduct").child(String.valueOf(productId))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            isFavorite.setValue(true);
                            Log.d("UserRepository", "Product is in favorite list");
                        } else {
                            isFavorite.setValue(false);
                            Log.d("UserRepository", "Product is not in favorite list");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("UserRepository", "Failed to check favorite product: " + error.getMessage());
                    }
                });
        return isFavorite;
    }

}



