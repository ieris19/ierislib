package com.ieris19.lib.ui.mvvm;

import javafx.beans.property.Property;
import javafx.collections.ObservableList;

/**
 * This interface represents a view model. A view model is responsible for linking the view and the model. It observes
 * the view and forwards events to the model.
 */
public interface ViewModel {
    /**
     * The model of the business logic of the view. This model should handle all operations that are not directly related
     * to the view.
     *
     * @param model the reference to the model. It should be cast and stored to a backing field of the more specific model
     *              interface used by the specific view at hand. It should NOT be stored as a concrete implementation of
     *              the model interface.
     */
    public abstract void setModel(Model model);

    /**
     * Links a field in the view to a value in the view model. This method is used to bind a field in the view to
     * corresponding value in the view model.
     * The implementation of this method should be as follows:
     * <pre>
     *   {@code
     *     switch (name) {
     *       case PropertyName -> {
     *       // If the property needs to be updated by view model:
     *         this.correspondingProperty.bindBidirectional((Property<CorrectPropertyType>) property);
     *       //If the property needs only to be read by view:
     *         this.correspondingProperty.bind((Property<CorrectPropertyType>) property);
     *       }
     *       case OtherPropertyName -> {
     *       // So on and so forth
     *     }
     *   }
     *   }
     *   </pre>
     *
     * @param name     the name of the property being linked
     * @param property the property being linked
     * @deprecated since 3.3.0, use {@link #bind(String, Property, Class)} instead
     */
    default void bind(String name, Property<?> property) {
        throw new UnsupportedOperationException("This method is deprecated by default. Use the #bind(String, Property, Class) method instead.");
    }

    default <C> void bind(String name, Property<? extends C> property, Class<C> type) {
        this.bind(name, property);
    }

    default <C> void syncLists(String name, ObservableList<? extends C> observable, Class<C> type) {}
}
