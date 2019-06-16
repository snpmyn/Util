package com.zsp.utilone;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.ref.WeakReference;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created on 2017/8/22.
 *
 * @author 郑少鹏
 * @decs KeyboardUtils
 */
public class KeyboardUtils {
    private KeyboardUtils() {

    }

    /**
     * 打开软件盘
     *
     * @param context  上下文
     * @param editText 软键盘
     */
    public static void openKeyboardThree(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    /**
     * 关软键盘
     *
     * @param context  上下文
     * @param editText 软键盘
     */
    public static void closeKeyboard(Context context, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    /**
     * 于活动关软键盘
     *
     * @param activity 活动
     */
    public static void closeKeyboardInActivity(Activity activity) {
        WeakReference<Activity> weakReference = new WeakReference<>(activity);
        Activity activityUse = weakReference.get();
        if (activityUse == null || activityUse.getCurrentFocus() == null) {
            return;
        }
        ((InputMethodManager) activityUse.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activityUse.getCurrentFocus().
                getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
