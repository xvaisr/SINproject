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
public interface SimulationLogger {

    public enum LogLevel {
        SIMULATION(0),
        ERROR(1),
        WARNING(10),
        INFO(100),
        ALL(1000),
        DEBUG(10000);

        private final int value;

        LogLevel(final int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public LogLevel getLoggingLevel();

    public void log(String mesage);
    public void log(String mesage, Throwable ex);
    public void log(String format, Object... arguments);

    public void logDebug(String mesage);
    public void logDebug(String mesage, Throwable ex);
    public void logDebug(String format, Object... arguments);

    public void logInfo(String mesage);
    public void logInfo(String mesage, Throwable ex);
    public void logInfo(String format, Object... arguments);

    public void logWarning(String mesage);
    public void logWarning(String mesage, Throwable ex);
    public void logWarning(String format, Object... arguments);

    public void logError(String mesage);
    public void logError(String mesage, Throwable ex);
    public void logError(String format, Object... arguments);

    public void logSimulation(String mesage);
    public void logSimulation(String mesage, Throwable ex);
    public void logSimulation(String format, Object... arguments);

    public void logAll(String mesage);
    public void logAll(String mesage, Throwable ex);
    public void logAll(String format, Object... arguments);

}
