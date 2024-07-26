/*
 * Copyright 2024 Ieris19
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.ieris19.lib.ui.core.control;

import com.ieris19.lib.ui.mvvm.Model;
import com.ieris19.lib.ui.mvvm.ViewController;
import com.ieris19.lib.ui.mvvm.ViewModel;

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
