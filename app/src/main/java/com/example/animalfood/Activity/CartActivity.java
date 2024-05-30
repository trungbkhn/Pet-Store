package com.example.animalfood.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.animalfood.Adapter.CartAdapter;
import com.example.animalfood.Adapter.PetAdapter;
import com.example.animalfood.Adapter.PetCartAdapter;
import com.example.animalfood.Helper.ManagmentCart;
import com.example.animalfood.Helper.ManagmentCartCategory;
import com.example.animalfood.Helper.NumberHelper;
import com.example.animalfood.Interface.ChangeNumberItemsListener;
import com.example.animalfood.Model.BestSellerModel;
import com.example.animalfood.Model.PetModel;
import com.example.animalfood.R;
import com.example.animalfood.ViewModel.CartViewModel;
import com.example.animalfood.databinding.ActivityCartBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding binding;
    private ManagmentCart managmentCart;
    private ManagmentCartCategory managmentCartCategory;
    private CartViewModel cartViewModel;
    private PetCartAdapter petCartAdapter;
    private List<PetModel> listPet = new ArrayList<>();
    private double petFeeTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        managmentCart = new ManagmentCart(this);
        managmentCartCategory = new ManagmentCartCategory(this);
        loadPetListFromPreferences();
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        petFeeTotal = calculateTotalPetPrice();
        setVariable();
        initCartList();
        calculatorCart();
    }

    private void calculatorCart() {
        petFeeTotal = calculateTotalPetPrice();
        Log.d("CartActivity", "Get petFeeTotal "+ petFeeTotal);
        String strPetFeeTotal = NumberHelper.formatPrice(petFeeTotal) + "đ";
        double percentTax = 0.02;
        double delivery = 15000.0;
        cartViewModel.getTotalAllFee(this,managmentCart);
        cartViewModel.getTotalFee().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double total) {
                double tax = Math.round(((total+petFeeTotal) * percentTax) * 100) / 100.0;
                double totalFee = Math.round(((total+petFeeTotal) + tax + delivery) * 100) / 100;
                double itemTotal = Math.round((total+petFeeTotal) * 100) / 100;
                String strDelivery = NumberHelper.formatPrice(delivery) + "đ";
                String strTax = NumberHelper.formatPrice(tax) + "đ";
                String strTotalFee = NumberHelper.formatPrice(totalFee) + "đ";
                String strItemTotal = NumberHelper.formatPrice(itemTotal) + "đ";
                binding.tvSubTotalPrice.setText(strItemTotal);
                binding.tvTotalTaxPrice.setText(strTax);
                binding.tvDeliveryPrice.setText(strDelivery);
                binding.tvTotalPrice.setText(strTotalFee);
                int cartList = managmentCart.getNumberOfItems();
                int cartPetList = managmentCartCategory.getCartSize();
                if (cartList == 0 && cartPetList == 0) {
                    binding.tvSubTotalPrice.setText("0.0");
                    binding.tvDeliveryPrice.setText("0.0");
                    binding.tvTotalPrice.setText("0.0"+"đ");
                    binding.viewpage1.setVisibility(View.GONE);
                    binding.viewpage2.setVisibility(View.GONE);
                }
            }
        });


    }

    private void initCartList() {
        cartViewModel.setListModel(this);
        Map<String, ?> allItems = managmentCart.getAllItems();
        cartViewModel.getListModel().observe(this, models -> {
            List<BestSellerModel> listCart = new ArrayList<>();
            if (models != null) {
                for (BestSellerModel model : models) {
                    String modelTitle = model.getTitle();
                    for (Map.Entry<String, ?> item : allItems.entrySet()) {
                        String[] itemParts = item.getKey().split("_");
                        String itemTitle = itemParts[0];
                        if (itemTitle.equals(modelTitle)) {
                            String selectedSize = itemParts[1];
                            BestSellerModel newModel = new BestSellerModel(model);
                            newModel.setSelectedSize(selectedSize);
                            listCart.add(newModel);
                            Log.d("CartActivity","Model size load from viewmodel "+newModel.getSelectedSize());
                        }
                    }
                }
            }

            if (!listCart.isEmpty()) {
                binding.rcvCartList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                Log.d("CartActivity", "CartList size is " + listCart.size());
                binding.rcvCartList.setAdapter(new CartAdapter(this, listCart, new ChangeNumberItemsListener() {
                    @Override
                    public void onChanged() {
                        calculatorCart();
                    }
                }));
            } else {
                binding.viewpage1.setVisibility(View.GONE);
                Log.d("CartActivity", "CartList size is " + listCart.size());
            }

            if (!listPet.isEmpty()){
                if (petCartAdapter == null) {
                    petCartAdapter = new PetCartAdapter(this, listPet, new ChangeNumberItemsListener() {
                        @Override
                        public void onChanged() {
                            calculatorCart();
                        }
                    });
                    binding.rcvCartListPet.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                    binding.rcvCartListPet.setAdapter(petCartAdapter);
                }
                petCartAdapter.notifyDataSetChanged();
                Log.d("CartActivity", "CartListPet size is " + listPet.size());
            } else {
                binding.viewpage1.setVisibility(View.GONE);
                Log.d("CartActivity", "CartListPet size is " + listCart.size());
            }
        });
    }


    private double calculateTotalPetPrice() {
        double totalPrice = 0.0;
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String json = sharedPreferences.getString("modelPet","");
        Type type = new TypeToken<ArrayList<PetModel>>(){}.getType();
        List<PetModel> listPet = new Gson().fromJson(json, type);

        if (listPet == null) {
            listPet = new ArrayList<>();
        }

        for (PetModel pet : listPet) {
            String priceString = pet.getPrice();
            if (priceString != null) {
                priceString = priceString.replace(".", "");
                double price = Double.parseDouble(priceString);
                totalPrice += price;
                Log.d("CartActivity", "Price of pet " + price);
            }
        }

        return totalPrice;
    }


    private void setVariable() {
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void loadPetListFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String json = sharedPreferences.getString("modelPet", "");
        Type type = new TypeToken<ArrayList<PetModel>>() {}.getType();
        listPet = new Gson().fromJson(json, type);
        if (listPet == null) {
            listPet = new ArrayList<>();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadPetListFromPreferences();

        if (petCartAdapter == null) {
            petCartAdapter = new PetCartAdapter(this, listPet, new ChangeNumberItemsListener() {
                @Override
                public void onChanged() {
                    calculatorCart();
                }
            });
            binding.rcvCartListPet.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            binding.rcvCartListPet.setAdapter(petCartAdapter);
        } else {
            petCartAdapter.notifyDataSetChanged();
        }
        calculatorCart();
    }
}

