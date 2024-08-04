package com.ieris19.lib.ui.core.control;

import com.ieris19.lib.ui.mvvm.Model;
import com.ieris19.lib.ui.mvvm.ViewController;
import com.ieris19.lib.ui.mvvm.ViewModel;
import javafx.scene.layout.Region;

public abstract class ViewLoader {
    /**
     * The business logic of the View. This class is typically either a remote object in a different process or a
     * local object in the same process that is forwarded methods from the {@link ViewController} through the
     * {@link ViewModel}.
     */
    protected final Model model;
    /**
     * The view model of the View. This class is responsible for linking {@link Model} and
     * {@link ViewController view} observing the view and forwarding events to the model.
     */
    protected final ViewModel viewModel;
    /**
     * The view controller of the View. This class is responsible for linking the user and system. It deals with
     * user interaction, it is responsible for updating the view, and being observed by the {@link ViewModel}.
     */
    protected final ViewController controller;

    public ViewLoader(ViewController controller, ViewModel viewModel, Model model) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.model = model;
        this.viewModel.setModel(model);
    }

    public abstract Region loadView(ViewManager view);

    /**
     * Returns the model of the View
     *
     * @return the model of the View
     */
    public Model getModel() {
        return model;
    }

    /**
     * Returns the view model of the View
     *
     * @return the view model of the View
     */
    public ViewModel getViewModel() {
        return viewModel;
    }

    /**
     * Returns the view controller of the View
     *
     * @return the view controller of the View
     */
    public ViewController getController() {
        return controller;
    }
}
