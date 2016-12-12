/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.logging.impl;

import simulator.logging.SimulationLogger;

/**
 *
 * @author Roman Vais
 */
public class SystemLogger extends AbstractLogger {

    public SystemLogger() {
        super();
    }

    public SystemLogger(String name, String format, SimulationLogger.LogLevel lvl) {
        super(name, format, lvl);
    }

    @Override
    protected void write(String message) {
        if(this.getLoggingLevel() == LogLevel.SIMULATION) {
            System.err.println(message);
            return;
        }
        System.out.println(message);
    }

}
