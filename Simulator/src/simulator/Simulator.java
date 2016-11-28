/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator;

import simulator.injection.impl.Injector;
import simulator.logging.LoggerFactory;
import simulator.logging.impl.SimulatorLoggerFactory;
import simulator.logging.impl.SystemLogger;

/**
 *
 * @author rvais
 */
public class Simulator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Injector.bind(LoggerFactory.class, SimulatorLoggerFactory.class);
        Injector.selfinjectLogger();

    }

}
