package com.zsp.utilone.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.zsp.utilone.log.LogManager;

/**
 * Created on 2019/4/19.
 *
 * @author 郑少鹏
 * @desc App管理器
 */
public class AppManager {
    /**
     * 版本号
     *
     * @param context 上下文
     * @return 版本号
     */
    public static int versionCode(Context context) {
        int versionCode = 0;
        try {
            PackageManager manager = context.getApplicationContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
            versionCode = info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.e(e.getMessage());
        }
        return versionCode;
    }

    /**
     * 版本名
     *
     * @param context 上下文
     * @return 版本名
     */
    public static String versionName(Context context) {
        String versionName = null;
        try {
            PackageManager manager = context.getApplicationContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
            versionName = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            LogManager.e(e.getMessage());
        }
        return versionName;
    }
}
