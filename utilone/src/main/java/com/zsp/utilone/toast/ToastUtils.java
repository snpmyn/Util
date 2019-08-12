package com.zsp.utilone.toast;

import android.content.Context;
import android.widget.Toast;

/**
 * Created on 2017/9/14.
 *
 * @author 郑少鹏
 * @decs ToastUtils
 */
public class ToastUtils {
    private static Toast toast;

    /**
     * 短吐司
     *
     * @param context 上下文
     * @param content 内容
     */
    public static void shortShow(Context context, String content) {
        if (null != toast) {
            toast.cancel();
        }
        toast = Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT);
        toast.show();
    }
}
