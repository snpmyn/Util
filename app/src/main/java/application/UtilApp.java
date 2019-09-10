package application;

import android.app.Application;

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
     * 初始化配置
     */
    private void initConfiguration() {
        // timber
        TimberInitConfigure.initTimber(BuildConfig.DEBUG);
        // MMKV
        MmkvInitConfigure.initMmkv(this, BuildConfig.DEBUG, this, this);
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
