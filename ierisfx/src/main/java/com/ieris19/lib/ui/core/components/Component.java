/*
 * Copyright 2021 Ieris19
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

package com.ieris19.lib.ui.core.components;

/**
 * This class represents a user-interface component that is completely standalone, containing all
 * that is needed to run this specific Component.
 * This makes Components encapsulated, reusable and easy to manage.
 *
 * @param view       The file path to the FXML file of the Component
 * @param model      The business logic of the Component. This class is typically either a remote object in a different process or a
 *                   local object in the same process that is forwarded methods from the {@link ViewController} through the
 *                   {@link ViewModel}.
 * @param viewModel  The view model of the Component. This class is responsible for linking {@link Model} and
 *                   {@link ViewController view} observing the view and forwarding events to the model.
 * @param controller The view controller of the Component. This class is responsible for linking the user and
 *                   system. It deals with user interaction, it is responsible for updating the view, and being
 *                   observed by the {@link ViewModel}.
 */
public record Component(View view,
                        Model model,
                        ViewModel viewModel,
                        ViewController controller) {
    /**
     * Creates a new Component instance with the given parameters
     *
     * @param view       the rendering logic for the Component's View
     * @param model      the model of the Component
     * @param viewModel  the view model of the Component
     * @param controller the view controller of the Component
     */
    public Component(View view, Model model, ViewModel viewModel, ViewController controller) {
        this.view = view;
        this.controller = controller;
        this.viewModel = viewModel;
        this.model = model;
        this.viewModel.setModel(model);
    }
}
