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

package com.ieris19.lib.ui.core;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class ErrorApplication extends Application {
    private static String title;
    private static String header;
    private static String message;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title != null ? title : "Fatal Error");
        alert.setHeaderText(header != null ? header : "A fatal error has occurred");
        alert.setContentText(message != null ? message : "The application will now exit");
        alert.showAndWait();
        Platform.exit();
    }

    public static void ShowErrorAndWait(String title, String header, String message) {
        ErrorApplication.title = title;
        ErrorApplication.header = header;
        ErrorApplication.message = message;
        Application.launch(ErrorApplication.class);
    }
}
