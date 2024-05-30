package com.example.animalfood.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.animalfood.Adapter.FavoriteProductAdapter;
import com.example.animalfood.Model.Product;
import com.example.animalfood.R;
import com.example.animalfood.Repository.UserRepository;
import com.example.animalfood.ViewModel.FavoriteProductViewModel;
import com.example.animalfood.databinding.ActivityFavoriteProductBinding;

import java.util.List;

public class FavoriteProductActivity extends AppCompatActivity {
    private RecyclerView rcv_favoriteProduct;
    private FavoriteProductAdapter adapter;
    private FavoriteProductViewModel viewModel;
    private ActivityFavoriteProductBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        UserRepository userRepository = new UserRepository();
        rcv_favoriteProduct = binding.rcvFavoriteProduct;
        rcv_favoriteProduct.setLayoutManager(new LinearLayoutManager(this));

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewModel = new FavoriteProductViewModel(userRepository);
        viewModel.getListProduct().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter = new FavoriteProductAdapter(FavoriteProductActivity.this, products);
                rcv_favoriteProduct.setAdapter(adapter);
            }
        });
    }

}

