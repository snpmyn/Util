package com.zsp.utilone.timber.debugtree;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import timber.log.Timber;

/**
 * Created on 2019/7/28.
 *
 * @author 郑少鹏
 * @desc ThreadAwareDebugTree
 */
public class ThreadAwareDebugTree extends Timber.DebugTree {
    /**
     * Extract the tag which should be used for the message from the {@code element}. By default
     * this will use the class name without any anonymous class suffixes (e.g., {@code Foo$1}
     * becomes {@code Foo}).
     * <p>
     * Note: This will not be called if a {@link android.util.Log #tag(String) manual tag} was specified.
     *
     * @param element StackTraceElement
     */
    @Override
    protected @Nullable String createStackElementTag(@NotNull StackTraceElement element) {
        return super.createStackElementTag(element) + "->" + element.getMethodName() +
                "(" + element.getFileName() + ":" + +element.getLineNumber() + ")";
    }

    /**
     * Break up {@code message} into maximum-length chunks (if needed) and send to either
     * {@link android.util.Log#println(int, String, String) Log.println()} or
     * {@link android.util.Log#wtf(String, String) Log.wtf()} for logging.
     * <p>
     * {@inheritDoc}
     *
     * @param priority 优先级
     * @param tag      标志
     * @param message  消息
     * @param t        抛出
     */
    @Override
    protected void log(int priority, String tag, @NotNull String message, Throwable t) {
        if (tag != null) {
            String currentThreadName = Thread.currentThread().getName();
            tag = "<" + currentThreadName + "> " + tag;
        }
        super.log(priority, tag, message, t);
    }
}
