package com.ieris19.lib.util.log.slf4j;

import org.slf4j.ILoggerFactory;
import org.slf4j.IMarkerFactory;
import org.slf4j.MarkerFactory;
import org.slf4j.helpers.BasicMDCAdapter;
import org.slf4j.helpers.BasicMarkerFactory;
import org.slf4j.spi.MDCAdapter;

/**
 * This class provides the logging service for the slf4j facade. All methods directed at slf4j will be directed to this
 * provider if present in the classpath and no other slf4j providers are present. If other providers are present it
 * won't work.
 */
public class SLF4JServiceProvider implements org.slf4j.spi.SLF4JServiceProvider {
    private final static String apiVersion = "2.0.5";
    private ILoggerFactory loggerFactory;
    private IMarkerFactory markerFactory;
    private MDCAdapter mdcAdapter;

    /**
     * Empty constructor for the SLF4JServiceProvider class as it is not used.
     */
    public SLF4JServiceProvider() {
    }

    /**
     * Return the instance of {@link org.slf4j.ILoggerFactory} that {@link org.slf4j.LoggerFactory} class should bind to.
     *
     * @return instance of {@link org.slf4j.ILoggerFactory}
     */
    @Override
    public ILoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    /**
     * Return the instance of {@link org.slf4j.IMarkerFactory} that {@link org.slf4j.MarkerFactory} class should bind to.
     *
     * @return instance of {@link org.slf4j.IMarkerFactory}
     */
    @Override
    public IMarkerFactory getMarkerFactory() {
        return markerFactory;
    }

    /**
     * Return the instance of {@link org.slf4j.spi.MDCAdapter} that {@link org.slf4j.MDC} should bind to.
     *
     * @return instance of {@link org.slf4j.spi.MDCAdapter}
     */
    @Override
    public MDCAdapter getMDCAdapter() {
        return mdcAdapter;
    }

    /**
     * Return the maximum API version for SLF4J that the logging implementation supports.
     *
     * <p>For example: {@code "2.0.1"}.
     *
     * @return the string API version.
     */
    @Override
    public String getRequestedApiVersion() {
        return apiVersion;
    }

    /**
     * Initialize the logging back-end.
     *
     * <p><b>WARNING:</b> This method is intended to be called once by
     * {@link org.slf4j.LoggerFactory} class and from nowhere else.
     */
    @Override
    public void initialize() {
        loggerFactory = new IerisLogFactory();
        markerFactory = new BasicMarkerFactory();
        mdcAdapter = new BasicMDCAdapter();
    }
}
