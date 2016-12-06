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

    public String getType();
    public Entity getSourceEntity();
    public <T> T getSourceEntity(Class<T> inf);
    public Entity getTargetEntity();
    public <T> T getTargetEntity(Class<T> inf);
    public TimeStamp getTimestamp();
    public List<Action> getActionList();
    public void setScheduledTo(TimeStamp ts);
    public void setCancle();
    public boolean getIsScheduled();
    public boolean getIsCancled();



}
