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

import com.ieris19.lib.ui.core.control.View;
import com.ieris19.lib.ui.mvvm.Model;
import com.ieris19.lib.ui.mvvm.ViewController;
import com.ieris19.lib.ui.mvvm.ViewModel;

/**
 * The IerisLib User Interface module. <br> <br>
 * <p>
 * This module contains the user interface framework for IerisLib. This framework is referred to as IerisFX and is a
 * JavaFX based framework. <br> <br>
 * <p>
 * The framework is designed to be extended by the application desiring. By instantiating the
 * {@link View View} users can create their own views. For this they need to
 * extend the {@link Model Model}, {@link ViewModel ViewModel} and
 * {@link ViewController ViewController} classes. The framework is designed to follow the MVVM
 * design pattern.
 */
module ierislib.ui {
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;

    requires ierislib.common;
    requires ierislib.files.config;
    requires ini4j;

    exports com.ieris19.lib.ui.core;
    exports com.ieris19.lib.ui.core.control;
    exports com.ieris19.lib.ui.fxml;
    exports com.ieris19.lib.ui.mvvm;
    exports com.ieris19.lib.ui.mvvm.defaults;
}