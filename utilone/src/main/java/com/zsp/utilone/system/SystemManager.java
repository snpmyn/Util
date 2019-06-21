package com.zsp.utilone.system;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * Created on 2018/6/20.
 *
 * @author 郑少鹏
 * @desc 系统管理器
 */
public class SystemManager {
    /**
     * 手机型号
     *
     * @return 手机型号
     */
    public static String phoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 系统版本号
     */
    public static String osVersionCode() {
        String release = Build.VERSION.RELEASE;
        release = "android" + release;
        return release;
    }

    /**
     * 系统SDK版本号
     */
    public static int osSdkVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 包信息
     * <p>
     * 据包名获。
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 包信息
     */
    public static PackageInfo packageInfo(Context context, String packageName) {
        if (null == context) {
            return null;
        }
        if (TextUtils.isEmpty(packageName)) {
            packageName = context.getPackageName();
        }
        PackageInfo packageInfo = null;
        PackageManager manager = context.getPackageManager();
        try {
            packageInfo = manager.getPackageInfo(packageName, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("packageInfo", e.toString());
        }
        return packageInfo;
    }

    /**
     * 本地IP
     */
    public static String localIp() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface networkInterface = en.nextElement();
                Enumeration<InetAddress> enumIpAddress = networkInterface.getInetAddresses();
                while (enumIpAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumIpAddress.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            Log.e("localIpAddress", e.toString());
        }
        return null;
    }

    /**
     * 设备信息
     */
    public static String[] deviceInfo() {
        String str1 = "/proc/cpuinfo";
        String str2;
        String[] cpuInfo = {"", ""};
        String[] arrayOfString;
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (int i = 2; i < arrayOfString.length; i++) {
                cpuInfo[0] = new StringBuilder().append(cpuInfo[0]).append(arrayOfString[i]).append(" ").toString();
            }
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            cpuInfo[1] += arrayOfString[2];
            localBufferedReader.close();
        } catch (IOException e) {
            Log.e("getDeviceInfo", e.toString());
        }
        return cpuInfo;
    }

    /**
     * CPU数
     */
    public static int cpuCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 全空间（byte）
     */
    public static long totalSpace() {
        long totalSpace = -1L;
        try {
            String path = Environment.getDataDirectory().getPath();
            StatFs stat = new StatFs(path);
            long blockSize = stat.getBlockSizeLong();
            // 该区可用文件系统数
            long totalBlocks = stat.getBlockCountLong();
            totalSpace = totalBlocks * blockSize;
        } catch (Exception e) {
            Log.e("全空间", e.toString());
        }
        return totalSpace;
    }

    /**
     * 可用空间（byte）
     */
    public static long availableSpace() {
        long availableSpace = -1L;
        try {
            String path = Environment.getDataDirectory().getPath();
            // 数据目录
            // 一模拟linux的df命令的一个类（获SD卡和手机内存用况）
            StatFs stat = new StatFs(path);
            // 返int（单位字节，一文件系统）
            long blockSize = stat.getBlockSizeLong();
            // 返int
            long availableBlocks = stat.getAvailableBlocksLong();
            // 当前可用存储空间
            availableSpace = availableBlocks * blockSize;
        } catch (Exception e) {
            Log.e("可用空间", e.toString());
        }
        return availableSpace;
    }

    /**
     * 应用最大分配内存（byte）
     *
     * @param context 上下文
     * @return 单应用最大分配内存
     */
    public static long applicationMaxAllocatedMemory(Context context) {
        if (context == null) {
            return -1;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            return activityManager.getMemoryClass() * 1024 * 1024;
        } else {
            return 0;
        }
    }

    /**
     * 指定应用占内存（byte）
     *
     * @param context     上下文
     * @param packageName 包名
     * @return 指定应用占内存（byte）
     */
    public static long theApplicationTakesUpMemory(Context context, String packageName) {
        if (context == null) {
            return -1;
        }
        if (TextUtils.isEmpty(packageName)) {
            packageName = context.getPackageName();
        }
        long size = 0;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runApps;
        if (activityManager != null) {
            runApps = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo runApp : runApps) {
                // 遍历运行中程序
                if (packageName.equals(runApp.processName)) {
                    // 程序进程名（通包名，然有些程序进程名并非对应包名）
                    // 返指定PID程序内存信息（可传递多PID，返亦数组型信息）
                    Debug.MemoryInfo[] processMemoryInfo = activityManager.getProcessMemoryInfo(new int[]{runApp.pid});
                    // 内存信息中已用内存（K）
                    size = processMemoryInfo[0].getTotalPrivateDirty() * 1024;
                }
            }
        }
        return size;
    }
}
