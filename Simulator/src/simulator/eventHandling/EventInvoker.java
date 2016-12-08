/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.eventHandling;

/**
 * Implements one part of observer pattern.
 * @author Roman Vais
 */
public interface EventInvoker {

    /**
     * Adds EventListener that wants to be informed about any events occurring on this object.
     *
     * @param  li EventListener to be added
     */
    public void addEventListener(EventListener li);

    /**
     * Unregisters EventListener that does not want to be informed about any events occurring on this object anymore.
     *
     * @param  li EventListener to be added
     */
    public void removeEventListener(EventListener li);

    /**
     * Sends given event to all registered listeners.
     *
     * @param  ev event that occurred to be distributed among registered EventListeners
     */
    public void fireEvent(Event ev);

}
