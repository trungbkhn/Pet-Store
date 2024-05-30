package com.example.animalfood.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.animalfood.R;

import java.util.ArrayList;

public class PicListAdapter extends RecyclerView.Adapter<PicListAdapter.PicListViewHolder> {
    private Context context;
    private ArrayList<String> listItems;
    private ImageView picMain;
    private int selectedPosition = -1;
    private int lastSelectedPosition = -1;

    public PicListAdapter(Context context, ArrayList<String> listItems, ImageView picMain) {
        this.context = context;
        this.listItems = listItems;
        this.picMain = picMain;
    }

    @NonNull
    @Override
    public PicListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_pic_list,parent,false);
        return new PicListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PicListViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(listItems.get(position)).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedPosition = selectedPosition;
                selectedPosition = position;
                notifyItemChanged(lastSelectedPosition);
                notifyItemChanged(selectedPosition);

                Glide.with(context).load(listItems.get(position)).into(picMain);

            }
        });
        if (selectedPosition == position){
            holder.imageView.setBackgroundResource(R.drawable.custom_gray_bg_selected);
        } else {
            holder.imageView.setBackgroundResource(R.drawable.custom_gray_bg);
        }

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class PicListViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public PicListViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_pic_list);
        }

    }
}
