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

package com.ieris19.lib.ui.core;

import com.ieris19.lib.files.assets.AssetHandler;
import com.ieris19.lib.files.config.FileProperties;
import com.ieris19.lib.files.config.PropertyTypeException;
import com.ieris19.lib.ui.mvvm.ViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Manager for all the views in the application. This class is responsible for loading the view from the FXML and
 * displaying it.
 */
public class ViewManager {
    /**
     * The name of the application being run
     */
    private final String name;
    /**
     * The window that the application is running on
     */
    private Stage stage;
    /**
     * Current view being displayed
     */
    private Scene currentScene;
    /**
     * Properties file for the application
     */
    private final FileProperties uiProperties;
    /**
     * Logs the events that occur in the application
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Creates a new ViewManager to handle the views in the application
     */
    public ViewManager() {
        log.debug("Initializing ViewManager");
        log.trace("Loading application properties");
        this.uiProperties = FileProperties.getInstance("ierisfx");
        try {
            this.name = uiProperties.getProperty("name");
            log.trace("Application name: " + name);
        } catch (IllegalArgumentException e) {
            throw defaultProperties();
        }
    }

    /**
     * Starts the view manager and displays the first view
     *
     * @param stage the window that the application is running on
     */
    public void start(Stage stage) {
        this.stage = stage;
        this.currentScene = new Scene(new Region());
        setIcon();
        log.trace("Trying to load the view");
        try {
            openView(uiProperties.getProperty("default-view"));
        } catch (IllegalArgumentException e) {
            throw defaultProperties();
        }
    }

    /**
     * Opens a view in the application and displays it
     *
     * @param viewId the id of the view to open
     */
    public void openView(String viewId) {
        showScene(loadView(viewId));
        log.debug("Successfully opened view: " + viewId);
    }

    /**
     * Loads a view from the {@link UIComponent UI Component} {@link ViewMap map} and returns the root node from the FXML
     * file
     *
     * @param viewId the id of the view to load
     * @return the view that was loaded
     */
    private Region loadView(String viewId) {
        UIComponent view = ViewMap.get(viewId);
        ViewController controller = view.getController();
        if (controller.getRoot() == null)
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(view.getFXML());
                loader.setController(controller);
                Region root = loader.load();
                controller.init(this, view, root);
                log.debug("Correctly loaded view: " + viewId);
            } catch (Exception e) {
                log.error("Cannot load view", e);
                throw new IllegalStateException("View couldn't be loaded", e);
            }
        controller.reset();
        return controller.getRoot();
    }

    /**
     * Displays a view in the application
     *
     * @param root the root element of the view to display. This is the parent of all view elements
     */
    protected void showScene(Region root) {
        currentScene.setRoot(root);
        String title = name;
        if (root.getUserData() != null) {
            title += " - " + root.getUserData();
        }
        stage.setTitle(title);
        log.debug("Window title set to: " + title);
        stage.sizeToScene();
        try {
            stage.setResizable(uiProperties.getPropertyBoolean("resizable"));
        } catch (IllegalArgumentException | PropertyTypeException e) {
            defaultProperties();
            stage.setResizable(true);
        }
        stage.setScene(currentScene);
        stage.show();
        stage.centerOnScreen();
    }

    /**
     * Sets the icon of the application
     */
    public void setIcon() {
        log.debug("Obtaining icon from file");
        InputStream iconStream = null;
        try {
            iconStream = AssetHandler.getInstance("images").getAssetAsStream(uiProperties.getProperty("icon"));
        } catch (IOException | IllegalArgumentException e) {
            log.error("Could not load icon because {}", e.getMessage() != null ? e.getMessage() : "of " + e.getClass());
            defaultProperties();
            return;
        }
        if (iconStream != null) {
            try {
                stage.getIcons().setAll(new Image(iconStream));
                iconStream.close();
            } catch (IOException e) {
                log.error("IOException while setting icon");
            } catch (NullPointerException e) {
                log.error("Icon doesn't exit or is null");
            }
        }
    }

    /**
     * Creates a default set of properties needed for IerisFX to run, and saves them to the properties file. It will skip
     * any properties that are already set
     */
    private IllegalStateException defaultProperties() {
        log.error("A necessary property cannot found in app.properties");
        log.debug("Setting default properties");
        uiProperties.createDefaultProperties(
                new String[][]{{"name", ""}, {"default-view", ""}, {"resizable", ""}, {"icon", ""}});
        log.debug("Default properties set");
        return new IllegalStateException(
                "The properties are not set up correctly. Please check the log for more information.");
    }
}
