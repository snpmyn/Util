package com.zsp.utilone.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import timber.log.Timber;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created on 2017/9/19.
 *
 * @author 郑少鹏
 * @desc ActivitySuperviseManager
 * Application：
 * {@link Application#registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks)}之onActivityCreated当{@link AppCompatActivity#onCreate(Bundle, PersistableBundle)}时执行，android:launchMode="singleTask"时不执行。
 * {@link Application#registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks)}onActivityDestroyed当{@link AppCompatActivity#finish()(Bundle, PersistableBundle)}时执行，android:launchMode="singleTask"时不执行。
 * 基类：
 * 基类之onCreate推当前Activity至Activity管理容器，需时遍历容器并finish所有Activity。
 */
public class ActivitySuperviseManager {
    private static final List<Activity> ACTIVITIES = Collections.synchronizedList(new LinkedList<>());

    /**
     * 推Activity至堆栈
     *
     * @param activity Activity
     */
    public static void pushActivity(Activity activity) {
        ACTIVITIES.add(activity);
        Timber.d("推入：%s", activity.getClass().getSimpleName());
        Timber.d("活动数：%s", ACTIVITIES.size());
        for (int i = 0; i < ACTIVITIES.size(); i++) {
            Timber.d("概览：%s", ACTIVITIES.get(i).getClass().getSimpleName());
        }
    }

    /**
     * 从堆栈去Activity
     *
     * @param activity Activity
     */
    public static void removeActivity(Activity activity) {
        ACTIVITIES.remove(activity);
        Timber.d("去除：%s", activity.getClass().getSimpleName());
        Timber.d("活动数：%s", ACTIVITIES.size());
        for (int i = 0; i < ACTIVITIES.size(); i++) {
            Timber.d("概览：%s", ACTIVITIES.get(i).getClass().getSimpleName());
        }
    }

    /**
     * 当前Activity名
     * <p>
     * info.topActivity.getShortClassName() Activity名
     * info.topActivity.getClassName() 类名
     * info.topActivity.getPackageName() 包名
     * info.topActivity.getClass() 类实例
     *
     * @param context 上下文
     * @return 当前Activity名
     */
    public static String getCurrentRunningActivityName(@NotNull Context context) {
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo runningTaskInfo = activityManager != null ? activityManager.getRunningTasks(1).get(0) : null;
        String currentRunningActivityName;
        if (null != runningTaskInfo && null != runningTaskInfo.topActivity) {
            currentRunningActivityName = runningTaskInfo.topActivity.getShortClassName();
        } else {
            currentRunningActivityName = null;
        }
        Timber.d("当前活动名：%s", currentRunningActivityName);
        return currentRunningActivityName;
    }

    /**
     * 栈顶Activity实例
     *
     * @return 栈顶Activity实例
     */
    public static @Nullable Activity getTopActivityInstance() {
        Activity topActivityInstance;
        synchronized (ACTIVITIES) {
            final int size = ACTIVITIES.size() - 1;
            if (size < 0) {
                return null;
            }
            topActivityInstance = ACTIVITIES.get(size);
        }
        return topActivityInstance;
    }

    /**
     * 结束指定Activity
     *
     * @param activity Activity
     */
    private static void finishActivity(Activity activity) {
        if (ACTIVITIES.isEmpty()) {
            return;
        }
        if (activity != null) {
            Timber.d("结束：%s", activity.getClass().getSimpleName());
            ACTIVITIES.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名Activity
     *
     * @param cls Class<?>
     */
    public static void finishActivity(Class<?> cls) {
        if (ACTIVITIES.isEmpty()) {
            return;
        }
        for (Activity activity : ACTIVITIES) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    private static void finishAllActivity() {
        for (Activity activity : ACTIVITIES) {
            activity.finish();
        }
        ACTIVITIES.clear();
    }

    /**
     * 退应用
     */
    public static void appExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {
            Timber.e(e);
        }
    }
}
