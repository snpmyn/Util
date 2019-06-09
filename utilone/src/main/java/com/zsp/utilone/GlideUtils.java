package com.zsp.utilone;

import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created on 2017/11/17.
 *
 * @author 郑少鹏
 * @desc GlideUtils
 * context类型影Glide加载优化，Glide监视activity生命周期并activity销毁时自取等待请求。Application context优化失效。
 * 不同Glide v3，Glide v4不默交叉淡入或其它过渡效果。每请求须手动应用过渡。
 * override(width, height)重写宽高后或致获图模糊。
 */
public class GlideUtils {
    /**
     * 加载
     *
     * @param fragmentActivity 上下文
     * @param rId              int资源符
     * @param cId              颜色占位符
     * @param iv               控件
     */
    public static void loadByIntPlaceHolderColorCircleCrop(FragmentActivity fragmentActivity, int rId, int cId, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .placeholder(new ColorDrawable(ContextCompat.getColor(fragmentActivity, cId)))
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(fragmentActivity)
                .load(rId)
                .apply(options)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                // sizeMultiplier
                .thumbnail(0.25f)
                .transition(withCrossFade())
                .into(iv);
    }

    /**
     * 加载
     *
     * @param fragmentActivity 上下文
     * @param strId            String资源符
     * @param cId              颜色占位符
     * @param iv               控件
     */
    public static void loadByStringPlaceHolderColorCircleCrop(FragmentActivity fragmentActivity, String strId, int cId, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .placeholder(new ColorDrawable(ContextCompat.getColor(fragmentActivity, cId)))
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(fragmentActivity)
                .load(strId)
                .apply(options)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                // sizeMultiplier
                .thumbnail(0.25f)
                .transition(withCrossFade())
                .into(iv);
    }

    /**
     * 加载
     *
     * @param fragmentActivity 上下文
     * @param head             int资源符
     * @param cId              颜色占位符
     * @param radius           半径
     * @param iv               控件
     */
    public static void loadByIntPlaceHolderColorRoundedCorners(FragmentActivity fragmentActivity, int head, int cId, int radius, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .placeholder(new ColorDrawable(ContextCompat.getColor(fragmentActivity, cId)))
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(fragmentActivity)
                .load(head)
                .apply(options)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)))
                // sizeMultiplier
                .thumbnail(0.25f)
                .transition(withCrossFade())
                .into(iv);
    }

    /**
     * 加载
     *
     * @param fragmentActivity 上下文
     * @param strId            String资源符
     * @param cId              颜色占位符
     * @param radius           半径
     * @param iv               控件
     */
    public static void loadByStringPlaceHolderColorRoundedCorners(FragmentActivity fragmentActivity, String strId, int cId, int radius, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .placeholder(new ColorDrawable(ContextCompat.getColor(fragmentActivity, cId)))
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(fragmentActivity)
                .load(strId)
                .apply(options)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(radius)))
                // sizeMultiplier
                .thumbnail(0.25f)
                .transition(withCrossFade())
                .into(iv);
    }

    /**
     * 加载
     *
     * @param fragmentActivity 上下文
     * @param rId              int资源符
     * @param cId              颜色占位符
     * @param iv               控件
     */
    public static void loadByIntPlaceHolderColor(FragmentActivity fragmentActivity, int rId, int cId, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .placeholder(new ColorDrawable(ContextCompat.getColor(fragmentActivity, cId)))
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(fragmentActivity)
                .load(rId)
                .apply(options)
                // sizeMultiplier
                .thumbnail(0.25f)
                .transition(withCrossFade())
                .into(iv);
    }

    /**
     * 加载
     *
     * @param fragmentActivity 上下文
     * @param strId            String资源符
     * @param cId              颜色占位符
     * @param iv               控件
     */
    public static void loadByStringPlaceHolderColor(FragmentActivity fragmentActivity, String strId, int cId, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .placeholder(new ColorDrawable(ContextCompat.getColor(fragmentActivity, cId)))
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.DATA);
        Glide.with(fragmentActivity)
                .load(strId)
                .apply(options)
                // sizeMultiplier
                .thumbnail(0.25f)
                .transition(withCrossFade())
                .into(iv);
    }
}