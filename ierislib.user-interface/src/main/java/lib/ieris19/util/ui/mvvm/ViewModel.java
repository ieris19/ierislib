package lib.ieris19.util.ui.mvvm;

import javafx.beans.property.Property;

/**
 * This interface represents a view model. A view model is responsible for linking the view and the model. It observes
 * the view and forwards events to the model.
 */
public abstract class ViewModel {
	/**
	 * The model of the business logic of the view. This model should handle all operations that are not directly related
	 * to the view.
	 *
	 * @param model the reference to the model. It should be cast and stored to a backing field of the more specific model
	 *              interface used by the specific view at hand.
	 */
	public void setModel(Model model) {
	}

	/**
	 * Links a field in the view to a value in the view model. This method is used to bind a field in the view to
	 * corresponding value in the view model.
	 * <p>
	 * The implementation of this method should be as follows:
	 * <pre>
	 *   {@code
	 *     switch (name) {
	 *       case PropertyName -> {
	 *       // If the property needs to be updated by view model:
	 *         this.correspondingProperty.bindBidirectional((Property<CorrectPropertyType) property);
	 *       //If the property needs only to be read by view:
	 *         this.correspondingProperty.bind((Property<CorrectPropertyType) property);
	 *       }
	 *       case OtherPropertyName -> {
	 *       // ...
	 *     }
	 *   }
	 *   }
	 *   </pre>
	 * </p>
	 *
	 * @param name     the name of the property being linked
	 * @param property the property being linked
	 */
	public abstract void bind(String name, Property<?> property);
}
