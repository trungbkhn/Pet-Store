package com.example.animalfood.Adapter;

import android.location.Address;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalfood.Model.Place;
import com.example.animalfood.R;

import java.util.List;


public class SuggestLocalAdapter extends RecyclerView.Adapter<SuggestLocalAdapter.ViewHolder> {
    private List<Place> localSuggestions;

    public SuggestLocalAdapter(List<Place> localSuggestions) {
        this.localSuggestions = localSuggestions;
    }

    public void updateData(List<Place> newSuggestions) {
        this.localSuggestions = newSuggestions;
        Log.d("SuggestLocalAdapter", "updateData" + localSuggestions);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_suggest_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = localSuggestions.get(position);
        holder.textView12.setText(place.getName());
        holder.textView13.setText(place.getVicinity());
    }

    @Override
    public int getItemCount() {
        return localSuggestions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView12, textView13;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView12 = itemView.findViewById(R.id.textView12);
            textView13 = itemView.findViewById(R.id.textView13);
        }
    }
}



