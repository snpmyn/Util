package com.zsp.utilone.view;

import android.view.View;

/**
 * Created on 2018/12/21.
 *
 * @author 郑少鹏
 * @desc ViewUtils
 */
public class ViewUtils {
    /**
     * 显示视图
     *
     * @param view 视图
     */
    public static void showView(View view) {
        int visibility = view.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏视图
     *
     * @param view  视图
     * @param state 状态
     */
    public static void hideView(View view, int state) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(state);
        }
    }

    /**
     * 视图高
     *
     * @param view 视图
     * @return 视图高
     */
    public static int viewHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }

    /**
     * 视图宽
     *
     * @param view 视图
     * @return 视图宽
     */
    public static int viewWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredWidth();
    }
}
