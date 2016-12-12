/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.entities;

import java.util.LinkedList;
import java.util.List;
import simulator.entities.actions.Action;
import simulator.environement.rooms.Room;
import simulator.eventHandling.Event;
import simulator.eventHandling.EventFilter;
import simulator.eventHandling.EventListener;
import simulator.injection.impl.Injector;
import simulator.logging.LoggerFactory;
import simulator.logging.SimulationLogger;

/**
 *
 * @author Roman Vais
 */
public abstract class AbstractEntity implements Entity {

    private final List<EventListener> listeners;
    protected final List<EventFilter> filters;
    protected final SimulationLogger logger;
    protected Room location;

    public AbstractEntity() {
        this.listeners = new LinkedList<>();
        this.filters = new LinkedList<>();

        this.logger = Injector.inject(LoggerFactory.class).getLogger(this.getClass());
        logger.logDebug("Entity '%s' created!", this.getType());
        this.location = null;
    }

    @Override
    public Room getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(Room r) {
        if (r != null) {
            this.location.removeEntity(this);
            this.location = r;
            r.addEntity(this);
            this.logger.logSimulation("Location of '%s' has changed to '%s' room.", this.getId(), r.getId());
        }
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName();
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
        for (EventListener listener : this.listeners) {
            listener.perceiveEvent(ev);
        }
    }

    @Override
    public void perceiveEvent(Event ev) {
        for (EventFilter f : this.filters) {
            if(!f.eventPass(ev)) {
                this.logger.logDebug("Event '%s' did not pass filter. Skipping...", ev.getType());
                return;
            }
        }

        this.logger.log(ev.toString());
        List<Action> acts = ev.getActionList();
        for (Action act : acts) {
            boolean sc = this.performeAction(act);
            this.logger.logSimulation("Action '%s' result: %s", act.getClass().getSimpleName(), sc);
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

    @Override
    public boolean performeAction(Action act) {
        return act.bePerformedBy(this);
    }

}
