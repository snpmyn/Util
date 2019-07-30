package application;

import android.app.Application;

import com.zsp.util.BuildConfig;
import com.zsp.utilone.timber.configure.TimberInitConfigure;

/**
 * Created on 2019/7/28.
 *
 * @author 郑少鹏
 * @desc 应用
 */
public class UtilApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TimberInitConfigure.initTimber(BuildConfig.DEBUG);
    }
}
