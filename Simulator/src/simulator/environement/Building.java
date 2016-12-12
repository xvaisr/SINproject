/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.environement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import simulator.environement.rooms.Room;
import simulator.environement.rooms.impl.CommonRoom;
import simulator.eventHandling.Event;
import simulator.eventHandling.EventFilter;
import simulator.eventHandling.EventInvoker;
import simulator.eventHandling.EventListener;
import simulator.injection.Singleton;
import simulator.injection.impl.Injector;
import simulator.logging.LoggerFactory;
import simulator.logging.SimulationLogger;
import simulator.utils.graph.InternalGraph;
import simulator.utils.graph.Node;
import simulator.utils.graph.ImmutableGraph;

/**
 *
 * @author Roman Vais
 */
@Singleton
public class Building implements EventListener, EventInvoker {


    private ArrayList<Room> rooms;
    private InternalGraph<Room> schema;
    private List<EventListener> listeners;
    private List<EventFilter> filters;
    private final SimulationLogger logger;

    public Building() {
        Room enterance = new CommonRoom("Enterance", 5, 10);
        this.rooms = new ArrayList();
        this.rooms.add(enterance);
        this.schema = new InternalGraph<>(new Node<>(enterance));

        this.listeners = new LinkedList<>();
        this.filters = new LinkedList<>();

        this.logger = Injector.inject(LoggerFactory.class).getLogger("Events");
        logger.logDebug("Building successfully created!");
    }

    public List<Room> getRoomList() {
        return Collections.unmodifiableList(this.rooms);
    }

    public ImmutableGraph<Room> getRoomSchema() {
        return this.schema;
    }

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
        for (Room r : this.rooms) {
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

}
