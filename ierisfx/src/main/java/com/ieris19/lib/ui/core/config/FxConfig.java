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

package com.ieris19.lib.ui.core.config;

import com.ieris19.lib.ui.core.control.View;

import java.util.Collection;

/**
 * This class represents the settings required by IerisFx
 */
public class FxConfig {
    private String title;
    private String icon;
    private String mainView;
    private boolean resizable;
    private ViewMap map;

    /**
     * Constructs an instance of FxConfig with default values
     */
    public FxConfig() {
        this.title = "IerisFx Application";
        this.icon = "";
        this.mainView = "main";
        this.resizable = false;
        this.map = new ViewMap();
    }

    /**
     * Constructs an instance of FxConfig with all the provided values
     *
     * @param title the title to be displayed in the main stage
     * @param icon the icon path to be shown by the application
     * @param mainView the first view to load when starting the application
     * @param resizable whether the window will be resizable
     */
    public FxConfig(String title, String icon, String mainView, boolean resizable) {
        this.title = title;
        this.icon = icon;
        this.mainView = mainView;
        this.resizable = resizable;
        this.map = new ViewMap();
    }

    //TODO: Document Class
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMainView() {
        return mainView;
    }

    public void setMainView(String mainView) {
        this.mainView = mainView;
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    public View getViews(String key) {
        return map.get(key);
    }

    public void addViews(Collection<View> views) {
        map.add(views);
    }

    public void addView(View view) {
        map.add(view);
    }
}
