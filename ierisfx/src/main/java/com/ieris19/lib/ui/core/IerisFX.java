/*
 * Copyright 2021 Ieris19
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
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

/**
 * The application class is the entry point of a JavaFX application. This class is a customizable concrete class
 * implementation of the {@link Application Application} class. This class is responsible for
 * initializing the IerisFX application.
 */
@Slf4j
public class IerisFX {
    /**
     * Empty script to be used in case the user does not wish to provide a script.
     */
    public static Runnable EMPTY_SCRIPT = () -> {};
    /**
     * The singleton instance of the application.
     */
    private static IerisFX instance;
    /**
     * The initializer method of the application. This script is responsible for initializing the application.
     */
    private Runnable initScript;
    /**
     * The main method of the application. This script is responsible for starting the application.
     */
    private Runnable startScript;
    /**
     * The stop method of the application. This script is responsible for cleanup after the application has terminated.
     */
    private Runnable stopScript;
    /**
     * A flag to keep track of whether the application has started or not.
     */
    private boolean hasStarted;

    /**
     * Returns the singleton instance of the application.
     *
     * @return the singleton instance of the application
     */
    public static IerisFX getInstance() {
        if (instance == null) {
            instance = new IerisFX(EMPTY_SCRIPT, EMPTY_SCRIPT, EMPTY_SCRIPT);
        }
        return instance;
    }

    /**
     * Creates a new Application instance with empty scripts. The scripts can be set later. This limits the application to
     * only the default behavior unless the scripts are set afterward.
     */
    private IerisFX(Runnable init, Runnable start, Runnable stop) {
        initScript = init;
        startScript = start;
        stopScript = stop;
        hasStarted = false;
    }

    /**
     * Sets the initialization script for the application
     *
     * @param initScript the initialization script
     */
    public void setInit(Runnable initScript) {
        if (!hasStarted) {
            this.initScript = initScript;
        } else {
            throw new IllegalStateException
                    ("Cannot set init script after application has started");
        }
    }

    /**
     * Sets the start script for the application
     *
     * @param startScript the script to be executed right before the view manager is started
     */
    public void setStart(Runnable startScript) {
        if (!hasStarted) {
            this.startScript = startScript;
        } else {
            throw new IllegalStateException
                    ("Cannot set start script after application has started");
        }
    }

    /**
     * Sets the stop script for the application
     *
     * @param stopScript the script to be executed right before the application is terminated
     */
    public void setStop(Runnable stopScript) {
        if (!hasStarted) {
            this.stopScript = stopScript;
        } else {
            throw new IllegalStateException
                    ("Cannot set stop script after application has started");
        }
    }

    /**
     * Starts the application.
     *
     * @param args that are passed to the JavaFX application
     */
    public void launch(String... args) {
        Application.launch(IerisFXApplication.class);
    }

    //TODO: Verify that this class can be instantiated by the JavaFX Launcher

    /**
     * The IerisFX application class. This class is responsible for actually running the application.
     * This class is instantiated by the JavaFX Launcher. This class is not meant to be instantiated by the user.
     */
    private static class IerisFXApplication extends Application {
        private final IerisFX application;

        public IerisFXApplication() {
            application = IerisFX.getInstance();
            application.hasStarted = true;
            log.debug("IerisFX Application has started");
        }

        /**
         * The application initialization method. This method is called immediately after the Application class is loaded and
         * constructed. An application may provide a script to run within this method to perform any necessary initialization
         * through the {@link #setInit(Runnable) setInit} method.
         *
         * <p>
         * The implementation of this method provided by the Application class does nothing.
         * </p>
         *
         * <p>
         * NOTE: This method is not called on the IerisFX Main Thread
         * </p>
         */
        @Override
        public void init() {
            Thread.currentThread().setName("IerisFX Initializer");
            application.initScript.run();
            log.debug("IerisFX Application has been initialized");
        }

        /**
         * A central method to all IerisFX applications. The start method is called after the init method has returned,
         * and after the system is ready for the application to begin running. An application may provide a script to
         * run within this method to start any necessary secondary processes through the
         * {@link #setStart(Runnable) setStart} method.
         * <p>
         * The implementation of this method already instantiates a {@link ViewManager ViewManager}
         * and starts it on the default view.
         * </p>
         *
         * <p>
         * NOTE: This method is called on the IerisFX Main Thread
         * </p>
         *
         * @param primaryStage the primary stage for this application, onto which the application scene can be set.
         *                     Applications may create other stages, if needed, but they will not be primary stages.
         */
        @Override
        public void start(Stage primaryStage) {
            Thread.currentThread().setName("IerisFX Main Thread");
            application.startScript.run();
            log.debug("Setting up the default view");
            ViewManager vm = new ViewManager();
            vm.start(primaryStage);
        }

        /**
         * This method is called when the application should stop, and provides a convenient place to prepare for application
         * exit and destroy resources. An application may provide an implementation of this method to perform any necessary
         * cleanup through the {@link #setStop(Runnable) setStop} method.
         *
         * <p>
         * NOTE: This method is called on the IerisFX Main Thread.
         * </p>
         */
        @Override
        public void stop() {
            String threadName = Thread.currentThread().getName();
            Thread.currentThread().setName("IerisFX Cleanup Thread");
            application.stopScript.run();
            application.hasStarted = false;
            log.debug("IerisFX Application has been stopped");
            Thread.currentThread().setName(threadName);
        }
    }
}
