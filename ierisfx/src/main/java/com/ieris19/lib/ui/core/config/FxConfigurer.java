package com.ieris19.lib.ui.core.config;

import com.ieris19.lib.files.config.api.ConfigFactory;
import com.ieris19.lib.files.config.api.ConfigFormat;
import com.ieris19.lib.files.config.api.ConfigManager;
import com.ieris19.lib.ui.core.control.View;
import javafx.stage.StageStyle;

import java.util.Collection;

// TODO: Document this class
/**
 * This class represents the settings required by IerisFx
 *
 */
public class FxConfigurer {
    private static final String SETTINGS_KEY = "ierisfx";
    private static final String EXTRAS_KEY = "ierisfx.extras";
    private static final FxConfiguration DEFAULTS;

    static {
        DEFAULTS = new FxConfiguration(
                "ierisFx Application",
                "",
                "main",
                false,
                false,
                false,
                StageStyle.DECORATED,
                new ViewMap()
        );
    }

    public static FxConfiguration getDefaults() {
        return DEFAULTS;
    }

    private String title;
    private String icon;
    private String mainView;
    private boolean resizable;
    private boolean fullscreen;
    private boolean alwaysOnTop;
    private StageStyle windowStyle;

    private final ViewMap map;
    private final ConfigManager settings;

    public FxConfigurer() {
        this.title = DEFAULTS.title();
        this.icon = DEFAULTS.icon();
        this.mainView = DEFAULTS.mainView();
        this.resizable = DEFAULTS.resizable();
        this.fullscreen = DEFAULTS.fullScreen();
        this.alwaysOnTop = DEFAULTS.alwaysOnTop();
        this.windowStyle = DEFAULTS.windowStyle();

        this.map = new ViewMap();
        this.settings = null;
    }

    public FxConfigurer(ConfigManager manager) {
        this.title = manager.getProperty(SETTINGS_KEY+"/title").orElse(DEFAULTS.title());
        this.icon = manager.getProperty(SETTINGS_KEY+"/main_view").orElse(DEFAULTS.mainView());
        this.mainView = manager.getProperty(SETTINGS_KEY+"/icon_path").orElse(DEFAULTS.icon());
        this.resizable = manager.getBooleanProperty(SETTINGS_KEY+"/resizable").orElse(DEFAULTS.resizable());
        this.fullscreen = manager.getBooleanProperty(EXTRAS_KEY+"/fullscreen").orElse(DEFAULTS.fullScreen());
        this.alwaysOnTop = manager.getBooleanProperty(EXTRAS_KEY+"/always_on_top").orElse(DEFAULTS.alwaysOnTop());
        this.windowStyle = manager.getProperty(EXTRAS_KEY+"/window_style").map(StageStyle::valueOf).orElse(DEFAULTS.windowStyle());

        this.map = new ViewMap();
        this.settings = manager;
    }

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

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    public boolean isAlwaysOnTop() {
        return alwaysOnTop;
    }

    public void setAlwaysOnTop(boolean alwaysOnTop) {
        this.alwaysOnTop = alwaysOnTop;
    }

    public StageStyle getWindowStyle() {
        return windowStyle;
    }

    public void setWindowStyle(StageStyle windowStyle) {
        this.windowStyle = windowStyle;
    }

    ViewMap getViewMap() {
        return map;
    }

    public FxConfiguration freezeData() {
        return FxConfiguration.of(this);
    }
}
