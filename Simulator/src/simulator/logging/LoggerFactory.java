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
public interface LoggerFactory {

    public boolean setChangeOnCall(boolean change);
    public boolean setDefaultLoggingLevel(SimulationLogger.LogLevel level);
    public boolean setDefaultFormatString(String format);

    public  SimulationLogger getLogger(String name);
    public  SimulationLogger getLogger(String name, SimulationLogger.LogLevel level);
    public  SimulationLogger getLogger(String name, SimulationLogger.LogLevel level, String format);
    public  SimulationLogger getLogger(Class<?> clazz);
    public  SimulationLogger getLogger(Class<?> clazz, SimulationLogger.LogLevel level);
    public  SimulationLogger getLogger(Class<?> clazz, SimulationLogger.LogLevel level, String format);

}
