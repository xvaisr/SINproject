/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.eventHandling.events;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import simulator.entities.Entity;
import simulator.entities.actions.Action;
import simulator.eventHandling.Event;
import simulator.injection.impl.Injector;
import simulator.logging.LoggerFactory;
import simulator.logging.SimulationLogger;
import simulator.utils.modeltime.TimeStamp;

/**
 *
 * @author Roman Vais
 */
public class GeneralEvent implements Event {

    private final String type;
    private final Entity source;
    private TimeStamp timestamp;
    private final List<Action> actl;
    private boolean schceduled;
    private boolean cancled;
    private final Entity target;
    private final SimulationLogger logger;

    public GeneralEvent(String type, Entity src, Entity tg, TimeStamp timestamp, Collection<Action> actions) {
        this.source = src;
        this.target = tg;
        this.timestamp = timestamp;
        this.actl = new LinkedList<>(actions);
        this.type = type;
        this.schceduled = false;
        this.cancled = false;

        this.logger = Injector.inject(LoggerFactory.class).getLogger("Events");
        logger.logDebug("New event of '" + this.type + "' type created.");
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public Entity getSourceEntity() {
        return this.source;
    }

    @Override
    public <T> T getSourceEntity(Class<T> inf) {
        if (inf.isInstance(this.source)) {
            return inf.cast(this.source);
        }
        return null;
    }

    @Override
    public Entity getTargetEntity() {
        return this.target;
    }

    @Override
    public <T> T getTargetEntity(Class<T> inf) {
        if (inf.isInstance(this.target)) {
            return inf.cast(this.target);
        }
        return null;
    }

    @Override
    public TimeStamp getTimestamp() {
        return this.timestamp;
    }

    @Override
    public List<Action> getActionList() {
        return Collections.unmodifiableList(this.actl);
    }

    @Override
    public void setScheduledTo(TimeStamp ts) {
        if (this.schceduled == true || this.timestamp.compareTo(ts) > 0) return;
        this.schceduled = true;
        this.timestamp = ts;
    }

    @Override
    public boolean getIsScheduled() {
        return this.schceduled;
    }

    @Override
    public void setCancle() {
        this.cancled = true;
    }

    @Override
    public boolean getIsCancled() {
        return this.cancled;
    }

}
