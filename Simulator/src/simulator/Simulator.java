/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator;

import graphics.MainWindow;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.swing.SwingUtilities;
import simulator.entities.Entity;
import simulator.entities.actions.Action;
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
import simulator.entities.actions.common.StartEntity;
import simulator.entities.impl.DockingStation;
import simulator.entities.impl.Roomba;

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

        List<Action> actl = new LinkedList<>();
        actl.add(Injector.inject(StartEntity.class));

        Event ev = new GeneralEvent("StartingEvent", null, null, new TimeStamp(), Collections.emptyList());
        s.scheduleEvent(ev);

        Room kitchen, hall, wroom, office, meetingroom;
        Room entrance = b.getRoomList().get(0);

        // welcome room
        wroom = new CommonRoom("Welcome Room", 5, 6);
        b.connectNewRoom(entrance, wroom);

        // kitchen
        kitchen = new CommonRoom("Kitchen", 5, 10);
        b.connectNewRoom(entrance, kitchen);
        DockingStation d = new DockingStation("Venice");
        Roomba r = new Roomba("Ballahoo");
        kitchen.addEntity(d);
        kitchen.addEntity(r);
        d.dockEntity(r);

        // hall
        hall = new CommonRoom("Hallway", 5, 5);
        b.connectNewRoom(wroom, hall);
        d = new DockingStation("Dubrovnik");
        r = new Roomba("Tilbury");
        hall.addEntity(d);
        hall.addEntity(r);
        d.dockEntity(r);

        //office
        office = new CommonRoom("Office1", 5, 7);
        b.connectNewRoom(hall, office);

        // meeting room
        meetingroom = new CommonRoom("Meeting Room", 6, 9);
        b.connectNewRoom(entrance, meetingroom);

        //office
        office = new CommonRoom("Office2", 5, 7);
        b.connectNewRoom(entrance, office);

        Entity sensor;
        sensor = new MovementSenzor("Sensor1");
        entrance.addEntity(sensor);

        sensor = new MovementSenzor("Sensor2");
        meetingroom.addEntity(sensor);

        sensor = new MovementSenzor("Sensor3");
        kitchen.addEntity(sensor);

        // GUI by se melo zobrazit az po te, co se inicializuje budova, aby byla jistota ze vsechna data budou k dispozici
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final MainWindow win;
                win = new MainWindow("SINproject");
                win.setVisible(true);
            }
        });

        s.fireNextEvent();
    }

}
