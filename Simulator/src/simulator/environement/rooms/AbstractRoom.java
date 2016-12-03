/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.environement.rooms;

import java.util.LinkedList;
import simulator.eventHandling.Event;
import simulator.eventHandling.EventInvoker;
import simulator.eventHandling.EventListener;

/**
 *
 * @author Roman Vais
 */
public abstract class AbstractRoom implements Room, EventInvoker, EventListener {


    private final LinkedList<EventListener> listeners;

    public AbstractRoom() {
        this.listeners = new LinkedList<>();
    }

    @Override
    public void addEventListener(EventListener li) {
        synchronized(this.listeners) {
            if (this.listeners.contains(li)) {
                return;
            }

            this.listeners.add(li);
        }
    }

    @Override
    public void removeEventListener(EventListener li) {
        synchronized(this.listeners) {
            this.listeners.remove(li);
        }
    }

    @Override
    public void fireEvent(Event ev) {
        synchronized(this.listeners) {
            for (EventListener listener : this.listeners) {
                listener.precieveEvent(ev);
            }
        }
    }

}
