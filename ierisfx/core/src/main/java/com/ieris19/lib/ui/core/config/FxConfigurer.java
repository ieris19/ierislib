package com.ieris19.lib.ui.core.config;

import com.ieris19.lib.files.config.api.ConfigManager;
import com.ieris19.lib.ui.core.control.View;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;

// TODO: Document this class
/**
 * This class represents the settings required by IerisFx
 *
 */
public class FxConfigurer {
    private static final String SETTINGS_KEY = "ierisfx";
    private static final String EXTRAS_KEY = "ierisfx.extras";
    private static final FxConfiguration DEFAULTS;
    private static final Logger log = LoggerFactory.getLogger(FxConfigurer.class);

    static {
        DEFAULTS = new FxConfiguration(
                "ierisFx Application",
                "",
                "main",
                false,
                false,
                false,
                StageStyle.DECORATED,
                Color.TRANSPARENT,
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
    private Color sceneFill;

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
        this.settings = manager;
        parseRequiredProperties(manager);
        this.fullscreen = manager.getBooleanProperty(EXTRAS_KEY+"/fullscreen").orElse(DEFAULTS.fullScreen());
        this.alwaysOnTop = manager.getBooleanProperty(EXTRAS_KEY+"/alwaysOnTop").orElse(DEFAULTS.alwaysOnTop());
        this.windowStyle = manager.getProperty(EXTRAS_KEY+"/windowStyle").map(FxConfigurer::parseStyle).orElse(DEFAULTS.windowStyle());
        this.sceneFill = manager.getProperty(EXTRAS_KEY+"/sceneFill").map(FxConfigurer::parseColor).orElse(DEFAULTS.sceneFill());
        this.map = new ViewMap();
    }

    private static StageStyle parseStyle(String style) {
        try {
            return StageStyle.valueOf(style.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static Color parseColor(String color) {
        try {
            return Color.web(color);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private void parseRequiredProperties(ConfigManager manager) {
        Optional<String> title = manager.getProperty(SETTINGS_KEY+"/title");
        if (title.isEmpty()) {
            log.warn("Title with a default value is deprecated and will be removed with release 4.0.0" +
                    "Please provide a title in the configuration file");
            this.title = DEFAULTS.title();
        } else {
            this.title = title.get();
        }
        Optional<String> mainView = manager.getProperty(SETTINGS_KEY+"/mainView");
        if (mainView.isEmpty()) {
            log.warn("Main view with a default value is deprecated and will be removed with release 4.0.0" +
                    "Please provide a main view in the configuration file");
            this.mainView = DEFAULTS.mainView();
        } else {
            this.mainView = mainView.get();
        }
        Optional<String> icon = manager.getProperty(EXTRAS_KEY+"/icon");
        if (icon.isEmpty()) {
            Optional<String> deprecatedIcon = manager.getProperty(SETTINGS_KEY+"/icon");
            if (deprecatedIcon.isPresent()) {
                log.warn("Icon as a mandatory property is deprecated and will be removed with release 4.0.0" +
                        "Please provide an icon in the configuration file under the key 'ierisfx.extras/icon instead of 'ierisfx/icon'");
                this.icon = deprecatedIcon.get();
            } else {
                this.icon = DEFAULTS.icon();
            }
        } else {
            this.icon = icon.get();
        }
        Optional<Boolean> resizable = manager.getBooleanProperty(EXTRAS_KEY+"/resizable");
        if (resizable.isEmpty()) {
            Optional<Boolean> deprecatedResizable = manager.getBooleanProperty(SETTINGS_KEY+"/resizable");
            if (deprecatedResizable.isPresent()) {
                log.warn("Resizable as a mandatory property is deprecated and will be removed with release 4.0.0" +
                        "Please provide an icon in the configuration file under the key 'ierisfx.extras/resizable instead of 'ierisfx/resizable'");
                this.resizable = deprecatedResizable.get();
            } else {
                this.resizable = DEFAULTS.resizable();
            }
        } else {
            this.resizable = resizable.get();
        }
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

    public Color getSceneFill() {
        return sceneFill;
    }

    public void setSceneFill(Color sceneFill) {
        this.sceneFill = sceneFill;
    }

    ViewMap getViewMap() {
        return map;
    }

    public FxConfiguration freezeData() {
        return FxConfiguration.of(this);
    }
}
