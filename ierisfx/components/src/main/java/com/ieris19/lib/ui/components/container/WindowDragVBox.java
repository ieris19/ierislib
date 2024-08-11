package com.ieris19.lib.ui.components.container;

import javafx.scene.layout.VBox;

public class WindowDragVBox extends VBox {
    private double xOffset = 0;
    private double yOffset = 0;

    public WindowDragVBox() {
        this.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        this.setOnMouseDragged(event -> {
            this.getScene().getWindow().setX(event.getScreenX() - xOffset);
            this.getScene().getWindow().setY(event.getScreenY() - yOffset);
        });
    }
}
