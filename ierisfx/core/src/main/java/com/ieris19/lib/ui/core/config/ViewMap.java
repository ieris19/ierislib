package com.ieris19.lib.ui.core.config;

import com.ieris19.lib.ui.core.control.View;
import com.ieris19.lib.ui.core.control.ViewManager;

import java.util.Collection;
import java.util.HashMap;

/**
 * A map of all the views in the application
 */
public class ViewMap {
    /**
     * An absolute map of the views included in the program running, it will be the source of truth for the
     * {@link ViewManager ViewManager}
     */
    private final HashMap<String, View> views;

    /**
     * A package-private constructor to allow only FxConfig to create instances
     * of this class.
     */
    ViewMap() {
        this.views = new HashMap<>();
    }

    /**
     * Fetches the {@link View UI Component} from the map
     *
     * @param key unique name identifier for the View
     * @return the corresponding UI Component
     * @throws IllegalArgumentException if the UI component is null
     */
    public synchronized View get(String key) {
        View view = views.get(key);
        if (view != null) {
            return view;
        } else {
            throw new IllegalArgumentException("View does not exist");
        }
    }

    /**
     * Adds a new {@link View UI Component} to the map
     *
     * @param key  unique name identifier for the View
     * @param view the UI Component to add to the map
     */
    public synchronized void add(View view) {
        String viewId = view.getId();
        if (views.get(viewId) != null) {
            throw new IllegalArgumentException("View ID is not unique, it already exists");
        }
        views.put(viewId, view);
    }

    public synchronized void add(Collection<View> collection) {
        collection.forEach(this::add);
    }
}
