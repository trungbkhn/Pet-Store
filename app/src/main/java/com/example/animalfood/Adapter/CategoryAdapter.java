package com.example.animalfood.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.animalfood.Activity.PetActivity;
import com.example.animalfood.Model.CategoryModel;
import com.example.animalfood.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private List<CategoryModel> categoryList;

    public CategoryAdapter(Context context, List<CategoryModel> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModel categoryModel = categoryList.get(position);
        holder.setCategory(categoryModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PetActivity.class);
                Log.d("CategoryAdapter","categoryModelTitle "+ categoryModel.getTitle());
                if (categoryModel.getTitle().equals("Cat") ) {
                    intent.putExtra("TitleCategory",categoryModel.getTitle());
                    context.startActivity(intent);
                } else if (categoryModel.getTitle().equals("Dog")) {
                    intent.putExtra("TitleCategory",categoryModel.getTitle());
                    context.startActivity(intent);
                } else if (categoryModel.getTitle().equals("Fish")) {
                    intent.putExtra("TitleCategory",categoryModel.getTitle());
                    context.startActivity(intent);
                } else if (categoryModel.getTitle().equals("Bird")) {
                    intent.putExtra("TitleCategory",categoryModel.getTitle());
                    context.startActivity(intent);
                } else {
                    intent.putExtra("TitleCategory", "None");
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView picCategory;
        private TextView categoryName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            picCategory = itemView.findViewById(R.id.img_picCategory);
            categoryName = itemView.findViewById(R.id.tv_categoryName);
        }

        public void setCategory(CategoryModel categoryModel) {
            if (categoryModel != null && categoryModel.getPicUrl() != null && categoryModel.getTitle() != null) {
                Glide.with(context).load(categoryModel.getPicUrl()).placeholder(R.drawable.ic_person).into(picCategory);
                categoryName.setText(categoryModel.getTitle());
            }
        }
    }
}
