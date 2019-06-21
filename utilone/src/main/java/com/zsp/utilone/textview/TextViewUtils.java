package com.zsp.utilone.textview;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

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
    public static void setDrawableByInt(Context context, int resId, TextView textView) {
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            textView.setCompoundDrawablePadding(6);
        }
    }

    /**
     * TextView宽
     *
     * @param context 上下文
     * @param tv      控件
     * @return TextView宽
     */
    public static int textViewWidth(Context context, TextView tv) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels - tv.getPaddingLeft() - tv.getPaddingRight();
    }

    /**
     * 设Drawable色
     *
     * @param textView 控件
     * @param color    色
     */
    public void setDrawableColor(TextView textView, int color) {
        Drawable[] drawables = textView.getCompoundDrawables();
        for (Drawable drawable : drawables) {
            if (null != drawable) {
                drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
            }
        }
    }
}
