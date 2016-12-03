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
import simulator.utils.modeltime.TimeStamp;

/**
 *
 * @author Roman Vais
 */
public class GeneralEvent implements Event {

    private String type;
    private final Entity source;
    private final TimeStamp timestamp;
    private List<Action> actl;
    private boolean schceduled;

    public GeneralEvent(String type, Entity ent, TimeStamp timestamp, Collection<Action> actions) {
        this.source = ent;
        this.timestamp = timestamp;
        this.actl = new LinkedList<>(actions);
        this.type = type;
        this.schceduled = false;
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
    public TimeStamp getTimestamp() {
        return this.timestamp;
    }

    @Override
    public List<Action> getActionList() {
        return Collections.unmodifiableList(this.actl);
    }

    @Override
    public void setScheduled() {
        this.schceduled = true;
    }

    @Override
    public boolean getIsScheduled() {
        return this.schceduled;
    }

}
