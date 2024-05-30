package com.example.animalfood.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.animalfood.Helper.ManagmentCartCategory;
import com.example.animalfood.Interface.ChangeNumberItemsListener;
import com.example.animalfood.Model.PetModel;
import com.example.animalfood.R;
import com.google.gson.Gson;

import java.util.List;

public class PetCartAdapter extends RecyclerView.Adapter<PetCartAdapter.PetCartViewHolder> {
    private Context context;
    private List<PetModel> listPetCart;
    private ManagmentCartCategory managmentCartCategory;
    ChangeNumberItemsListener changeNumberItemsListener;

    public PetCartAdapter(Context context, List<PetModel> listPetCart, ChangeNumberItemsListener changeNumberItemsListener) {
        this.context = context;
        this.listPetCart = listPetCart;
        this.managmentCartCategory = new ManagmentCartCategory(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public PetCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_cartpet, parent, false);
        return new PetCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetCartViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PetModel petModel = listPetCart.get(position);
        Log.d("PetCartAdapter","petModelTitle"+petModel.getName());
        if (petModel != null) {
            if (petModel.getImage() != null) {
                Glide.with(context).load(petModel.getImage())
                        .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(20))).into(holder.img_cartPet);
            }
            holder.tv_title_cartPet.setText(petModel.getName());
            holder.tv_totalEachItemPet.setText(petModel.getPrice()+"đ");
            holder.constraintLayoutPet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listPetCart.remove(listPetCart.get(position));
                    SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("modelPet", new Gson().toJson(listPetCart));
                    myEdit.apply();
                    managmentCartCategory.removeItemById(petModel,petModel.getId());
                    notifyItemRemoved(position);
                    changeNumberItemsListener.onChanged();
                    Log.d("PetCartAdapter","petTitle-ID delete from cart"+ petModel.getName() + petModel.getId());
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return  listPetCart != null ? listPetCart.size() : 0;
    }

    class PetCartViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_cartPet;
        private TextView tv_title_cartPet;
        private TextView tv_totalEachItemPet;
        private ConstraintLayout constraintLayoutPet;

        public PetCartViewHolder(@NonNull View itemView) {
            super(itemView);
            img_cartPet = itemView.findViewById(R.id.img_cartPet);
            tv_title_cartPet = itemView.findViewById(R.id.tv_title_cartPet);
            tv_totalEachItemPet = itemView.findViewById(R.id.tv_totalEachItemPet);
            constraintLayoutPet = itemView.findViewById(R.id.constraintLayoutPet);
        }
    }
}
