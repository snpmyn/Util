package com.zsp.utilone.miui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * Created on 2019/7/8.
 *
 * @author 郑少鹏
 * @desc MiuiUtils
 */
public class MiuiUtils {
    /**
     * MIUI
     *
     * @return MIUI
     */
    public static boolean isMiUi() {
        // 字符串自定（小米xiaomi、华为huawei、魅族meizu）
        return "xiaomi".equalsIgnoreCase(Build.MANUFACTURER);
    }

    /**
     * 跳至权限设置页
     *
     * @param context 上下文
     */
    public static void jumpToPermissionsEditorActivity(Context context) {
        try {
            // MIUI 8
            Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            localIntent.putExtra("extra_pkgname", context.getPackageName());
            context.startActivity(localIntent);
        } catch (Exception e) {
            try {
                // MIUI 5/6/7
                Intent localIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                localIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                localIntent.putExtra("extra_pkgname", context.getPackageName());
                context.startActivity(localIntent);
            } catch (Exception e1) {
                // 跳应用详情
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            }
        }
    }
}
