package com.zsp.utilone.rxbus.annotation;

import com.zsp.utilone.rxbus.thread.EventThread;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @decs: Subscribe
 * Marks a method as an event subscriber, as used by {@link com.zsp.utilone.rxbus.finder.AnnotatedFinder} and {@link com.zsp.utilone.rxbus.Bus}.
 * <p>
 * The method's first (and only) parameter and tag defines the event type.
 * If this annotation is applied to methods with zero parameters or more than one parameter, the object containing the method will not be able to register for event delivery from the {@link com.zsp.utilone.rxbus.Bus}.
 * Bus fails fast by throwing runtime exceptions in these cases.
 * @author: 郑少鹏
 * @date: 2019/8/28 10:57
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Subscribe {
    Tag[] tags() default {};

    EventThread thread() default EventThread.MAIN_THREAD;
}