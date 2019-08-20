package com.zsp.utilone.glide.transformation;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

/**
 * Created on 2019/8/20.
 *
 * @author 郑少鹏
 * @desc CenterCropRoundCornerTransform
 */
public class CenterCropRoundCornerTransform extends CenterCrop {
    private int radius;

    /**
     * constructor
     *
     * @param radius 半径
     */
    public CenterCropRoundCornerTransform(int radius) {
        super();
        this.radius = radius;
    }

    @Override
    public Bitmap transform(@NonNull BitmapPool bitmapPool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap transform = super.transform(bitmapPool, toTransform, outWidth, outHeight);
        return roundCrop(bitmapPool, transform);
    }

    private Bitmap roundCrop(BitmapPool bitmapPool, Bitmap bitmap) {
        if (null == bitmap) {
            return null;
        }
        Bitmap bitmapResult = bitmapPool.get(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapResult);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF fRect = new RectF(0.0f, 0.0f, Integer.valueOf(bitmap.getWidth()).floatValue(), Integer.valueOf(bitmap.getHeight()).floatValue());
        canvas.drawRoundRect(fRect, Integer.valueOf(radius).floatValue(), Integer.valueOf(radius).floatValue(), paint);
        return bitmapResult;
    }
}
