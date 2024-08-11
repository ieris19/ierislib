package com.ieris19.lib.utils.observable;

/**
 * Interface for observable listeners. The listener is notified when the observable subject changes.
 * @param <V> the type of the value that is observed
 */
public interface ObservableListener<V> {
    void update(String propertyName, V newValue);
}
