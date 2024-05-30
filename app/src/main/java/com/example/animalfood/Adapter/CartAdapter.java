package com.example.animalfood.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.animalfood.Helper.ManagmentCart;
import com.example.animalfood.Helper.NumberHelper;
import com.example.animalfood.Interface.ChangeNumberItemsListener;
import com.example.animalfood.Model.BestSellerModel;
import com.example.animalfood.R;
import com.example.animalfood.ViewModel.CartViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<BestSellerModel> listItem;
    ChangeNumberItemsListener changeNumberItemsListener;
    private ManagmentCart managmentCart;

    public CartAdapter(Context context, List<BestSellerModel> listItem, ChangeNumberItemsListener changeNumberItemsListener) {
        this.context = context;
        this.listItem = listItem;
        this.changeNumberItemsListener = changeNumberItemsListener;
        this.managmentCart = new ManagmentCart(context);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_cart, parent, false);
        return new CartViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        BestSellerModel model = listItem.get(position);
        StringBuilder description = new StringBuilder();
        String selectedSize = model.getSelectedSize(); // Lấy selectedSize từ model
        int totalQuantity = managmentCart.getTotalQuantity(model, model.getSelectedSize());
        description.append("Size: ").append(selectedSize);
        holder.tv_title.setText(model.getTitle());
        holder.tv_feeRachItem.setText(NumberHelper.formatPrice(model.getPrice()) +"đ");
        holder.tv_totalEachItem.setText(NumberHelper.formatPrice(Math.round(totalQuantity * model.getPrice()))+"đ");
        holder.tv_cartNumber.setText(String.valueOf(totalQuantity));
        Glide.with(context).load(model.getFirstPicUrl()).apply(new RequestOptions().transform(new CenterCrop())).into(holder.img_cart);

        holder.btn_plusCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managmentCart.increaseQuantity(model, selectedSize);
                notifyDataSetChanged();
                changeNumberItemsListener.onChanged();
            }
        });

        holder.btn_minusCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                managmentCart.decreaseQuantity(model, selectedSize);
                if (managmentCart.getTotalQuantity(model, selectedSize) == 0) {
                    listItem.remove(position);
                    notifyItemRemoved(position);
                } else {
                    notifyItemChanged(position);
                }
                changeNumberItemsListener.onChanged();
            }
        });


        holder.tv_descriptionItem.setText(description.toString());
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_cart = itemView.findViewById(R.id.img_cart);
        private TextView tv_title = itemView.findViewById(R.id.tv_title_cart);
        private TextView tv_feeRachItem = itemView.findViewById(R.id.tv_feeRachItem);
        private TextView tv_totalEachItem = itemView.findViewById(R.id.tv_totalEachItem);
        private TextView btn_minusCart = itemView.findViewById(R.id.btn_minusCart);
        private TextView btn_plusCart = itemView.findViewById(R.id.btn_plusCart);
        private TextView tv_cartNumber = itemView.findViewById(R.id.tv_cartNumber);
        private TextView tv_descriptionItem = itemView.findViewById(R.id.tv_descriptionItem);

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

