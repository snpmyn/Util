package com.zsp.utilone.rxbus.entity;

import com.zsp.utilone.rxbus.thread.EventThread;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import rx.Observable;

/**
 * @decs: ProducerBaseEvent
 * Wraps a 'producer' method on a specific object.
 * <p>
 * This class only verifies the suitability of the method and event type if something fails.
 * Callers are expected to verify their uses of this class.
 * @author: 郑少鹏
 * @date: 2019/8/28 11:03
 */
public class ProducerBaseEvent extends BaseEvent {
    /**
     * object sporting the producer method
     */
    private final Object target;
    /**
     * producer method
     */
    private final Method method;
    /**
     * producer thread
     */
    private final EventThread thread;
    /**
     * object hash code
     */
    private final int hashCode;
    /**
     * should this producer produce events
     */
    private boolean valid = true;

    public ProducerBaseEvent(Object target, Method method, EventThread thread) {
        if (target == null) {
            throw new NullPointerException("EventProducer target cannot be null.");
        }
        if (method == null) {
            throw new NullPointerException("EventProducer method cannot be null.");
        }
        this.target = target;
        this.thread = thread;
        this.method = method;
        method.setAccessible(true);
        // Compute hash code eagerly since we know it will be used frequently and we cannot estimate the runtime of the target's hashCode call.
        final int prime = 31;
        hashCode = (prime + method.hashCode()) * prime + target.hashCode();
    }

    public boolean isValid() {
        return valid;
    }

    /**
     * If invalidated, will subsequently refuse to produce events.
     * <p/>
     * Should be called when the wrapped object is unregistered from the Bus.
     */
    public void invalidate() {
        valid = false;
    }

    /**
     * Invokes the wrapped producer method and produce a {@link Observable}.
     */
    public Observable produce() {
        return Observable.unsafeCreate(subscriber -> {
            try {
                subscriber.onNext(produceEvent());
                subscriber.onCompleted();
            } catch (InvocationTargetException e) {
                throwRuntimeException("Producer " + ProducerBaseEvent.this + " threw an exception.", e);
            }
        }).subscribeOn(EventThread.getScheduler(thread));
    }

    /**
     * Invokes the wrapped producer method.
     *
     * @throws IllegalStateException     If previously invalidated.
     * @throws InvocationTargetException If the wrapped method throws any {@link Throwable} that is not an {@link Error} ({@code Error}s are propagated as-is).
     */
    private Object produceEvent() throws InvocationTargetException {
        if (!valid) {
            throw new IllegalStateException(toString() + " has been invalidated and can no longer produce events.");
        }
        try {
            return method.invoke(target);
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
        return "[EventProducer " + method + "]";
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
        final ProducerBaseEvent other = (ProducerBaseEvent) obj;
        return method.equals(other.method) && target == other.target;
    }

    public Object getTarget() {
        return target;
    }
}
