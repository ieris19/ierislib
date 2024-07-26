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

import com.ieris19.lib.common.Script;
import com.ieris19.lib.files.config.ConfigFactory;
import com.ieris19.lib.files.config.ConfigManager;
import com.ieris19.lib.ui.core.config.FxConfig;
import com.ieris19.lib.ui.core.control.View;
import javafx.application.Application;

import java.net.URI;
import java.util.Collection;

import static com.ieris19.lib.files.config.ConfigFactory.*;

public class IerisFxBuilder {
    private final FxConfig settings;

    public IerisFxBuilder() {
        this.settings = new FxConfig();
    }

    public IerisFxBuilder setInit(Script initScript) {
        IerisFX.setInit(initScript);
        return this;
    }

    public IerisFxBuilder setStop(Script stopScript) {
        IerisFX.setStop(stopScript);
        return this;
    }

    public IerisFxBuilder setStart(Script startScript) {
        IerisFX.setStart(startScript);
        return this;
    }

    public IerisFxBuilder loadSettings(URI settingsPath) {
        ConfigManager manager = ConfigFactory.getConfig(settingsPath, ConfigFormat.INI);
        this.settings.setTitle(manager.getProperty("ierisfx/title").orElse(settings.getTitle()));
        this.settings.setMainView(manager.getProperty("ierisfx/main_view").orElse(settings.getMainView()));
        this.settings.setIcon(manager.getProperty("ierisfx/icon_path").orElse(settings.getIcon()));
        this.settings.setResizable(manager.getBooleanProperty("ierisfx/resizable").orElse(settings.isResizable()));
        return this;
    }

    public IerisFxBuilder setResizable(boolean isResizable) {
        this.settings.setResizable(isResizable);
        return this;
    }

    public IerisFxBuilder setTitle(String title) {
        this.settings.setTitle(title);
        return this;
    }

    public IerisFxBuilder setMainView(String viewId) {
        this.settings.setTitle(viewId);
        return this;
    }

    public IerisFxBuilder setIcon(String icon) {
        this.settings.setIcon(icon);
        return this;
    }

    public IerisFxBuilder addViews(Collection<View> views) {
        this.settings.addViews(views);
        return this;
    }

    public IerisFxBuilder addView(View view) {
        this.settings.addView(view);
        return this;
    }

    public void launch() {
        IerisFX.setConfig(this.settings);
        Application.launch(IerisFX.class);
    }
}
