package com.ieris19.lib.ui.core.config;

import com.ieris19.lib.ui.core.control.View;
import javafx.stage.StageStyle;

public record FxConfiguration(
        String title,
        String icon,
        String mainView,
        boolean resizable,
        boolean fullScreen,
        boolean alwaysOnTop,
        StageStyle windowStyle,
        ViewMap views
        ) {
        public static FxConfiguration of(FxConfigurer settings) {
                return new FxConfiguration(
                        settings.getTitle(),
                        settings.getIcon(),
                        settings.getMainView(),
                        settings.isResizable(),
                        settings.isFullscreen(),
                        settings.isAlwaysOnTop(),
                        settings.getWindowStyle(),
                        settings.getViewMap()
                );
        }

        public View getViews(String key) {
                return views.get(key);
        }
}
