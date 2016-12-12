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
public interface EventFilter {

    /**
     * Returns whether or not has given event passed through this filter.
     * @param ev event to check
     * @return true if passed, false otherwise
     */
    public boolean eventPass(Event ev);

}
