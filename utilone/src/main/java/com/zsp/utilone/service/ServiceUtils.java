package com.zsp.utilone.service;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created on 2019/3/26.
 *
 * @author 郑少鹏
 * @desc ServiceUtils
 */
public class ServiceUtils {
    /**
     * 服务运行否
     *
     * @param context     上下文
     * @param serviceName 服务名
     * @param maxNum      最大数
     * @return 服务运行否
     */
    public static boolean isServiceRunning(Context context, String serviceName, int maxNum) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // 此处前maxNum个查找（据需设）
        List<ActivityManager.RunningServiceInfo> runningServiceInfoList = am.getRunningServices(maxNum);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServiceInfoList) {
            // 比对服务名
            String name = runningServiceInfo.service.getClassName();
            if (serviceName.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
