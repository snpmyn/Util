package com.zsp.utilone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.TELEPHONY_SERVICE;
import static android.content.Context.WIFI_SERVICE;

/**
 * Created on 2018/6/5.
 * 参考 https://developer.android.com/training/basics/network-ops/managing.html
 * ConnectivityManager: Answers queries about the state of network connectivity.
 * It also notifies applications when network connectivity changes.
 * NetworkInfo: Describes the status of a network interface of a given type (currently either Mobile or Wi-Fi).
 *
 * @author 郑少鹏
 * @desc NetUtils
 */
public class NetUtils {
    private static final String TAG = "NetUtils";
    private static final boolean D = true;
    private static NetConnChangedReceiver sNetConnChangedReceiver = new NetConnChangedReceiver();
    private static List<NetConnChangedListener> sNetConnChangedListeners = new ArrayList<>();

    private NetUtils() {
        throw new IllegalStateException("No instance!");
    }

    /**
     * 网络接口可用否（即网络连接可行否）
     * 连接（即存网络连接否，可建套接字并传数据否）
     *
     * @param context 上下文
     * @return 可用true
     */
    public static boolean isNetConnected(Context context) {
        checkNonNull(context.getApplicationContext(), "context == null");
        NetworkInfo activeInfo = getActiveNetworkInfo(context);
        return (activeInfo != null && activeInfo.isConnected());
    }

    /**
     * 移动数据否
     *
     * @param context 上下文
     * @return 移动数据true
     */
    public static boolean isMobileConnected(Context context) {
        checkNonNull(context.getApplicationContext(), "context == null");
        NetworkInfo activeInfo = getActiveNetworkInfo(context);
        return (activeInfo != null && activeInfo.isConnected() && activeInfo.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * 2G否
     *
     * @param context 上下文
     * @return 2G true
     */
    public static boolean is2GConnected(Context context) {
        checkNonNull(context.getApplicationContext(), "context == null");
        NetworkInfo activeInfo = getActiveNetworkInfo(context);
        if (activeInfo == null || !activeInfo.isConnected()) {
            return false;
        }
        int subtype = activeInfo.getSubtype();
        switch (subtype) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_GSM:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return true;
            default:
                return false;
        }
    }

    /**
     * 3G否
     *
     * @param context 上下文
     * @return 3G true
     */
    public static boolean is3GConnected(Context context) {
        checkNonNull(context.getApplicationContext(), "context == null");
        NetworkInfo activeInfo = getActiveNetworkInfo(context);
        if (activeInfo == null || !activeInfo.isConnected()) {
            return false;
        }
        int subtype = activeInfo.getSubtype();
        switch (subtype) {
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
            case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                return true;
            default:
                return false;
        }
    }

    /**
     * 4G否
     *
     * @param context 上下文
     * @return 4G true
     */
    public static boolean is4GConnected(Context context) {
        checkNonNull(context.getApplicationContext(), "context == null");
        NetworkInfo activeInfo = getActiveNetworkInfo(context);
        if (activeInfo == null || !activeInfo.isConnected()) {
            return false;
        }
        int subtype = activeInfo.getSubtype();
        switch (subtype) {
            case TelephonyManager.NETWORK_TYPE_LTE:
            case TelephonyManager.NETWORK_TYPE_IWLAN:
                return true;
            default:
                return false;
        }
    }

    /**
     * 移动网络运营商名
     * 中国联通
     * 中国移动
     * 中国电信
     *
     * @param context 上下文
     * @return 移动网络运营商名
     */
    public static String getNetworkOperatorName(Context context) {
        checkNonNull(context.getApplicationContext(), "context == null");
        TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(TELEPHONY_SERVICE);
        return tm != null ? tm.getNetworkOperatorName() : null;
    }

    /**
     * 移动终端类型
     * <ul>
     * <li>{@link TelephonyManager#PHONE_TYPE_NONE } : 0 手机制式未知</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_GSM  } : 1 手机制式GSM（移动和联通）</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_CDMA } : 2 手机制式CDMA（电信）</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_SIP  } : 3</li>
     * </ul>
     *
     * @param context 上下文
     * @return 手机制式
     */
    public static int getPhoneType(Context context) {
        checkNonNull(context.getApplicationContext(), "context == null");
        TelephonyManager tm = (TelephonyManager) context.getApplicationContext().getSystemService(TELEPHONY_SERVICE);
        return tm != null ? tm.getPhoneType() : 0;
    }

    /**
     * Wifi连否
     *
     * @param context 上下文
     * @return wifi连true
     */
    public static boolean isWifiConnected(Context context) {
        checkNonNull(context.getApplicationContext(), "context == null");
        NetworkInfo activeInfo = getActiveNetworkInfo(context);
        return (activeInfo != null && activeInfo.isConnected() && activeInfo.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * 连WIFI
     *
     * @param context 上下文
     */
    public static void wifiConnect(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getApplicationContext().getSystemService(WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
    }

    /**
     * 注网络接收者
     *
     * @param context 上下文
     */
    public static void registerNetConnChangedReceiver(Context context) {
        checkNonNull(context.getApplicationContext(), "context == null");
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.getApplicationContext().registerReceiver(sNetConnChangedReceiver, filter);
    }

    /**
     * 反注网络接收者
     *
     * @param context 上下文
     */
    public static void unregisterNetConnChangedReceiver(Context context) {
        checkNonNull(context.getApplicationContext(), "context == null");
        context.getApplicationContext().unregisterReceiver(sNetConnChangedReceiver);
        sNetConnChangedListeners.clear();
    }

    /**
     * 添网状变监听
     *
     * @param listener 网连状变监听
     */
    public static void addNetConnChangedListener(NetConnChangedListener listener) {
        checkNonNull(listener, "listener == null");
        boolean result = sNetConnChangedListeners.add(listener);
        log("addNetConnChangedListener: " + result);
    }

    /**
     * 移网状变监听
     *
     * @param listener 网连状变监听
     */
    public static void removeNetConnChangedListener(NetConnChangedListener listener) {
        checkNonNull(listener, "listener == null");
        boolean result = sNetConnChangedListeners.remove(listener);
        log("removeNetConnChangedListener: " + result);
    }

    private static void broadcastConnStatus(ConnectStatus connectStatus) {
        int size = sNetConnChangedListeners.size();
        if (size == 0) {
            return;
        }
        for (int i = 0; i < size; i++) {
            sNetConnChangedListeners.get(i).onNetConnChanged(connectStatus);
        }
    }

    /**
     * 网络信息
     *
     * @param context 上下文
     * @return 网络信息
     */
    private static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        return connMgr != null ? connMgr.getActiveNetworkInfo() : null;
    }

    private static void checkNonNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    private static void log(String msg) {
        if (D) {
            Log.e(TAG, msg);
        }
    }

    public enum ConnectStatus {
        /**
         * 无网
         */
        NO_NETWORK,
        WIFI,
        MOBILE,
        MOBILE_2G,
        MOBILE_3G,
        MOBILE_4G,
        MOBILE_UNKNOWN,
        OTHER,
        NO_CONNECTED
    }

    public interface NetConnChangedListener {
        /**
         * 网络连状变
         *
         * @param connectStatus 连状
         */
        void onNetConnChanged(ConnectStatus connectStatus);
    }

    private static final class NetConnChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            log("onReceive");
            NetworkInfo activeInfo = getActiveNetworkInfo(context);
            if (activeInfo == null) {
                broadcastConnStatus(ConnectStatus.NO_NETWORK);
            } else if (activeInfo.isConnected()) {
                int networkType = activeInfo.getType();
                if (ConnectivityManager.TYPE_WIFI == networkType) {
                    broadcastConnStatus(ConnectStatus.WIFI);
                } else if (ConnectivityManager.TYPE_MOBILE == networkType) {
                    broadcastConnStatus(ConnectStatus.MOBILE);
                    int subtype = activeInfo.getSubtype();
                    if (TelephonyManager.NETWORK_TYPE_GPRS == subtype
                            || TelephonyManager.NETWORK_TYPE_GSM == subtype
                            || TelephonyManager.NETWORK_TYPE_EDGE == subtype
                            || TelephonyManager.NETWORK_TYPE_CDMA == subtype
                            || TelephonyManager.NETWORK_TYPE_1xRTT == subtype
                            || TelephonyManager.NETWORK_TYPE_IDEN == subtype) {
                        broadcastConnStatus(ConnectStatus.MOBILE_2G);
                    } else if (TelephonyManager.NETWORK_TYPE_UMTS == subtype
                            || TelephonyManager.NETWORK_TYPE_EVDO_0 == subtype
                            || TelephonyManager.NETWORK_TYPE_EVDO_A == subtype
                            || TelephonyManager.NETWORK_TYPE_HSDPA == subtype
                            || TelephonyManager.NETWORK_TYPE_HSUPA == subtype
                            || TelephonyManager.NETWORK_TYPE_HSPA == subtype
                            || TelephonyManager.NETWORK_TYPE_EVDO_B == subtype
                            || TelephonyManager.NETWORK_TYPE_EHRPD == subtype
                            || TelephonyManager.NETWORK_TYPE_HSPAP == subtype
                            || TelephonyManager.NETWORK_TYPE_TD_SCDMA == subtype) {
                        broadcastConnStatus(ConnectStatus.MOBILE_3G);
                    } else if (TelephonyManager.NETWORK_TYPE_LTE == subtype
                            || TelephonyManager.NETWORK_TYPE_IWLAN == subtype) {
                        broadcastConnStatus(ConnectStatus.MOBILE_4G);
                    } else {
                        broadcastConnStatus(ConnectStatus.MOBILE_UNKNOWN);
                    }
                } else {
                    broadcastConnStatus(ConnectStatus.OTHER);
                }
            } else {
                broadcastConnStatus(ConnectStatus.NO_CONNECTED);
            }
        }
    }
}
