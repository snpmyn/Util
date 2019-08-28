package com.zsp.utilone.rxbus.entity;

import com.zsp.utilone.rxbus.thread.EventThread;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * @decs: SubscriberBaseEvent
 * Wraps a single-argument 'subscriber' method on a specific object.
 * <p>
 * his class only verifies the suitability of the method and event type if something fails.
 * Callers are expected to verify their uses of this class.
 * <p>
 * Two SubscriberBaseEvent are equivalent when they refer to the same method on the same object (not class).
 * This property is used to ensure that no subscriber method is registered more than once.
 * @author: 郑少鹏
 * @date: 2019/8/28 11:06
 */
public class SubscriberBaseEvent extends BaseEvent {
    /**
     * object sporting the method
     */
    private final Object target;
    /**
     * subscriber method
     */
    private final Method method;
    /**
     * subscriber thread
     */
    private final EventThread thread;
    /**
     * object hash code
     */
    private final int hashCode;
    /**
     * RxJava {@link Subject}
     */
    private Subject subject;
    /**
     * should this Subscriber receive events
     */
    private boolean valid = true;

    public SubscriberBaseEvent(Object target, Method method, EventThread thread) {
        if (target == null) {
            throw new NullPointerException("SubscriberBaseEvent target cannot be null.");
        }
        if (method == null) {
            throw new NullPointerException("SubscriberBaseEvent method cannot be null.");
        }
        if (thread == null) {
            throw new NullPointerException("SubscriberBaseEvent thread cannot be null.");
        }
        this.target = target;
        this.method = method;
        this.thread = thread;
        method.setAccessible(true);
        initObservable();
        // Compute hash code eagerly since we know it will be used frequently and we cannot estimate the runtime of the target's hashCode call.
        final int prime = 31;
        hashCode = (prime + method.hashCode()) * prime + target.hashCode();
    }

    private void initObservable() {
        subject = PublishSubject.create();
        subject.onBackpressureBuffer().observeOn(EventThread.getScheduler(thread)).subscribe((Action1<Object>) event -> {
            try {
                if (valid) {
                    handleEvent(event);
                }
            } catch (InvocationTargetException e) {
                throwRuntimeException("Could not dispatch event: " + event.getClass() + " to subscriber " + SubscriberBaseEvent.this, e);
            }
        });
    }

    public boolean isValid() {
        return valid;
    }

    /**
     * If invalidated, will subsequently refuse to handle events.
     * <p/>
     * Should be called when the wrapped object is unregistered from the Bus.
     */
    public void invalidate() {
        valid = false;
    }

    public void handle(Object event) {
        subject.onNext(event);
    }

    public Subject getSubject() {
        return subject;
    }

    /**
     * Invokes the wrapped subscriber method to handle {@code event}.
     *
     * @param event event to handle
     * @throws IllegalStateException     If previously invalidated.
     * @throws InvocationTargetException If the wrapped method throws any {@link Throwable} that is not an {@link Error} ({@code Error}s are propagated as-is).
     */
    private void handleEvent(Object event) throws InvocationTargetException {
        if (!valid) {
            throw new IllegalStateException(toString() + " has been invalidated and can no longer handle events.");
        }
        try {
            method.invoke(target, event);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof Error) {
                throw (Error) e.getCause();
            }
            throw e;
        }
    }

    @NotNull
    @Override
    public String toString() {
        return "[SubscriberBaseEvent " + method + "]";
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
        final SubscriberBaseEvent other = (SubscriberBaseEvent) obj;
        return method.equals(other.method) && target == other.target;
    }
}
