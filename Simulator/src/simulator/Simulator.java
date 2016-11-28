/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator;

import simulator.environement.rooms.Room;
import simulator.environement.rooms.impl.CommonRoom;
import simulator.injection.impl.Injector;
import simulator.logging.LoggerFactory;
import simulator.logging.SimulationLogger;
import simulator.logging.impl.SimulatorLoggerFactory;

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

        Room r = Injector.inject(CommonRoom.class);

        LoggerFactory factory1, factory2;
        SimulationLogger logger1, logger2;

        factory1 = Injector.inject(LoggerFactory.class);
        logger1 = factory1.getLogger(Simulator.class);

        factory2 = Injector.inject(LoggerFactory.class);
        logger2 = factory2.getLogger(Simulator.class);

        logger1.logDebug("logger1 === logger2: " + String.valueOf(logger1 == logger2));
        logger1.logDebug("factory1 === factory2: " + String.valueOf(factory1 == factory2));

    }

}
