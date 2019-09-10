package application;

import android.app.Application;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.tencent.mmkv.MMKVContentChangeNotification;
import com.tencent.mmkv.MMKVHandler;
import com.tencent.mmkv.MMKVLogLevel;
import com.tencent.mmkv.MMKVRecoverStrategic;
import com.zsp.util.BuildConfig;
import com.zsp.utilone.storage.mmkv.MmkvInitConfigure;
import com.zsp.utilone.timber.configure.TimberInitConfigure;

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
     * 应用程序对象终止调
     * 不定调。应用程序被内核终止为别应用程序释放资源，将不提醒且不调应用程序对象onTerminate()而直接终止进程。
     */
    @Override
    public void onTerminate() {
        Timber.d("onTerminate");
        super.onTerminate();
    }

    /**
     * 系统资源匮乏调
     * 通于后台进程已结束且前台应用程序仍缺内存时调，重写该法清缓存或释放非必要资源。
     */
    @Override
    public void onLowMemory() {
        Timber.d("onLowMemory");
        super.onLowMemory();
    }

    /**
     * 运行时决定当前应用程序应减内存开销时（通进后台运行）调，含一level参数提供请求上下文。
     *
     * @param level 级别
     */
    @Override
    public void onTrimMemory(int level) {
        Timber.d("onTrimMemory");
        super.onTrimMemory(level);
    }

    /**
     * 与Activity不同，配置变时应用程序对象不终止和重启。应用程序用值依赖特定配置则重写该法加载这些值或于应用程序级处理配置值改变。
     *
     * @param newConfig 配置
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        Timber.d("onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 初始化配置
     */
    private void initConfiguration() {
        // timber
        TimberInitConfigure.initTimber(BuildConfig.DEBUG);
        // MMKV
        MmkvInitConfigure.initMmkv(this, MMKVLogLevel.LevelInfo, this, this);
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
    public void mmkvLog(MMKVLogLevel mmkvLogLevel, String s, int i, String s1, String s2) {
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
