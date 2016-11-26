/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.entities;

import simulator.entities.actions.Action;
import simulator.eventHandling.Event;
import simulator.eventHandling.EventInvoker;

/**
 *
 * @author Roman Vais
 */
public interface Entity extends EventInvoker {

    public String getType();
    public boolean performeAction(Action act);
    public void preciveEvent(Event ev);

}
