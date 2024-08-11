package com.ieris19.lib.ui.components.control.window;

import javafx.scene.control.Button;

public class WindowHideButton extends Button {
    public WindowHideButton() {
        super("–");
        this.getStyleClass().add("window-hide-button");
        this.setOnAction(event -> {
            this.getScene().getWindow().hide();
        });
    }
}
