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

package com.ieris19.lib.ui.fxml;

import com.ieris19.lib.ui.core.control.View;
import com.ieris19.lib.ui.core.control.ViewLoader;
import com.ieris19.lib.ui.core.control.ViewManager;
import com.ieris19.lib.ui.core.config.ViewMap;
import com.ieris19.lib.ui.mvvm.Model;
import com.ieris19.lib.ui.mvvm.ViewController;
import com.ieris19.lib.ui.mvvm.ViewModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class FxmlView extends ViewLoader {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final URL location;


    public FxmlView(ViewController controller, ViewModel viewModel,
                    Model model, URL fxml) {
        super(controller, viewModel, model);
        this.location = fxml;
    }

    /**
     * Loads a view from the {@link View} {@link ViewMap map} and returns the root node from the FXML
     * file
     *
     * @param manager the {@link ViewManager} to manage the view
     * @return the root of the view that was loaded
     */
    public Region loadView(ViewManager manager) {
        if (this.controller.getRoot() == null) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(this.location);
                loader.setController(this.controller);
                Region root = loader.load();
                getController().init(manager, this.viewModel, root);
            } catch (Exception e) {
                log.error("Cannot load view", e);
                throw new IllegalStateException("View couldn't be loaded", e);
            }
        }
        this.controller.reset();
        return getController().getRoot();
    }
}
