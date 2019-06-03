package com.zsp.utilone;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created on 2019/3/6.
 *
 * @author 郑少鹏
 * @desc ScreenUtils
 */
public class ScreenUtils {
    private static WindowManager windowManager;

    /**
     * 窗口管理器
     *
     * @param context 上下文
     * @return 窗口管理器
     */
    private static WindowManager getWindowManager(Context context) {
        if (windowManager == null) {
            windowManager = (WindowManager) context.getApplicationContext().getSystemService(WINDOW_SERVICE);
        }
        return windowManager;
    }

    /**
     * 屏宽（像素）
     *
     * @param context 上下文
     * @return 屏宽（像素）
     */
    public static int screenWidth(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager(context).getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    /**
     * 屏高（像素）
     *
     * @param context 上下文
     * @return 屏高（像素）
     */
    private static int screenHeight(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager(context).getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    /**
     * Setting window background alpha.
     *
     * @param activity Activity
     * @param alpha    Alpha
     */
    public static void setWindowBackgroundAlpha(Activity activity, float alpha) {
        WeakReference<Activity> weakReference = new WeakReference<>(activity);
        Window window = weakReference.get().getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = alpha;
        window.setAttributes(layoutParams);
    }
}