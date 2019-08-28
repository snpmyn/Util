package com.zsp.utilone.rxbus.entity;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * @decs: BaseEvent
 * @author: 郑少鹏
 * @date: 2019/8/28 11:00
 */
abstract class BaseEvent {
    /**
     * Throw a {@link RuntimeException} with given message and cause lifted from an {@link InvocationTargetException}.
     * If the specified {@link InvocationTargetException} does not have a cause, neither will the {@link RuntimeException}.
     *
     * @param msg String
     * @param e   InvocationTargetException
     */
    void throwRuntimeException(String msg, InvocationTargetException e) {
        throwRuntimeException(msg, Objects.requireNonNull(e.getCause(), "e.getCause() must not be null"));
    }

    /**
     * Throw a {@link RuntimeException} with given message and cause lifted from an {@link InvocationTargetException}.
     * If the specified {@link InvocationTargetException} does not have a cause, neither will the {@link RuntimeException}.
     *
     * @param msg String
     * @param e   Throwable
     */
    private void throwRuntimeException(String msg, Throwable e) {
        Throwable cause = e.getCause();
        if (cause != null) {
            throw new RuntimeException(msg + ": " + cause.getMessage(), cause);
        } else {
            throw new RuntimeException(msg + ": " + e.getMessage(), e);
        }
    }
}