package application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.tencent.mmkv.MMKVContentChangeNotification;
import com.tencent.mmkv.MMKVHandler;
import com.tencent.mmkv.MMKVLogLevel;
import com.tencent.mmkv.MMKVRecoverStrategic;
import com.zsp.util.BuildConfig;
import com.zsp.utilone.activity.ActivitySuperviseManager;
import com.zsp.utilone.storage.mmkv.MmkvInitConfigure;
import com.zsp.utilone.timber.configure.TimberInitConfigure;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

/**
 * Created on 2019/7/28.
 *
 * @author 郑少鹏
 * @desc 应用
 */
public class UtilApp extends Application implements MMKVHandler, MMKVContentChangeNotification {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化配置
        initConfiguration();
    }

    /**
     * 初始化配置
     */
    private void initConfiguration() {
        // timber
        TimberInitConfigure.initTimber(BuildConfig.DEBUG);
        // MMKV
        MmkvInitConfigure.initMmkv(this, BuildConfig.DEBUG, this, this);
        // 全局监听Activity生命周期
        registerActivityListener();
    }

    /**
     * Activity全局监听
     */
    private void registerActivityListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
                // 添监听到创事件Activity至集合
                ActivitySuperviseManager.pushActivity(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                // 移监听到销事件Activity出集合
                ActivitySuperviseManager.removeActivity(activity);
            }
        });
    }

    @Override
    public MMKVRecoverStrategic onMMKVCRCCheckFail(String s) {
        return MMKVRecoverStrategic.OnErrorRecover;
    }

    @Override
    public MMKVRecoverStrategic onMMKVFileLengthError(String s) {
        return MMKVRecoverStrategic.OnErrorRecover;
    }

    @Override
    public boolean wantLogRedirecting() {
        return true;
    }

    @Override
    public void mmkvLog(@NotNull MMKVLogLevel mmkvLogLevel, String s, int i, String s1, String s2) {
        String log = "<" + s + ":" + i + "::" + s1 + "> " + s2;
        switch (mmkvLogLevel) {
            case LevelDebug:
                Timber.d("【redirect logging MMKV】%s", log);
                break;
            case LevelInfo:
                Timber.i("【redirect logging MMKV】%s", log);
                break;
            case LevelWarning:
                Timber.w("【redirect logging MMKV】%s", log);
                break;
            case LevelError:
            case LevelNone:
                Timber.e("【redirect logging MMKV】%s", log);
                break;
            default:
                break;
        }
    }

    @Override
    public void onContentChangedByOuterProcess(String s) {
        Timber.i("【content changed】%s", s);
    }
}
