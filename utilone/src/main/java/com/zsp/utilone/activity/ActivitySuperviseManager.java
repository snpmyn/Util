package com.zsp.utilone.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.CopyOnWriteArrayList;

import timber.log.Timber;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created on 2017/9/19.
 *
 * @author 郑少鹏
 * @desc ActivitySuperviseManager
 * Application：
 * {@link Application#registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks)}之onActivityCreated当{@link AppCompatActivity#onCreate(Bundle, PersistableBundle)}时执行，android:launchMode="singleTask"时不执行。
 * {@link Application#registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks)}onActivityDestroyed当{@link AppCompatActivity#finish()} (Bundle, PersistableBundle)}时执行，android:launchMode="singleTask"时不执行。
 * 基类：
 * 基类之onCreate推当前Activity至Activity管理容器，需时遍历容器并finish所有Activity。
 */
public class ActivitySuperviseManager {
    private static final CopyOnWriteArrayList<Activity> ACTIVITIES = new CopyOnWriteArrayList<>();

    /**
     * 添Activity至堆栈
     *
     * @param activity Activity
     */
    public static void pushActivity(Activity activity) {
        ACTIVITIES.add(activity);
        Timber.d("活动数：%s", ACTIVITIES.size());
        for (int i = 0; i < ACTIVITIES.size(); i++) {
            Timber.d("概览：%s", ACTIVITIES.get(i).getClass().getSimpleName());
        }
        Timber.d("推入：%s", ACTIVITIES.get(ACTIVITIES.size() - 1).getClass().getSimpleName());
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
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static String getCurrentRunningActivityName(Context context) {
        ActivityManager manager = (ActivityManager) context.getApplicationContext().getSystemService(ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo info = manager != null ? manager.getRunningTasks(1).get(0) : null;
        String activityName;
        if (null != info && null != info.topActivity) {
            activityName = info.topActivity.getShortClassName();
        } else {
            activityName = null;
        }
        Timber.d("当前活动：%s", activityName);
        return activityName;
    }

    /**
     * 栈顶Activity实例
     *
     * @return 栈顶Activity实例
     */
    public static Activity getTopActivityInstance() {
        Activity mBaseActivity;
        synchronized (ACTIVITIES) {
            final int size = ACTIVITIES.size() - 1;
            if (size < 0) {
                return null;
            }
            mBaseActivity = ACTIVITIES.get(size);
        }
        return mBaseActivity;
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
