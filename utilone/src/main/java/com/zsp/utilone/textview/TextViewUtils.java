package com.zsp.utilone.textview;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.NotNull;

/**
 * Created on 2019/1/8.
 *
 * @author 郑少鹏
 * @desc TextViewUtils
 */
public class TextViewUtils {
    /**
     * 设Drawable
     *
     * @param context  上下文
     * @param resId    资源ID
     * @param textView 控件
     */
    public static void setTopDrawableByInt(Context context, int resId, TextView textView) {
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            textView.setCompoundDrawablePadding(6);
        }
    }

    /**
     * 设右上红点
     * <p>
     * 红点Drawable（圆）宽高6dip较适宜。
     *
     * @param context    上下文
     * @param drawableId 位图ID
     * @param textView   控件
     */
    public static void setRightTopRedDot(Context context, int drawableId, TextView textView) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable != null) {
            drawable.setBounds(0, -16, 16, 0);
            textView.setCompoundDrawables(null, null, drawable, null);
        }
    }

    /**
     * TextView宽
     *
     * @param context 上下文
     * @param tv      控件
     * @return TextView宽
     */
    public static int textViewWidth(@NotNull Context context, @NotNull TextView tv) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels - tv.getPaddingLeft() - tv.getPaddingRight();
    }

    /**
     * 设Drawable色
     *
     * @param textView 控件
     * @param color    色
     */
    public static void setDrawableColor(@NotNull TextView textView, int color) {
        Drawable[] drawables = textView.getCompoundDrawables();
        for (Drawable drawable : drawables) {
            if (null != drawable) {
                drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
            }
        }
    }
}
