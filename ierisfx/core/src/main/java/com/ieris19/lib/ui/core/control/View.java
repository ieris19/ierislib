package com.ieris19.lib.ui.core.control;

/**
 * This class represents a user-interface component that is completely standalone, containing all that is needed to run
 * this specific View. This makes UIComponents encapsulated, reusable and easy to manage.
 */
public class View {
    /**
     * The unique identifier of the view
     */
    private final String id;
    /**
     * The title of the view, this will be displayed in the title bar
     */
    private final String title;
    /**
     * The location of the FXML file that represents the view
     */
    private final ViewLoader loader;

    /**
     * Creates a new View instance with the given parameters
     *
     * @param fxmlName   the name of the FXML file
     * @param model      the model of the View
     * @param viewModel  the view model of the View
     * @param controller the view controller of the View
     */
    public View(String id, String title, ViewLoader loader) {
        this.id = id;
        this.title = title;
        this.loader = loader;
    }

    /**
     * Returns the unique identifier of the View
     *
     * @return the unique identifier of the View
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the title of the View
     *
     * @return the title of the View
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the location of the FXML file that represents the View
     */
    public ViewLoader getLoader() {
        return loader;
    }}
