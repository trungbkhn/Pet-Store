package com.example.animalfood.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.animalfood.Activity.CartActivity;
import com.example.animalfood.Activity.PetActivity;
import com.example.animalfood.Helper.ManagmentCartCategory;
import com.example.animalfood.Interface.CartUpdateListener;
import com.example.animalfood.Model.BirdModel;
import com.example.animalfood.Model.BreedCat;
import com.example.animalfood.Model.BreedDog;
import com.example.animalfood.Model.CatModel;
import com.example.animalfood.Model.DogModel;
import com.example.animalfood.Model.FishModel;
import com.example.animalfood.Model.ImageSrcSet;
import com.example.animalfood.Model.Meta;
import com.example.animalfood.Model.PetModel;
import com.example.animalfood.R;
import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {
    private CartUpdateListener cartUpdateListener;
    private Context context;
    private List list;
    private String title;
    private ArrayList<PetModel> listPet;
    private ManagmentCartCategory managmentCartCategory;


    public PetAdapter(Context context, List list, String title,CartUpdateListener cartUpdateListener) {
        this.context = context;
        this.list = list;
        this.title = title;
        this.cartUpdateListener = cartUpdateListener;
        this.managmentCartCategory = new ManagmentCartCategory(context);
        this.listPet = new ArrayList<>();
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_pet, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Random rand = new Random();
        PetModel petModel = new PetModel();
        int min = 1;
        int max = 30;
        int randomNum = (rand.nextInt((max - min) + 1) + min) * 1000000;
        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedNum = nf.format(randomNum);
        if (title.equals("Cat")) {
            CatModel model = (CatModel) list.get(position);
            Glide.with(context).load(model.getUrl()).transform(new RoundedCorners(20)).into(holder.imageView);
            BreedCat breedCat = model.getBreeds().get(0);
            holder.tv_descriptionPet.setText(breedCat.getDescription());
            holder.tv_title_pet.setText(breedCat.getName());
            holder.tv_feeRachPet.setText(formattedNum + "đ");
            petModel.setId(model.getId());
            petModel.setImage(model.getUrl());
            petModel.setName(breedCat.getName());
            petModel.setPrice(formattedNum);
            Log.d("PetAdapter", "TITLE, DES: " + breedCat.getDescription());
        } else if (title.equals("Dog")) {
            DogModel model = (DogModel) list.get(position);
            Glide.with(context).load(model.getUrl()).transform(new RoundedCorners(20)).into(holder.imageView);
            BreedDog breedDog = model.getBreeds().get(0);
            holder.tv_descriptionPet.setText(breedDog.getTemperament());
            holder.tv_title_pet.setText(breedDog.getName());
            holder.tv_feeRachPet.setText(formattedNum + "đ");
            petModel.setId(model.getId());
            petModel.setImage(model.getUrl());
            petModel.setName(breedDog.getName());
            petModel.setPrice(formattedNum);
        } else if (title.equals("Fish")) {
            FishModel model = (FishModel) list.get(position);
            Meta meta = model.getMeta();
            if (model.hasValidImageSrcSet()) {
                Glide.with(context).load(model.getImageUrl()).placeholder(R.drawable.anhca).transform(new RoundedCorners(20)).into(holder.imageView);
            } else {
                Glide.with(context).load(R.drawable.anhca).transform(new RoundedCorners(20)).into(holder.imageView);
            }
            holder.tv_descriptionPet.setText(meta.getGenera());
            holder.tv_title_pet.setText(model.getName());
            holder.tv_feeRachPet.setText(formattedNum + "đ");
            petModel.setId(String.valueOf(model.getId()));
            if (model.hasValidImageSrcSet()) {
                petModel.setImage(model.getImageUrl());
            } else {
                petModel.setImage("https://bigfishing.vn/wp-content/uploads/2023/05/hinh-anh-ca-chep-dep-nhat_024847932.jpg");
            }
            petModel.setName(model.getName());
            petModel.setPrice(formattedNum);
        } else if (title.equals("Bird")) {
            BirdModel model = (BirdModel) list.get(position);
            Glide.with(context).load(model.getImage()).transform(new RoundedCorners(20)).into(holder.imageView);
            holder.tv_descriptionPet.setText(model.getDescription());
            holder.tv_title_pet.setText(model.getName());
            holder.tv_feeRachPet.setText(formattedNum + "đ");
            petModel.setId(String.valueOf(model.getId()));
            petModel.setImage(model.getImage());
            petModel.setName(model.getName());
            petModel.setPrice(formattedNum);
        }
        holder.btn_pet.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (managmentCartCategory.isAddedToCart(petModel,petModel.getId())){
                    managmentCartCategory.removeItemById(petModel,petModel.getId());
                    Log.d("PetAdapter","petModel title "+petModel.getName());
                    holder.btn_pet.setText("Add to cart"); // Chuyển nút thành "Add to cart"
                    holder.btn_pet.setBackgroundResource(R.drawable.custom_green_stroke1);
                    holder.btn_pet.setTextColor(ContextCompat.getColor(context, R.color.green));
                    holder.btn_pet.setTextSize(10);


                    // Lấy danh sách hiện tại từ MySharedPref và loại bỏ mục đã xóa
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    String json = sharedPreferences.getString("modelPet", "");
                    Type type = new TypeToken<ArrayList<PetModel>>(){}.getType();
                    ArrayList<PetModel> existingList = new Gson().fromJson(json, type);
                    if (existingList != null) {
                        existingList.remove(petModel); // Loại bỏ mục đã xóa từ danh sách
                        Log.d("PetAdapter", "Danh sách cập nhật sau khi xóa: " + new Gson().toJson(existingList));
                        String updatedJson = new Gson().toJson(existingList);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putString("modelPet", updatedJson);
                        myEdit.apply();
                    }
                    cartUpdateListener.onCartUpdated();
                    Toast.makeText(context,"Da xoa khoi gio hang",Toast.LENGTH_SHORT).show();
                } else {
                    managmentCartCategory.insertPetToCart(petModel,petModel.getId());
                    holder.btn_pet.setText("Remove from cart"); // Chuyển nút thành "Remove from cart"
                    holder.btn_pet.setBackgroundResource(R.drawable.custom_green_bg2);
                    holder.btn_pet.setTextColor(ContextCompat.getColor(context, R.color.white));
                    holder.btn_pet.setTextSize(10);
                    Log.d("PetAdapter","petModel title "+petModel.getName());
                    // Lấy danh sách hiện tại từ MySharedPref và thêm mục mới vào danh sách
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    String json = sharedPreferences.getString("modelPet", "");
                    Type type = new TypeToken<ArrayList<PetModel>>(){}.getType();
                    ArrayList<PetModel> existingList = new Gson().fromJson(json, type);
                    if (existingList == null) {
                        existingList = new ArrayList<>();
                    }
                    existingList.add(petModel); // Thêm mục mới vào danh sách
                    Log.d("PetAdapter", "Danh sách cập nhật sau khi thêm: " + new Gson().toJson(existingList));
                    String updatedJson = new Gson().toJson(existingList);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("modelPet", updatedJson);
                    myEdit.apply();
                    cartUpdateListener.onCartUpdated();
                    Toast.makeText(context,"Da them vao gio hang",Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (managmentCartCategory.isAddedToCart(petModel, petModel.getId())) {
            holder.btn_pet.setText("Remove from cart");
            holder.btn_pet.setBackgroundResource(R.drawable.custom_green_bg2);
            holder.btn_pet.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.btn_pet.setTextSize(10);
        } else {
            holder.btn_pet.setText("Add to cart");
            holder.btn_pet.setBackgroundResource(R.drawable.custom_green_stroke1);
            holder.btn_pet.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.btn_pet.setTextSize(10);
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class PetViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tv_title_pet;
        private TextView tv_feeRachPet;
        private TextView tv_descriptionPet;
        private AppCompatButton btn_pet;

        @SuppressLint("ResourceType")
        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_pet);
            tv_title_pet = itemView.findViewById(R.id.tv_title_pet);
            tv_feeRachPet = itemView.findViewById(R.id.tv_feeRachPet);
            tv_descriptionPet = itemView.findViewById(R.id.tv_descriptionPet);
            btn_pet = itemView.findViewById(R.id.btn_pet);
        }
    }
}
