package com.example.animalfood;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class RoundedCornersTransformation extends BitmapTransformation {
    private static final String ID = "com.example.animalfood.RoundedCornersTransformation";
    private static final byte[] ID_BYTES = ID.getBytes();

    private float cornerRadius;

    public RoundedCornersTransformation(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap result = pool.get(toTransform.getWidth(), toTransform.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(toTransform.getWidth(), toTransform.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(null);

        RectF rect = new RectF(0f, 0f, toTransform.getWidth(), toTransform.getHeight());
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);

        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawBitmap(toTransform, 0f, 0f, paint);

        return result;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof RoundedCornersTransformation;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}
