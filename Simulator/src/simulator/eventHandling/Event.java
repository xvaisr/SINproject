/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.eventHandling;

import java.util.List;
import simulator.entities.Entity;
import simulator.entities.actions.Action;
import simulator.utils.modeltime.TimeStamp;

/**
 *
 * @author Roman Vais
 */
public interface Event {

    /**
     * Returns string identifying type of event
     *
     * @return type of this event
     */
    public String getType();

    /**
     * Returns entity that invoked this event.
     *
     * @return common entity
     */
    public Entity getSourceEntity();

    /**
     * Returns source entity cast to given specific implementation.
     * If the source entity is not instance given class returns null.
     *
     * @param <T> class extending Entity interface
     * @param inf class implementing Entity interface
     * @return entity cast to given class if it is possible, null otherwise
     */
    public <T> T getSourceEntity(Class<T> inf);

    /**
     * Returns target entity for this event if it is known.
     * Entity does not necessarily implement or represent specific entity.
     *
     * @return target entity if defined, null otherwise
     */
    public Entity getTargetEntity();

    /**
     * Returns target entity cast to given specific implementation.
     * If the target entity is not instance given class returns null.
     *
     * @param <T> class extending Entity interface
     * @param inf class implementing Entity interface
     * @return entity cast to given class if it is possible, null otherwise
     */
    public <T> T getTargetEntity(Class<T> inf);

    /**
     * Returns timestamp of a time to which is this event planed.
     * If event has not been planned yet, timestamp represents how long it will take
     * to invoke event since current simulation time. Otherwise it represents time when
     * this event actually occurred.
     *
     * @return timestamp of this event
     */
    public TimeStamp getTimestamp();

    /**
     * Returns list of actions that should be performed by target entity.
     *
     * @return list of actions or empty list
     */
    public List<Action> getActionList();

    /**
     * Method that should be used only by scheduler. It will take effect only after first
     * call.
     *
     * @param ts actual timestamp when this event will occur.
     */
    public void setScheduledTo(TimeStamp ts);

    /**
     * Method that sets this event to be canceled. It will take effect only after first
     * call. To take effect event should be send for scheduling again. It will remove event
     * from scheduling queue.
     *
     * @param ts actual timestamp when this event will occur.
     */
    public void setCancle();

    /**
     * Returns whether or not has this event was already scheduled.
     *
     * @return true if scheduled, false otherwise
     */
    public boolean getIsScheduled();

    /**
     * Returns whether or not has this event was canceled.
     *
     * @return true if canceled, false otherwise
     */
    public boolean getIsCancled();



}
