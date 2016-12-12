/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.eventHandling;

/**
 *
 * @author Roman Vais
 */
public interface EventListener {

    /**
     * Method called by EventInvoker upon event occurring.
     *
     * @param  ev occurring event
     */
    public void perceiveEvent(Event ev);

    /**
     * Adds filter to events that are accepted
     *
     * @param  f EventFilter implementation
     * @return returns true if filter could be added. false otherwise
     */
    public boolean addEventFilter(EventFilter f);
}
