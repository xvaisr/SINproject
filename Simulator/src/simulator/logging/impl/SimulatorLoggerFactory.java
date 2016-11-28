/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.logging.impl;

import java.util.Hashtable;
import simulator.injection.Named;
import simulator.logging.LoggerFactory;
import simulator.logging.SimulationLogger;

/**
 *
 * @author Roman Vais
 */
@Named(name = "default")
public class SimulatorLoggerFactory implements LoggerFactory {

    public static final String DEFAULT_FORMAT = "#{name} [#{level}]: #{message};#{stack}";
    public static final SimulationLogger.LogLevel DEFAULT_LEVEL = SimulationLogger.LogLevel.DEBUG;

    private final Hashtable<String, SystemLogger> dployedLoggers;
    private String formatStr;
    private SimulationLogger.LogLevel level;
    private boolean changeOnCall;

    public SimulatorLoggerFactory() {
        this.dployedLoggers = new Hashtable<>();
        this.formatStr = DEFAULT_FORMAT;
        this.level = DEFAULT_LEVEL;
        this.changeOnCall = false;
    }

    @Override
    public boolean setChangeOnCall(boolean change) {
        boolean old = this.changeOnCall;
        this.changeOnCall = change;
        return old;
    }

    @Override
    public final boolean setDefaultLoggingLevel(SimulationLogger.LogLevel level) {
        if (level == null) return false;
        this.level = level;
        return true;
    }

    @Override
    public final boolean setDefaultFormatString(String format) {
        if (format == null || format.isEmpty()) return false;
        this.formatStr = format;
        return true;
    }

    @Override
    public SimulationLogger getLogger(String name) {
        return this.getLogger(name, this.level, this.formatStr);
    }

    @Override
    public SimulationLogger getLogger(String name, SimulationLogger.LogLevel level) {
        return this.getLogger(name, level, this.formatStr);
    }

    @Override
    public SimulationLogger getLogger(String name, SimulationLogger.LogLevel level, String format) {
        SystemLogger logger;
        if (this.dployedLoggers.contains(name)) {
            logger = this.dployedLoggers.get(name);
            if (this.changeOnCall) {
                logger.setFormat(format);
                logger.setLogLevel(level);
            }
        }
        else {
            logger = new SystemLogger(name, format, level);
            this.dployedLoggers.put(name, logger);
        }
        return logger;
    }

    @Override
    public SimulationLogger getLogger(Class<?> clazz) {
        return this.getLogger(clazz.getName());
    }

    @Override
    public SimulationLogger getLogger(Class<?> clazz, SimulationLogger.LogLevel level) {
        return this.getLogger(clazz.getName(), level, this.formatStr);
    }

    @Override
    public SimulationLogger getLogger(Class<?> clazz, SimulationLogger.LogLevel level, String format) {
        return this.getLogger(clazz.getName(), level, format);
    }

}
