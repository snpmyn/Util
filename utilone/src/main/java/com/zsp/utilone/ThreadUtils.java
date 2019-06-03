package com.zsp.utilone;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created on 2018/9/6.
 *
 * @author 郑少鹏
 * @desc ThreadUtils
 * 依赖org.apache.commons:commons-lang3:3.8
 */
public class ThreadUtils {
    public static ScheduledExecutorService stepScheduledExecutorService() {
        return new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
    }
}
