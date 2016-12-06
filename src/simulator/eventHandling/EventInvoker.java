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
public interface EventInvoker {

    public void addEventListener(EventListener li);
    public void removeEventListener(EventListener li);
    public void fireEvent(Event ev);

}
