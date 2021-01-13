package com.zsp.utilone.storage.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017/11/24.
 * getSharedPreferences()
 * Fragment内访通资源字符串R.string.preference_file_key识别共享首选项文件并专用模式打开，仅允许您应用访文件。
 * 命名您共享首选项文件应使用对您应用唯一可识别名称，如"com.example.myapp.PREFERENCE_FILE_KEY"。
 * <p>
 * getPreferences()
 * 仅需Activity一共享首选项，从Activity调此法，该法无需提供名称并检索属该Activity默认共享首选项文件。
 * <p>
 * getDefaultSharedPreferences()
 * 默应用包名
 *
 * @author 郑少鹏
 * @desc SharedPreferencesUtils
 */
public class SharedPreferencesUtils {
    /**
     * 获SharedPreferences
     *
     * @param context 上下文
     * @return SharedPreferences
     */
    public static SharedPreferences createSharedPreferences(@NotNull Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    /**
     * Save int data.
     *
     * @param context 上下文
     * @param key     key
     * @param value   value
     */
    public static void saveInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = createSharedPreferences(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Save long data.
     *
     * @param context 上下文
     * @param key     key
     * @param value   value
     */
    public static void saveLong(Context context, String key, long value) {
        SharedPreferences.Editor editor = createSharedPreferences(context).edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * Save String data.
     *
     * @param context 上下文
     * @param key     key
     * @param value   value
     */
    public static void saveString(Context context, String key, String value) {
        SharedPreferences.Editor editor = createSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Save List<String> data.
     *
     * @param context 上下文
     * @param key     key
     * @param list    list
     */
    public static void saveListString(Context context, String key, @NotNull List<String> list) {
        // 存前清已存数据保唯一性
        clearListString(context, key);
        int size = list.size();
        saveInt(context, key + "size", size);
        for (int i = 0; i < size; i++) {
            saveString(context, key + i, list.get(i));
        }
    }

    /**
     * Get int data.
     *
     * @param context 上下文
     * @param key     key
     * @return int
     */
    public static int getInt(Context context, String key) {
        SharedPreferences sharedPreferences = createSharedPreferences(context);
        return sharedPreferences.getInt(key, 0);
    }

    /**
     * Get long data.
     *
     * @param context 上下文
     * @param key     key
     * @return long
     */
    public static long getLong(Context context, String key) {
        SharedPreferences sharedPreferences = createSharedPreferences(context);
        return sharedPreferences.getLong(key, 0);
    }

    /**
     * Get String data.
     *
     * @param context  上下文
     * @param key      key
     * @param defValue defValue
     * @return string
     */
    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sharedPreferences = createSharedPreferences(context);
        return sharedPreferences.getString(key, defValue);
    }

    /**
     * Get List<String> data.
     *
     * @param context 上下文
     * @param key     key
     * @return list<String>
     */
    public static @NotNull List<String> getListString(Context context, String key) {
        List<String> list = new ArrayList<>();
        int size = getInt(context, key + "size");
        for (int i = 0; i < size; i++) {
            list.add(getString(context, key + i, null));
        }
        return list;
    }

    /**
     * Clear List<String> data.
     *
     * @param context 上下文
     * @param key     key
     */
    public static void clearListString(Context context, String key) {
        int size = getInt(context, key + "size");
        if (0 == size) {
            return;
        }
        clearValueByKey(context, key + "size");
        for (int i = 0; i < size; i++) {
            clearValueByKey(context, key + i);
        }
    }

    /**
     * Clear ListString's one data.
     *
     * @param context 上下文
     * @param key     key
     * @param str     str
     */
    public static void clearListStringOne(Context context, String key, String str) {
        int size = getInt(context, key + "size");
        if (0 == size) {
            return;
        }
        List<String> list = getListString(context, key);
        for (String string : list) {
            if (string.equals(str)) {
                list.remove(str);
                saveListString(context, key, list);
            }
        }
    }

    /**
     * Clear data corresponding to key.
     *
     * @param context 上下文
     * @param key     key
     */
    public static void clearValueByKey(Context context, String key) {
        SharedPreferences.Editor editor = createSharedPreferences(context).edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * Clear all data.
     *
     * @param context 上下文
     */
    public static void clearAll(Context context) {
        SharedPreferences.Editor editor = createSharedPreferences(context).edit();
        editor.clear();
        editor.apply();
    }
}
