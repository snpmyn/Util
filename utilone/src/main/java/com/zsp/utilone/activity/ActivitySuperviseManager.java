package com.zsp.utilone.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.appcompat.app.AppCompatActivity;

import com.zsp.utilone.log.ErrorLogManager;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created on 2017/9/19.
 *
 * @author 郑少鹏
 * @desc ActivitySuperviseManager
 * Application：
 * 结合{@link Application#registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks)}。
 * {@link Application#registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks)}之onActivityCreated当{@link AppCompatActivity#onCreate(Bundle, PersistableBundle)}时执行，android:launchMode="singleTask"时不执行。
 * @link application.App#registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks)}onActivityDestroyed当{@link AppCompatActivity#finish()} (Bundle, PersistableBundle)}时执行，android:launchMode="singleTask"时不执行。
 * <p>
 * 基类：
 * 基类之onCreate推当前Activity至Activity管理容器，需时遍历容器并finish所有Activity。
 */
public class ActivitySuperviseManager {
    private static final List<Activity> ACTIVITIES = Collections.synchronizedList(new LinkedList<>());

    /**
     * 添Activity至堆栈
     *
     * @param activity 活动
     */
    public static void pushActivity(Activity activity) {
        ACTIVITIES.add(activity);
        ErrorLogManager.e("活动数", String.valueOf(ACTIVITIES.size()));
        for (int i = 0; i < ACTIVITIES.size(); i++) {
            ErrorLogManager.e("概览", ACTIVITIES.get(i).getClass().getSimpleName());
        }
        ErrorLogManager.e("推入", ACTIVITIES.get(ACTIVITIES.size() - 1).getClass().getSimpleName());
    }

    /**
     * 结束指定Activity
     *
     * @param activity 活动
     */
    public static void finishActivity(Activity activity) {
        if (ACTIVITIES == null || ACTIVITIES.isEmpty()) {
            return;
        }
        if (activity != null) {
            ACTIVITIES.remove(activity);
            activity.finish();
        }
        if (activity != null) {
            ErrorLogManager.e("结束：" + activity.getClass().getSimpleName());
        }
    }

    /**
     * 结束所有Activity
     */
    private static void finishAllActivity() {
        if (ACTIVITIES == null) {
            return;
        }
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
            ErrorLogManager.e(e.getMessage());
        }
    }

    /**
     * 当前Activity名
     * info.topActivity.getShortClassName() Activity名
     * info.topActivity.getClassName() 类名
     * info.topActivity.getPackageName() 包名
     * info.topActivity.getClass() 类实例
     *
     * @param context 上下文
     * @return 当前Activity名
     */
    public static String getCurrentRunningActivityName(Context context) {
        ActivityManager manager = (ActivityManager) context.getApplicationContext().getSystemService(ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo info = manager != null ? manager.getRunningTasks(1).get(0) : null;
        String activityName = info != null ? info.topActivity.getShortClassName() : null;
        ErrorLogManager.e("当前活动", activityName);
        return activityName;
    }
}