package com.example.animalfood.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.animalfood.Adapter.PicListAdapter;
import com.example.animalfood.Adapter.SizeListAdapter;
import com.example.animalfood.Factory.DetailViewModelFactory;
import com.example.animalfood.Helper.ManagmentCart;
import com.example.animalfood.Helper.ManagmentCartCategory;
import com.example.animalfood.Interface.OnSizeSelectedListener;
import com.example.animalfood.Model.BestSellerModel;
import com.example.animalfood.R;
import com.example.animalfood.ViewModel.DetailViewModel;
import com.example.animalfood.databinding.ActivityDetailBinding;

import java.util.ArrayList;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private BestSellerModel item;
    private ManagmentCart managmentCart;
    private ManagmentCartCategory managmentCartCategory;
    private DetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this, new DetailViewModelFactory(getApplication())).get(DetailViewModel.class);
        viewModel.setItem(Objects.requireNonNull(getIntent().getParcelableExtra("object")));
        managmentCart = new ManagmentCart(this);
        managmentCartCategory = new ManagmentCartCategory(this);
        viewModel.checkIsFavoriteProduct(viewModel.getItem().getValue().getProductId());
        viewModel.isFavorite.observe(this, isFav -> {
            if (isFav) {
                binding.btnFav.setImageResource(R.drawable.ic_favorite_full);
            } else {
                binding.btnFav.setImageResource(R.drawable.ic_favorite_border);
            }
        });
        getBundle();
        initSize();
    }

    private void initSize() {
        ArrayList<String> sizeList = new ArrayList<>(viewModel.getItem().getValue().getSize());
        SizeListAdapter adapter = new SizeListAdapter(this, sizeList, viewModel.getItem().getValue());
        binding.rcvListSell.setAdapter(adapter);
        binding.rcvListSell.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<String> colorList = new ArrayList<>(viewModel.getItem().getValue().getPicUrl());
        Glide.with(this).load(colorList.get(0)).into(binding.imgDetail);
        binding.rcvListDetail.setAdapter(new PicListAdapter(this, colorList, binding.imgDetail));
        binding.rcvListDetail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        updateCartItemCount();

        adapter.setOnSizeSelectedListener(new OnSizeSelectedListener() {
            @Override
            public void onSizeSelected(String size) {
                viewModel.setSelectedSize(size);
            }
        });

    }

    private void getBundle() {
        item = viewModel.getItem().getValue();
        binding.tvTitle.setText(item.getTitle());
        binding.tvDescription.setText(item.getDescription());
        binding.tvPrice.setText(item.getPrice() + "đ");
        binding.tvNameSeller.setText(item.getSellerName());
        binding.tvStartCount.setText(String.valueOf(item.getRating()));

        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedSize = viewModel.getSelectedSize().getValue();
                if (selectedSize == null || selectedSize.isEmpty()) {
                    Toast.makeText(DetailActivity.this, "Vui lòng chọn kích cỡ trước khi thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                } else {
                    item.setSelectedSize(selectedSize);
                    managmentCart.insertItem(item, selectedSize);
                    updateCartItemCount();
                    Toast.makeText(DetailActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }
        });


        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnGotoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailActivity.this, CartActivity.class));
            }
        });

        Glide.with(this).load(item.getSellerPic()).apply(new RequestOptions().transform(new CenterCrop(), new CircleCrop())).into(binding.imgSeller);

        binding.imgMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:" + item.getSellerTell()));
                intent.putExtra("sms body", "type your message");
                startActivity(intent);
            }
        });

        binding.imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = item.getSellerTell();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });

        binding.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.checkIsFavoriteProduct(viewModel.getItem().getValue().getProductId());
                Boolean isFav = viewModel.isFavorite.getValue();
                Log.d("DetailActivity", "Fav is " + isFav.toString());

                if (!isFav) {
                    viewModel.insertProduct(item.getProductId());
                    Toast.makeText(DetailActivity.this, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.removeProduct(item.getProductId());
                    Toast.makeText(DetailActivity.this, "Đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                }
                
                binding.btnFav.setImageResource(isFav ? R.drawable.ic_favorite_full : R.drawable.ic_favorite_border);
            }
        });
    }

    private void updateCartItemCount() {
        int cartItemCount = managmentCart.getAllItems().size();
        int cartPetItemCount = managmentCartCategory.getCartSize();
        Log.d("DetailActivity", "So item:" + (cartItemCount + cartPetItemCount));
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
}
