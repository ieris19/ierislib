package com.ieris19.lib.ui.mvvm.defaults;

import com.ieris19.lib.ui.mvvm.ViewController;
import com.ieris19.lib.ui.mvvm.ViewModel;
import javafx.beans.property.Property;

/**
 * Empty controller that can be used when a view does not need a controller
 */
public class EmptyController extends ViewController {
    /**
     * Constructs an empty controller that can be used when a view does not need a controller
     */
    public EmptyController() {
    }

    /**
     * Returns the view to the initial state, regardless of the current state.
     * <p>
     * As a view without a controller is not interactive, this method does nothing.
     * </p>
     */
    @Override
    public void reset() {
    }

    /**
     * This method should be called loading the view, after the {@link ViewModel} is set. It should call
     * {@link ViewModel#bind(String, Property)} for every property that needs to be bound.
     * <p>
     * As a view without a controller is not interactive, this method does nothing.
     * </p>
     */
    @Override
    public void setBindings() {
    }
}