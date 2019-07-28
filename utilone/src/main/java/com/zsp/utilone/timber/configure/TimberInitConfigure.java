package com.zsp.utilone.timber.configure;

import com.zsp.utilone.timber.debugtree.ReleaseTree;
import com.zsp.utilone.timber.debugtree.ThreadAwareDebugTree;

import timber.log.Timber;

/**
 * Created on 2019/7/28.
 *
 * @author 郑少鹏
 * @desc timber初始化配置
 */
public class TimberInitConfigure {
    public static void initTimber(boolean debug) {
        if (debug) {
            Timber.plant(new ThreadAwareDebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }
    }
}
