package com.zsp.utilone.rxbus.thread;

import android.os.Looper;

import com.zsp.utilone.rxbus.Bus;

/**
 * @decs: ThreadEnforcer
 * Enforces a thread confinement policy for methods on a particular event bus.
 * @author: 郑少鹏
 * @date: 2019/8/28 11:19
 */
public interface ThreadEnforcer {
    /**
     * A {@link ThreadEnforcer} that does no verification.
     */
    ThreadEnforcer ANY = bus -> {
        // Allow any thread.
    };
    /**
     * A {@link ThreadEnforcer} that confines {@link Bus} methods to the main thread.
     */
    ThreadEnforcer MAIN = bus -> {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("Event bus " + bus + " accessed from non-main thread " + Looper.myLooper());
        }
    };

    /**
     * Enforce a valid thread for the given {@code bus}.
     * Implementations may throw any runtime exception.
     *
     * @param bus Event bus instance on which an action is being performed.
     */
    void enforce(Bus bus);
}
