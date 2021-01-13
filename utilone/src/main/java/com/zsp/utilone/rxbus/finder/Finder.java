package com.zsp.utilone.rxbus.finder;

import com.zsp.utilone.rxbus.entity.EventType;
import com.zsp.utilone.rxbus.entity.ProducerBaseEvent;
import com.zsp.utilone.rxbus.entity.SubscriberBaseEvent;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;

/**
 * @decs: Finder
 * Finds producer and subscriber methods.
 * @author: 郑少鹏
 * @date: 2019/8/28 11:16
 */
public interface Finder {
    Finder ANNOTATED = new Finder() {
        @Override
        public @NotNull Map<EventType, ProducerBaseEvent> findAllProducers(Object listener) {
            return AnnotatedFinder.findAllProducers(listener);
        }

        @Override
        public @NotNull Map<EventType, Set<SubscriberBaseEvent>> findAllSubscribers(Object listener) {
            return AnnotatedFinder.findAllSubscribers(listener);
        }
    };

    /**
     * Find all producers.
     *
     * @param listener Object
     * @return Map<EventType, ProducerBaseEvent>
     */
    Map<EventType, ProducerBaseEvent> findAllProducers(Object listener);

    /**
     * Find all subscribers.
     *
     * @param listener Object
     * @return Map<EventType, Set < SubscriberBaseEvent>>
     */
    Map<EventType, Set<SubscriberBaseEvent>> findAllSubscribers(Object listener);
}
