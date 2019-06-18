package com.zsp.utilone;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created on 2017/11/17.
 *
 * @author 郑少鹏
 * @desc DensityUtils
 */
public class DensityUtils {
    /**
     * 设备独立像素转像素
     *
     * @param context 上下文
     * @param dip     设备独立像素
     * @return 像素
     */
    public static int dipToPxByFloat(Context context, float dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * 设备独立像素转像素
     *
     * @param dip 设备独立像素
     * @return 像素
     */
    public static int dipToPxByInt(int dip) {
        return (int) (dip * Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * 像素转设备独立像素
     *
     * @param context 上下文
     * @param px      像素
     * @return 设备独立像素
     */
    public static int pxToDip(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param context 上下文
     * @param px      px
     * @return sp
     */
    public static int pxToSp(Context context, float px) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param context 上下文
     * @param sp      sp
     * @return px
     */
    public static int spToPx(Context context, float sp) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }
}
