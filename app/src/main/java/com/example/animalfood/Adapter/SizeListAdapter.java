package com.example.animalfood.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.animalfood.Helper.ManagmentCart;
import com.example.animalfood.Interface.OnSizeSelectedListener;
import com.example.animalfood.Model.BestSellerModel;
import com.example.animalfood.R;

import java.util.ArrayList;

public class SizeListAdapter extends RecyclerView.Adapter<SizeListAdapter.ListSizeViewHolder> {
    private Context context;
    private ArrayList<String> listItems;
    private ManagmentCart managmentCart;
    private BestSellerModel item;
    private OnSizeSelectedListener onSizeSelectedListener;

    private int selectedPosition = -1;
    private int lastSelectedPosition = -1;

    public SizeListAdapter(Context context, ArrayList<String> listItems, BestSellerModel item) {
        this.context = context;
        this.listItems = listItems;
        this.managmentCart = new ManagmentCart(context);
        this.item = item;
    }

    @NonNull
    @Override
    public ListSizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_size,parent,false);
        return new ListSizeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSizeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.sizeText.setText(listItems.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition = selectedPosition;
                selectedPosition = position;
                String selectedSize = listItems.get(position);
                if (onSizeSelectedListener != null) {
                    onSizeSelectedListener.onSizeSelected(selectedSize);
                }
                notifyItemChanged(lastSelectedPosition);
                notifyItemChanged(selectedPosition);
            }
        });
        if (selectedPosition == position){
            holder.size_layout.setBackgroundResource(R.drawable.custom_green_bg2);
            holder.sizeText.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.size_layout.setBackgroundResource(R.drawable.custom_gray_bg);
            holder.sizeText.setTextColor(context.getResources().getColor(R.color.black));
        }

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class ListSizeViewHolder extends RecyclerView.ViewHolder{
        public TextView sizeText;
        public LinearLayout size_layout;

        public ListSizeViewHolder(@NonNull View itemView) {
            super(itemView);
            sizeText = itemView.findViewById(R.id.tv_size);
            size_layout = itemView.findViewById(R.id.size_layout);
        }
    }

    public void setOnSizeSelectedListener(OnSizeSelectedListener listener) {
        this.onSizeSelectedListener = listener;
    }
}
