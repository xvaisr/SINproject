/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator;

import java.util.Collections;
import simulator.entities.Entity;
import simulator.entities.impl.MovementSenzor;
import simulator.environement.Building;
import simulator.environement.rooms.Room;
import simulator.environement.rooms.impl.CommonRoom;
import simulator.eventHandling.Event;
import simulator.eventHandling.Scheduler;
import simulator.eventHandling.events.GeneralEvent;
import simulator.injection.impl.Injector;
import simulator.logging.LoggerFactory;
import simulator.logging.impl.SimulatorLoggerFactory;
import simulator.utils.modeltime.TimeStamp;

/**
 *
 * @author rvais
 */
public class Simulator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Bootstrap section
        Injector.bind(LoggerFactory.class, SimulatorLoggerFactory.class);
        Injector.selfinjectLogger();
        Injector.bind(Building.class, Building.class);
        Injector.bind(Scheduler.class, Scheduler.class);

        // test of Dependenci injection
        /*
        Room r = Injector.inject(CommonRoom.class);

        LoggerFactory factory1, factory2;
        SimulationLogger logger1, logger2;

        factory1 = Injector.inject(LoggerFactory.class);
        logger1 = factory1.getLogger(Simulator.class);

        factory2 = Injector.inject(LoggerFactory.class);
        logger2 = factory2.getLogger(Simulator.class);

        logger1.logDebug("logger1 === logger2: " + String.valueOf(logger1 == logger2));
        logger1.logDebug("factory1 === factory2: " + String.valueOf(factory1 == factory2));

        factory1.setChangeOnCall(true);
        logger1 = factory1.getLogger(Simulator.class,SimulationLogger.LogLevel.SIMULATION, "#[#{level}]: #{message} #{stack}");
        logger1.logDebug("This should not show i log ...");
        logger1.logSimulation("This is simulation log and should be on stderr.");
        */

        // testing hardcoded simulation section
        Scheduler s;
        Building b;
        s = Injector.inject(Scheduler.class);
        b = Injector.inject(Building.class);

        s.addEventListener(b);
        b.addEventListener(s);

        Event ev = new GeneralEvent("StartingEvent", null, null, new TimeStamp(), Collections.emptyList());
        s.scheduleEvent(ev);

        Room hall = new CommonRoom("Hallway", 200, 10000);
        Room enterance = b.getRoomList().get(0);
        b.connectNewRoom(enterance, hall);

        Entity senzor = new MovementSenzor("Senzor1");
        hall.addEntity(senzor);
    }

}
