package com.ieris19.lib.utils.observable;

/**
 * Interface for observable subjects.
 */
public interface ObservableSubject {
    /**
     * Adds a listener to the list of listeners
     *
     * @param listener the listener to add
     */
    void addListener(ObservableListener listener);

    /**
     * Removes a listener from the list of listeners
     *
     * @param listener the listener to remove
     */
    void removeListener(ObservableListener listener);
}
