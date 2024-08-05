package com.ieris19.lib.ui.core.control;

import com.ieris19.lib.ui.core.config.FxConfiguration;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Manager for all the views in the application. This class is responsible for loading the view from the FXML and
 * displaying it.
 */
public class ViewManager {
    private final FxConfiguration settings;
    /**
     * Logs the events that occur in the application
     */
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * The window that the application is running on
     */
    private Stage stage;
    /**
     * Current view being displayed
     */
    private Scene currentScene;

    /**
     * Creates a new ViewManager to handle the views in the application
     */
    public ViewManager(FxConfiguration settings) {
        log.debug("Initializing ViewManager");
        this.settings = settings;
        log.debug("ViewManager initialized");
    }

    public File openFileDialog(FileChooser fileSelector) {
        if (this.stage == null) {
            throw new IllegalStateException("Application is not running");
        }
        return fileSelector.showOpenDialog(this.stage);
    }

    /**
     * Starts the view manager and displays the first view
     *
     * @param stage the window that the application is running on
     */
    public void start(Stage stage) {
        this.stage = stage;
        configureStage();
        this.currentScene = new Scene(new Region());
        log.trace("Trying to load the view");
        openView(settings.mainView());
        this.stage.centerOnScreen();
    }

    /**
     * Configures the stage properties
     */
    public void configureStage() {
        if (settings.windowStyle() != StageStyle.DECORATED) {
            this.stage.initStyle(settings.windowStyle());
        }
        this.stage.setFullScreen(settings.fullScreen());
        this.stage.setAlwaysOnTop(settings.alwaysOnTop());
        setIcon();
    }

    /**
     * Opens a view in the application and displays it
     *
     * @param viewId the id of the view to open
     */
    public void openView(String viewId) {
        View view = settings.getViews(viewId);
        showScene(view);
        log.debug("Successfully opened view: {}", viewId);
    }

    /**
     * Displays a view in the application
     *
     * @param view the view to display
     */
    protected void showScene(View view) {
        Region root = view.getLoader().loadView(this);
        log.debug("Correctly loaded view: {}", view.getId());
        this.currentScene.setRoot(root);
        String title = settings.title();
        if (view.getTitle() != null && !view.getTitle().isEmpty()) {
            title += title.isEmpty() ? view.getTitle() : " - " + view.getTitle();
        }
        this.stage.setTitle(title);
        log.debug("Window title set to: {}", title);
        this.stage.sizeToScene();
        this.stage.setResizable(settings.resizable());
        this.stage.setScene(this.currentScene);
        if (root.getMinHeight() > 0) {
            this.stage.setMinHeight(root.getMinHeight());
        }
        if (root.getMinWidth() > 0) {
            this.stage.setMinWidth(root.getMinWidth());
        }
        this.stage.show();
    }

    /**
     * Sets the icon of the application
     */
    public void setIcon() {
        log.debug("Obtaining icon from file");
        InputStream iconStream = null;
        try {
            iconStream = Thread.currentThread().getStackTrace()[2].getClass().getResourceAsStream(settings.icon());
        } catch (Exception e) {
            log.error("Cannot load icon", e);
        }
        if (iconStream != null) {
            try {
                this.stage.getIcons().setAll(new Image(iconStream));
                iconStream.close();
            } catch (IOException e) {
                log.error("IOException while setting icon");
            } catch (NullPointerException e) {
                log.error("Icon doesn't exit or is null");
            }
        }
    }
}
