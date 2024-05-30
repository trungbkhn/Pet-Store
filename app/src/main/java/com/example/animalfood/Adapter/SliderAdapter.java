package com.example.animalfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.animalfood.Model.SliderModel;
import com.example.animalfood.R;
import com.example.animalfood.RoundedCornersTransformation;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private Context mContext;
    private List<SliderModel> mDataList;
    private ViewPager2 mViewPager2;
    private ViewPager2.OnPageChangeCallback onPageChangeCallback;

    public SliderAdapter(Context context, List<SliderModel> dataList, ViewPager2 viewPager2) {
        this.mContext = context;
        this.mDataList = dataList;
        this.mViewPager2 = viewPager2;
        onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Nếu đến trang cuối cùng, chuyển về trang đầu tiên
            }
        };

        // Đăng ký callback
        mViewPager2.registerOnPageChangeCallback(onPageChangeCallback);
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.viewholder_image_slider, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        SliderModel sliderModel = mDataList.get(position);
        holder.setImage(sliderModel);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public void onViewRecycled(@NonNull SliderViewHolder holder) {
        super.onViewRecycled(holder);
        mViewPager2.unregisterOnPageChangeCallback(onPageChangeCallback);
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_slider);
        }

        public void setImage(SliderModel sliderItems) {
            if (sliderItems != null && sliderItems.getUrl() != null) {
                Glide.with(mContext).load(sliderItems.getUrl()).placeholder(R.drawable.banner_1).apply(new RequestOptions().transform(new CenterCrop(), new RoundedCornersTransformation(100))).into(imageView);
            }
        }
    }

}
