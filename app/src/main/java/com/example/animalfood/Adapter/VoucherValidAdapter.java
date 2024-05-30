package com.example.animalfood.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalfood.Model.VoucherModel;
import com.example.animalfood.R;


import java.util.List;

public class VoucherValidAdapter extends RecyclerView.Adapter<VoucherValidAdapter.VoucherValidViewHolder> {
    private Context context;
    private List<VoucherModel> listVoucher;

    public VoucherValidAdapter(Context context, List<VoucherModel> listVoucher) {
        this.context = context;
        this.listVoucher = listVoucher;
    }

    @NonNull
    @Override
    public VoucherValidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_valid_voucher,parent,false);
        return new VoucherValidViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherValidViewHolder holder, int position) {
        VoucherModel model = listVoucher.get(position);
        Log.d("ValidAdapter","model title" + model.getTitle());
        holder.tv_titleVoucher.setText(model.getTitle());
        holder.tv_limitedVocher.setText(model.getDescription());
        holder.tv_descriptionVoucher.setText("Expiry: "+model.getExpiry());
    }

    @Override
    public int getItemCount() {
        return listVoucher.size();
    }

    public class VoucherValidViewHolder extends RecyclerView.ViewHolder {

        TextView tv_titleVoucher = itemView.findViewById(R.id.tv_titleValidVoucher);
        TextView tv_limitedVocher = itemView.findViewById(R.id.tv_limitedValidVoucher);
        TextView tv_descriptionVoucher = itemView.findViewById(R.id.tv_descriptionValidVoucher);

        public VoucherValidViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
