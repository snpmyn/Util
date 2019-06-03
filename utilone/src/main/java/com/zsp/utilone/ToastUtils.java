package com.zsp.utilone;

import android.content.Context;
import android.widget.Toast;

/**
 * Created on 2017/9/14.
 *
 * @author 郑少鹏
 * @decs 吐司
 */
public class ToastUtils {
    private static Toast toast;

    /**
     * 短吐司
     *
     * @param content 内容
     */
    public static void shortShow(Context context, String content) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT);
        toast.show();
    }
}
