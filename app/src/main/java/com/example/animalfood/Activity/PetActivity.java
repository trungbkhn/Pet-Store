package com.example.animalfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.animalfood.Adapter.PetAdapter;
import com.example.animalfood.Factory.PetViewModelFactory;
import com.example.animalfood.Helper.ManagmentCart;
import com.example.animalfood.Helper.ManagmentCartCategory;
import com.example.animalfood.Interface.CartUpdateListener;
import com.example.animalfood.Model.PetModel;
import com.example.animalfood.R;
import com.example.animalfood.Repository.PetRepository;
import com.example.animalfood.ViewModel.FavoriteProductViewModel;
import com.example.animalfood.ViewModel.PetViewModel;
import com.example.animalfood.databinding.ActivityPetBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PetActivity extends AppCompatActivity implements CartUpdateListener {
    private ActivityPetBinding binding;
    private String title = "";
    private PetViewModel viewModel;
    private ManagmentCartCategory managmentCartCategory;
    private ManagmentCart managmentCart;
    private List list = new ArrayList();
    PetRepository repository = new PetRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        managmentCart = new ManagmentCart(this);
        managmentCartCategory = new ManagmentCartCategory(this);
        PetViewModelFactory factory = new PetViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(PetViewModel.class);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnGotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCartItemCount();
                Intent intent = new Intent(PetActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        setRcv();
    }

    private void setRcv() {
        Intent intent = getIntent();
        title = intent.getStringExtra("TitleCategory");
        binding.rcvPet.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        if (title != null) {
            Log.d("PetActivity", "title: " + title);
            if (title.equals("Cat")) {
                viewModel.getListCat().observe(this, listCat -> {
                    list = listCat != null ? listCat : new ArrayList(); // Check for null and initialize if necessary
                    Log.d("PetActivity", "list size: " + list.size());
                    binding.rcvPet.setAdapter(new PetAdapter(PetActivity.this, list, "Cat", PetActivity.this));
                    binding.tvTitlePet.setText("Cat");
                });
            } else if (title.equals("Dog")) {
                viewModel.getListDog().observe(this, listDog -> {
                    list = listDog != null ? listDog : new ArrayList(); // Check for null and initialize if necessary
                    binding.rcvPet.setAdapter(new PetAdapter(PetActivity.this, list, "Dog", PetActivity.this));
                    binding.tvTitlePet.setText("Dog");
                });
            } else if (title.equals("Fish")) {
                viewModel.getListFish().observe(this, listFish -> {
                    list = listFish != null ? listFish : new ArrayList(); // Check for null and initialize if necessary
                    binding.rcvPet.setAdapter(new PetAdapter(PetActivity.this, list, "Fish", PetActivity.this));
                    binding.tvTitlePet.setText("Fish");
                });
            } else if (title.equals("Bird")) {
                viewModel.getListBird().observe(this, listBird -> {
                    list = listBird != null ? listBird : new ArrayList(); // Check for null and initialize if necessary
                    Log.d("PetActivity", "list size: " + list.size());
                    binding.rcvPet.setAdapter(new PetAdapter(PetActivity.this, list, "Bird", PetActivity.this));
                    binding.tvTitlePet.setText("Bird");
                });
            } else if (title.equals("None")) {
                Log.d("PetActivity", "No list");
            }
            Log.d("PetActivity", "list size: " + list.size());
        }
    }

    private void updateCartItemCount() {
        int cartItemCount = managmentCart.getAllItems().size();
        int cartPetItemCount = managmentCartCategory.getCartSize();
        Log.d("DetailActivity", "So item: " + cartItemCount);
        if (cartItemCount > 0 || cartPetItemCount > 0) {
            binding.imgRedCircle.setVisibility(View.VISIBLE);
            binding.tvItemCount.setText(String.valueOf(cartItemCount + cartPetItemCount));
            binding.tvItemCount.setVisibility(View.VISIBLE);
        } else {
            binding.imgRedCircle.setVisibility(View.GONE);
            binding.tvItemCount.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartItemCount();
    }

    private void updateSharedPreferences() {
        List<PetModel> petItemList = managmentCartCategory.getAllItems();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(petItemList);
        editor.putString("modelPet", json);
        editor.apply();
    }

    @Override
    public void onCartUpdated() {
        updateCartItemCount();
        //updateSharedPreferences();
    }
}


