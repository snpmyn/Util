package com.zsp.utilone.rxbus.entity;

import org.jetbrains.annotations.NotNull;

/**
 * @decs: EventType
 * @author: 郑少鹏
 * @date: 2019/8/28 11:02
 */
public class EventType {
    /**
     * baseEvent tag
     */
    private final String tag;
    /**
     * baseEvent clazz
     */
    private final Class<?> clazz;
    /**
     * object hash code
     */
    private final int hashCode;

    public EventType(String tag, Class<?> clazz) {
        if (tag == null) {
            throw new NullPointerException("EventType Tag cannot be null.");
        }
        if (clazz == null) {
            throw new NullPointerException("EventType Clazz cannot be null.");
        }
        this.tag = tag;
        // hook by getRealClass method, for kotlin
        this.clazz = getRealClass(clazz);
        // Compute hash code eagerly since we know it will be used frequently and we cannot estimate the runtime of the target's hashCode call.
        final int prime = 31;
        hashCode = (prime + tag.hashCode()) * prime + clazz.hashCode();
    }

    @NotNull
    @Override
    public String toString() {
        return "[EventType " + tag + " && " + clazz + "]";
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EventType other = (EventType) obj;
        return tag.equals(other.tag) && clazz == other.clazz;
    }

    /**
     * GetRealClass, compat to kotlin base type.
     *
     * @param cls Class<?>
     * @return Class<?>
     */
    private Class<?> getRealClass(@NotNull Class<?> cls) {
        String clsName = cls.getName();
        if (int.class.getName().equals(clsName)) {
            cls = Integer.class;
        } else if (double.class.getName().equals(clsName)) {
            cls = Double.class;
        } else if (float.class.getName().equals(clsName)) {
            cls = Float.class;
        } else if (long.class.getName().equals(clsName)) {
            cls = Long.class;
        } else if (byte.class.getName().equals(clsName)) {
            cls = Byte.class;
        } else if (short.class.getName().equals(clsName)) {
            cls = Short.class;
        } else if (boolean.class.getName().equals(clsName)) {
            cls = Boolean.class;
        } else if (char.class.getName().equals(clsName)) {
            cls = Character.class;
        }
        return cls;
    }
}