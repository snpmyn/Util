package com.zsp.utilone.intent;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.io.File;

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
        Intent intent = application.getPackageManager().getLaunchIntentForPackage(application.getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        application.startActivity(intent);
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

    /**
     * 打电话
     *
     * @param context         上下文
     * @param cellPhoneNumber 手机号
     */
    public static void phone(Context context, String cellPhoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + cellPhoneNumber);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 装APK
     *
     * @param context 上下文
     * @param apkPath APK路径
     */
    public static void installApk(Context context, String apkPath) {
        File apkFile = new File(apkPath);
        if (!apkFile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
