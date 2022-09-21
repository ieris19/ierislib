package lib.ieris19.util.ui.mvvm;

/**
 * This interface represents a model. A model is responsible for handling all operations that are not directly related
 * to the user interface. This includes all business logic and data management. The model should be as independent as
 * possible from the view and the view model. <br>
 * This interface is empty because it is only used as a marker interface. All concrete models should extend this
 * interface.<br> <br>
 *
 * This interface usually represents a remote object in a different process. Because of this, it is recommended to use
 * interfaces as models. This allows the view model to be unaware of the implementation of the model, and allows the
 * model to be implemented in a different process.
 */
public interface Model extends Cloneable {}
