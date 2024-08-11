package com.ieris19.lib.ui.components.container;

import javafx.scene.layout.Pane;

public class WindowDragPane extends Pane {
    private double xOffset = 0;
    private double yOffset = 0;

    public WindowDragPane() {
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
