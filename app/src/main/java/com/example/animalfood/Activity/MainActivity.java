package com.example.animalfood.Activity;


import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.animalfood.Adapter.BestSellerAdapter;
import com.example.animalfood.Adapter.CategoryAdapter;
import com.example.animalfood.Adapter.SliderAdapter;
import com.example.animalfood.Model.BestSellerModel;
import com.example.animalfood.Model.CategoryModel;
import com.example.animalfood.Model.SliderModel;
import com.example.animalfood.R;
import com.example.animalfood.ViewModel.MainViewModel;
import com.example.animalfood.databinding.ActivityMainBinding;
import com.google.android.material.transformation.FabTransformationSheetBehavior;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private ActivityMainBinding binding;
    private Handler handler = new Handler();
    private Runnable shakeRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        initBanner();
        initCategory();
        initBestSeller();
        bottomNavigation();
        initUserName();
        startNotificationAnimation();
    }

    private void startNotificationAnimation() {
        final Animation shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake_animation);
        shakeRunnable = new Runnable() {
            @Override
            public void run() {
                binding.imgNoti.startAnimation(shakeAnimation);
                handler.postDelayed(this, 5000);
            }
        };
        handler.post(shakeRunnable);
    }

    private void bottomNavigation() {
        binding.imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });

        binding.imgProFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
        binding.imgWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FavoriteProductActivity.class));
            }
        });
        binding.imgExplorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/2k.trung/";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });
    }
    private void initUserName() {
        viewModel.nameUser.observe(this, userName -> {
            if (userName != null) {
                binding.tvUserName.setText(userName.toString());
            }
        });
        viewModel.getName();
    }

    private void initBestSeller() {
        binding.progressBestSeller.setVisibility(View.VISIBLE);
        viewModel.bestSellers.observe(this, bestSellerModels -> {
            Log.d("MainActivity", "New bestSellers data received: " + bestSellerModels.size());
            if (bestSellerModels != null && !bestSellerModels.isEmpty()) {
                setUpBestSellers(bestSellerModels);
                binding.progressBestSeller.setVisibility(View.GONE);
            }
        });
        viewModel.loadBestSeller();
    }

    private void setUpBestSellers(List<BestSellerModel> models) {
        BestSellerAdapter bestSellerAdapter = new BestSellerAdapter(this, models);
        binding.recyclerViewBestSeller.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerViewBestSeller.setAdapter(bestSellerAdapter);
    }

    private void initCategory() {
        binding.progressCategory.setVisibility(View.VISIBLE);
        viewModel.categories.observe(this, categoryModels -> {
            Log.d("MainActivity", "New categories data received: " + categoryModels.size());
            if (categoryModels != null && !categoryModels.isEmpty()) {
                setUpCategories(categoryModels);
                binding.progressCategory.setVisibility(View.GONE);
            }
        });
        viewModel.loadCategory();
    }

    private void setUpCategories(List<CategoryModel> categoryModels) {
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categoryModels);
        binding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewCategory.setAdapter(categoryAdapter);
    }

    private void initBanner() {
        binding.progressBarBanner.setVisibility(View.VISIBLE);
        viewModel.banners.observe(this, sliderModels -> {
            Log.d("MainActivity", "New banner data received: " + sliderModels.size());
            if (sliderModels != null && !sliderModels.isEmpty()) {
                setUpBanners(sliderModels);
                binding.progressBarBanner.setVisibility(View.GONE);
            }
        });
        viewModel.loadBanners();
    }

    private void setUpBanners(List<SliderModel> sliderModels) {
        final Handler handler = new Handler();

        Log.d("MainActivity", "Setting up banners with " + sliderModels.size() + " items");
        SliderAdapter sliderAdapter = new SliderAdapter(this, sliderModels, binding.viewPagerBanner);
        binding.viewPagerBanner.setAdapter(sliderAdapter);
        binding.viewPagerBanner.setClipToPadding(false);
        binding.viewPagerBanner.setClipChildren(false);
        binding.viewPagerBanner.setOffscreenPageLimit(3);
        binding.viewPagerBanner.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        // Thiết lập PageTransformer để áp dụng hiệu ứng trượt cho các trang
        int marginPx = getResources().getDimensionPixelSize(R.dimen.viewpager2_page_margin);
        ViewPager2.PageTransformer marginPageTransformer = (page, position) -> {
            page.setPadding(marginPx, 0, marginPx, 0);
        };
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(marginPageTransformer);

        binding.viewPagerBanner.setPageTransformer(compositePageTransformer);
        if (sliderModels.size() > 1) {
            binding.dotIndicator.setVisibility(View.VISIBLE);
            binding.dotIndicator.attachTo(binding.viewPagerBanner);
        }
        final Runnable update = new Runnable() {
            public void run() {
                int currentItem = binding.viewPagerBanner.getCurrentItem();
                int nextItem = (currentItem + 1) % sliderModels.size();
                binding.viewPagerBanner.setCurrentItem(nextItem);
                handler.postDelayed(this, 5000);
            }
        };
        handler.post(update);

    }


}