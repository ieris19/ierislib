package com.ieris19.lib.ui.core;

import com.ieris19.lib.common.Script;
import com.ieris19.lib.files.config.api.ConfigFactory;
import com.ieris19.lib.files.config.api.ConfigFormat;
import com.ieris19.lib.files.config.api.ConfigManager;
import com.ieris19.lib.ui.core.config.FxConfigurer;
import com.ieris19.lib.ui.core.control.View;
import javafx.application.Application;

import java.net.URI;
import java.util.Collection;

public class IerisFxBuilder {
    private FxConfigurer settings;

    public IerisFxBuilder() {
        this.settings = new FxConfigurer();
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
        this.settings = new FxConfigurer(manager);
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
        IerisFX.setConfig(settings.freezeData());
        Application.launch(IerisFX.class);
    }
}
