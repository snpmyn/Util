package com.zsp.utilone;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created on 2018/11/28.
 *
 * @author 郑少鹏
 * @desc IntentUtils
 */
public class IntentUtils {
    /**
     * 重启
     *
     * @param application Application
     */
    public static void restart(Application application) {
        Intent intent1 = application.getPackageManager().getLaunchIntentForPackage(application.getPackageName());
        if (intent1 != null) {
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        application.startActivity(intent1);
    }

    /**
     * 携Bundle跳页
     *
     * @param context             上下文
     * @param targetActivityClass 目标活动
     * @param bundle              Bundle
     */
    public static void jumpWithBundle(Context context, Class<?> targetActivityClass, Bundle bundle) {
        Intent intent = new Intent(context, targetActivityClass);
        intent.putExtras(bundle);
        if (context instanceof Activity) {
            context.startActivity(intent);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    /**
     * 不携Bundle跳页
     *
     * @param context             上下文
     * @param targetActivityClass 目标活动
     */
    public static void jumpNoBundle(Context context, Class<?> targetActivityClass) {
        Intent intent = new Intent(context, targetActivityClass);
        if (context instanceof Activity) {
            context.startActivity(intent);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
