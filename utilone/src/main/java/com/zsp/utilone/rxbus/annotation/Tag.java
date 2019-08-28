package com.zsp.utilone.rxbus.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @decs: Tag
 * Marks the tags for a subscriber, as used by {@link com.zsp.utilone.rxbus.finder.AnnotatedFinder} and {@link com.zsp.utilone.rxbus.Bus}.
 * <p>
 * The tag's default value is {@code Tag.DEFAULT}.
 * If this annotation is applied to subscriber with none parameter or more than one parameter, Bus will delivery the events(tag and method's first (and only) parameter).
 * @author: 郑少鹏
 * @date: 2019/8/28 10:58
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Tag {
    String DEFAULT = "rxbus_default_tag";

    String value() default DEFAULT;
}
