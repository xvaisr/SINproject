/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.environement;

import java.util.*;
import java.util.List;

import simulator.entities.impl.DockingStation;
import simulator.entities.impl.Roomba;
import simulator.environement.rooms.Room;
import simulator.environement.rooms.impl.CommonRoom;
import simulator.environement.rooms.impl.Enterance;
import simulator.eventHandling.Event;
import simulator.eventHandling.EventFilter;
import simulator.eventHandling.EventInvoker;
import simulator.eventHandling.EventListener;
import simulator.injection.Singleton;
import simulator.injection.impl.Injector;
import simulator.logging.LoggerFactory;
import simulator.logging.SimulationLogger;
import simulator.utils.graph.ImmutableGraph;
import simulator.utils.graph.InternalGraph;
import simulator.utils.graph.Node;

/**
 * @author Roman Vais
 */
@Singleton
public class Building implements EventListener, EventInvoker {


    private HashMap<String,Room> rooms;
    private InternalGraph<Room> schema;
    private List<EventListener> listeners;
    private List<EventFilter> filters;
    private final SimulationLogger logger;

    public Building() {
        Room enterance = new Enterance();
        this.rooms = new HashMap<>();
        this.rooms.put("Entrance",enterance);
        this.schema = new InternalGraph<>(new Node<>(enterance));

        this.listeners = new LinkedList<>();
        this.filters = new LinkedList<>();

        this.logger = Injector.inject(LoggerFactory.class).getLogger("Events");
        logger.logDebug("Building successfully created!");
    }

    public List<Room> getRoomList() {
        return Collections.unmodifiableList(new ArrayList<>(this.rooms.values()));
    }

    public ImmutableGraph<Room> getRoomSchema() {
        return this.schema;
    }

/*
    public boolean connectNewRoom(Room existing, Room newRoom) {
        boolean hasIt = this.rooms.contains(existing);
        if (hasIt) {
            for (Node n : this.schema.getAllNodes()) {
                if (n.getObject() == existing) {
                    this.schema.linkObject(n, newRoom);
                    this.rooms.add(newRoom);
                    newRoom.addEventListener(this);

                    logger.logDebug("Room '" + newRoom.getId() + "'successfully connected!");
                    break;
                }
            }
        }

        return hasIt;
    }
*/

    @Override
    public void addEventListener(EventListener li) {
        if (this.listeners.contains(li)) {
            return;
        }
        this.listeners.add(li);
    }

    @Override
    public void removeEventListener(EventListener li) {
        this.listeners.remove(li);
    }

    @Override
    public void fireEvent(Event ev) {
        for (EventListener li : this.listeners) {
            li.perceiveEvent(ev);
        }
    }

    @Override
    public void perceiveEvent(Event ev) {
        for (EventFilter f : this.filters) {
            if (!f.eventPass(ev)) {
                return;
            }
        }
        for (Room r : this.rooms.values()) {
            r.perceiveEvent(ev);
        }
    }

    @Override
    public boolean addEventFilter(EventFilter f) {
        if (this.filters.contains(f)) {
            return false;
        }
        this.filters.add(f);
        return true;
    }

    public void init() {
//        addRoom("1", "2", 30);
        Room newRoom = new CommonRoom("Welcome Room", 5, 6);
        addNewRoom(newRoom, "Entrance");
//        addRoom("1", "3", 50);
        newRoom = new CommonRoom("Kitchen", 5, 10);
        addNewRoom(newRoom, "Entrance");
//        addStation("X", "3");
        DockingStation d = new DockingStation("Venice");
        newRoom.addEntity(d);
//        placeRoomba("A", "X");
        d.dockEntity(new Roomba("Ballahoo"));
//        addRoom("2", "4", 25);
        newRoom = new CommonRoom("Hallway", 5, 5);
        addNewRoom(newRoom, "Welcome Room");
//        addStation("Y", "4");
        d = new DockingStation("Dubrovnik");
        newRoom.addEntity(d);
//        placeRoomba("A", "X");
        d.dockEntity(new Roomba("Tilbury"));
//        addRoom("4", "5", 35);
        newRoom = new CommonRoom("Office1", 5, 7);
        addNewRoom(newRoom, "Hallway");
//        addRoom("1", "6", 54);
        newRoom = new CommonRoom("Meeting Room", 6, 9);
        addNewRoom(newRoom, "Entrance");
//        addRoom("1", "7", 42);
        newRoom = new CommonRoom("Office2", 7, 6);
        addNewRoom(newRoom, "Entrance");
    }

    private boolean addNewRoom(Room newRoom, String oldRoomName) {
        for (Node n : this.schema.getAllNodes()) {
            Room oldRoom = this.rooms.getOrDefault(oldRoomName,null);
            if (n.getObject() == oldRoom) {
                this.schema.linkObject(n, newRoom);
                this.rooms.put(newRoom.getId(),newRoom);
                newRoom.addEventListener(this);
                logger.logDebug("Room '" + newRoom.getId() + "'successfully connected!");
                return true;
            }
        }
        return false;
    }
}
