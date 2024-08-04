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

import com.ieris19.lib.files.config.api.ConfigFactory;
import com.ieris19.lib.files.config.api.ConfigFormat;
import com.ieris19.lib.files.config.api.ConfigManager;
import com.ieris19.lib.ui.core.control.View;

import java.util.Collection;
import java.util.Optional;

/**
 * This class represents the settings required by IerisFx
 */
public class FxConfig {
    private static final FxConfig DEFAULTS = new FxConfig("ierisFx Application", "", "main", false);

    public static FxConfig getDefaults() {
        return DEFAULTS;
    }

    private String title;
    private String icon;
    private String mainView;
    private boolean resizable;
    private ViewMap map;
    private ConfigManager settings;

    /**
     * Constructs an instance of FxConfig with all the provided values
     *
     * @param title the title to be displayed in the main stage
     * @param icon the icon path to be shown by the application
     * @param mainView the first view to load when starting the application
     * @param resizable whether the window will be resizable
     * @param settings the views to be added to the application
     */
    public FxConfig(String title, String icon, String mainView, boolean resizable, ConfigManager settings) {
        this.title = title;
        this.icon = icon;
        this.mainView = mainView;
        this.resizable = resizable;
        this.map = new ViewMap();
        this.settings = settings;
    }

    public FxConfig(String title, String icon, String mainView, boolean resizable) {
        this(title, icon, mainView, resizable, ConfigFactory.getConfig(title, ConfigFormat.MEMORY));
    }

    public FxConfig(ConfigManager manager) {
        this(
                manager.getProperty("ierisfx/title").orElse(DEFAULTS.title),
                manager.getProperty("ierisfx/main_view").orElse(DEFAULTS.mainView),
                manager.getProperty("ierisfx/icon_path").orElse(DEFAULTS.icon),
                manager.getBooleanProperty("ierisfx/resizable").orElse(DEFAULTS.resizable),
                manager
        );
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

    public ConfigManager getAdditionalSettings() {
        return settings;
    }
}
