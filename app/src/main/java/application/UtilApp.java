package application;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
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
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        TimberInitConfigure.initTimber();
    }
}
