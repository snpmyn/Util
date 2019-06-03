package com.zsp.utilone;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

/**
 * Created on 2019/6/3.
 *
 * @author 郑少鹏
 * @desc SnackbarUtils
 */
public class SnackbarUtils {
    /**
     * 通字符串序列创Snackbar
     *
     * @param view         视图
     * @param charSequence 字符序列
     * @param lengthLong   长否
     * @return Snackbar
     */
    public static Snackbar snackbarCreateByCharSequence(View view, CharSequence charSequence, boolean lengthLong) {
        return Snackbar.make(view, charSequence, lengthLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT);
    }

    /**
     * 通字符串序列创Snackbar附通字符串序列动作
     *
     * @param view                    视图
     * @param createCharSequence      创字符序列
     * @param lengthLong              长否
     * @param actionCharSequence      动作字符序列
     * @param snackbarOnClickListener Snackbar点监听
     * @return Snackbar
     */
    public static Snackbar snackbarCreateByCharSequenceWithActionByCharSequence(
            View view, CharSequence createCharSequence, boolean lengthLong,
            CharSequence actionCharSequence, SnackbarOnClickListener snackbarOnClickListener) {
        Snackbar snackbar = snackbarCreateByCharSequence(view, createCharSequence, lengthLong);
        snackbar.setAction(actionCharSequence, v -> snackbarOnClickListener.snackbarOnClickListener(v, snackbar));
        return snackbar;
    }

    /**
     * 通资源ID创Snackbar
     *
     * @param view       视图
     * @param resId      资源ID
     * @param lengthLong 长否
     * @return Snackbar
     */
    public static Snackbar snackbarCreateByResId(View view, int resId, boolean lengthLong) {
        return Snackbar.make(view, resId, lengthLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT);
    }

    /**
     * 通资源ID创Snackbar附通资源ID动作
     *
     * @param view                    视图
     * @param createResId             创资源ID
     * @param lengthLong              长否
     * @param actionResId             动作资源ID
     * @param snackbarOnClickListener Snackbar点监听
     * @return Snackbar
     */
    public static Snackbar snackbarCreateByResIdWithActionByResId(
            View view, int createResId, boolean lengthLong,
            CharSequence actionResId, SnackbarOnClickListener snackbarOnClickListener) {
        Snackbar snackbar = snackbarCreateByResId(view, createResId, lengthLong);
        snackbar.setAction(actionResId, v -> snackbarOnClickListener.snackbarOnClickListener(v, snackbar));
        return snackbar;
    }

    public interface SnackbarOnClickListener {
        /**
         * Snackbar点监听
         *
         * @param view     视图
         * @param snackbar Snackbar
         */
        void snackbarOnClickListener(View view, Snackbar snackbar);
    }
}
