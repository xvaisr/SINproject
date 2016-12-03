/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.entities;

import simulator.entities.actions.Action;
import simulator.environement.rooms.Room;
import simulator.eventHandling.Event;
import simulator.eventHandling.EventInvoker;
import simulator.eventHandling.EventListener;

/**
 *
 * @author Roman Vais
 */
public interface Entity extends EventInvoker, EventListener {

    public String getType();
    public Room getLocation();
    public boolean performeAction(Action act);
    public void preciveEvent(Event ev);

}
