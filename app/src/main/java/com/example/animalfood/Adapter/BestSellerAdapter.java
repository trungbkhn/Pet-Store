package com.example.animalfood.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.animalfood.Activity.DetailActivity;
import com.example.animalfood.Helper.NumberHelper;
import com.example.animalfood.Model.BestSellerModel;
import com.example.animalfood.R;

import java.util.List;

public class BestSellerAdapter extends RecyclerView.Adapter<BestSellerAdapter.BestSellerViewHolder> {
    private Context context;
    private List<BestSellerModel> list;

    public BestSellerAdapter(Context context, List<BestSellerModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BestSellerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_bestseller, parent, false);
        return new BestSellerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BestSellerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        BestSellerModel model = list.get(position);
        holder.setBestSeller(model);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("object", list.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class BestSellerViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_picSeller = itemView.findViewById(R.id.img_picSeller);
        private ImageView btn_like = itemView.findViewById(R.id.btn_like);
        private TextView tv_price = itemView.findViewById(R.id.tv_price);
        private TextView tv_startCount = itemView.findViewById(R.id.tv_startCount);
        private TextView tv_title = itemView.findViewById(R.id.tv_title_bestSeller);

        public BestSellerViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setBestSeller(BestSellerModel bestSellerModel) {
            if (bestSellerModel.getPicUrl() != null && bestSellerModel.getTitle() != null && bestSellerModel.getRating() != null && bestSellerModel.getPrice() != null) {
                Glide.with(context).load(bestSellerModel.getFirstPicUrl()).placeholder(R.drawable.ic_person).centerInside().into(img_picSeller);
                tv_title.setText(bestSellerModel.getTitle());
                tv_startCount.setText(bestSellerModel.getRating().toString());
                tv_price.setText(NumberHelper.formatPrice(bestSellerModel.getPrice())+"đ");
            }
        }
    }
}
