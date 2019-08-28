package com.zsp.utilone.rxbus.thread;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @decs: ThreadHandler
 * @author: 郑少鹏
 * @date: 2019/8/28 11:20
 */
public interface ThreadHandler {
    ThreadHandler DEFAULT = new ThreadHandler() {
        private Executor executor;
        private Handler handler;

        @Override
        public Executor getExecutor() {
            if (executor == null) {
                executor = Executors.newCachedThreadPool();
            }
            return executor;
        }

        @Override
        public Handler getHandler() {
            if (handler == null) {
                handler = new Handler(Looper.getMainLooper());
            }
            return handler;
        }
    };

    /**
     * Get executor.
     *
     * @return Executor
     */
    Executor getExecutor();

    /**
     * Get handler.
     *
     * @return Handler
     */
    Handler getHandler();
}
