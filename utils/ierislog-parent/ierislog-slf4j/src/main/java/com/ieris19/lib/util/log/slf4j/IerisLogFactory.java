package com.ieris19.lib.util.log.slf4j;

import com.ieris19.lib.util.log.common.Level;
import com.ieris19.lib.util.log.core.IerisLog;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * Implementation of {@link ILoggerFactory} which will return the {@link IerisLog} instance.
 */
public class IerisLogFactory implements ILoggerFactory {
    private static String appName = null;

    /**
     * Empty constructor as the class needs not be initialized.
     */
    public IerisLogFactory() {
    }

    /**
     * Return an appropriate {@link Logger} instance as specified by the
     * <code>name</code> parameter.
     *
     * <p>If the name parameter is equal to {@link Logger#ROOT_LOGGER_NAME}, that is
     * the string value "ROOT" (case-insensitive), then the root logger of the underlying logging system is returned.
     *
     * <p>Null-valued name arguments are considered invalid.
     *
     * <p>Certain extremely simple logging systems, e.g. NOP, may always
     * return the same logger instance regardless of the requested name.
     *
     * @param name the name of the Logger to return
     * @return a Logger instance
     */
    @Override
    public Logger getLogger(String name) {
        IerisLog loggerInstance;
        if (appName == null) {
            if (name == null) {
                throw new IllegalArgumentException("Logger name cannot be null");
            }
            if (name.equals(Logger.ROOT_LOGGER_NAME)) {
                return new IerisLogAdapter(IerisLog.getInstance());
            }
            loggerInstance = IerisLog.getInstance(name);
        } else {
            loggerInstance = IerisLog.getInstance(appName);
        }
        loggerInstance.useANSI(false);
        loggerInstance.setLogLevel(Level.INFO.value());
        return new IerisLogAdapter(loggerInstance);
    }

    public Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }

    public static void setAppName(String givenName) {
        IerisLogFactory.appName = givenName;
    }
}
