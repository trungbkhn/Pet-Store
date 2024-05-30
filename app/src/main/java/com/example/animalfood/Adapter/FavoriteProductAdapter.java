package com.example.animalfood.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.animalfood.Model.Product;
import com.example.animalfood.R;


import java.util.List;

public class FavoriteProductAdapter extends RecyclerView.Adapter<FavoriteProductAdapter.ViewHolder> {
    private List<Product> productList;
    private Context context;

    public FavoriteProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_favorite_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tv_title_fav.setText(String.valueOf(product.getTitle()));
        Log.d("FavoriteProductAdapter","ProductId"+ product.getTitle());
        holder.tv_feeRachFav.setText(product.getPrice().toString());
        holder.tv_descriptionFav.setText(product.getRating().toString());
        // Load image from URL into ImageView using Picasso or Glide here
        Glide.with(context).load(product.getPicUrl().get(0)).placeholder(R.drawable.ic_person).apply(new RequestOptions().transform(new CenterCrop())).into(holder.img_fav);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title_fav, tv_feeRachFav, tv_descriptionFav;
        ImageView img_fav;
        ImageButton btn_fav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title_fav = itemView.findViewById(R.id.tv_title_fav);
            tv_feeRachFav = itemView.findViewById(R.id.tv_feeRachFav);
            tv_descriptionFav = itemView.findViewById(R.id.tv_descriptionFav);
            img_fav = itemView.findViewById(R.id.img_fav);
            btn_fav = itemView.findViewById(R.id.btn_fav);
        }
    }
}

