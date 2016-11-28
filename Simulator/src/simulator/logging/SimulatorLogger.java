/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.logging;

/**
 *
 * @author Roman Vais
 */
public interface SimulatorLogger {

    public enum LogLevel {
        DEBUG,
        INFO,
        WARNING,
        ERROR,
        SIMULATION,
        ALL
    }

    public LogLevel getLoggingLevel();
    public void log(String mesage);
    public void log(String format, Object... arguments);

    public void setLogLevel(LogLevel lvl);
}
