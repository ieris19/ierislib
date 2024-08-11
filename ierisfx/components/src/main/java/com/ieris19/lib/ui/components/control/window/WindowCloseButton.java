package com.ieris19.lib.ui.components.control.window;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

public class WindowCloseButton extends Button {
    public WindowCloseButton() {
        super("×");
        this.getStyleClass().add("window-close-button");
        this.setOnAction(event -> {
            Window window = this.getScene().getWindow();
            if (window instanceof Stage stage) {
                stage.close();
            }
        });
    }
}
