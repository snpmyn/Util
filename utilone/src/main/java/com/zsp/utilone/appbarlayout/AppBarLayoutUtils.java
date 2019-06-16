package com.zsp.utilone.appbarlayout;

import android.view.View;

import com.google.android.material.appbar.AppBarLayout;

/**
 * Created on 2019/6/14.
 *
 * @author 郑少鹏
 * @desc AppBarLayoutUtils
 */
public class AppBarLayoutUtils {
    /**
     * 滑标志
     * <p>
     * CollapsingToolbarLayout自身app:layout_scrollFlags含scroll时下设layout_behavior布局无法垂直居中。
     * 不设scroll上无法滑而吸顶。
     * 动设即可。
     *
     * @param view  视图
     * @param flags 标志
     */
    public static void setScrollFlags(View view, int flags) {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) view.getLayoutParams();
        params.setScrollFlags(flags);
        view.setLayoutParams(params);
    }
}
