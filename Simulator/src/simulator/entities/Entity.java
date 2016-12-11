/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.entities;

import simulator.entities.actions.Action;
import simulator.environement.rooms.Room;
import simulator.eventHandling.EventInvoker;
import simulator.eventHandling.EventListener;

/**
 * Interface represents entity for simulation model that can be found in any room of the building.
 * Implementations include Roomba, humans, motion sensors, etc.
 * @author Roman Vais
 */
public interface Entity extends EventInvoker, EventListener {

    /**
     * Returns identification string given to the entity,
     *
     * @return identification string of entity (usually includes type)
     */
    public String getId();

    /**
     * Returns identification type string of the entity. Usually equals to
     * class.getSimpleName();
     *
     * @return identification string of entity type
     */
    public String getType();

    /**
     * Returns the model representation of a room in which this entity is currently located.
     *
     * @return room model representation
     */
    public Room getLocation();

    /**
     * Changes the room in which this entity is currently located.
     *
     * @param r room form model representation
     */
    public void setLocation(Room r);

    /**
     * Method makes entity to perform an action. Action is instance of a class implementing
     * simulator.entities.actions.Action interface. Class implementing the action should be
     * written for specific implementation of entity so it can utilize all methods of that
     * entity, that are not accessible through this interface.
     *
     * @param act instance of Action interface implementing class
     * @return true if performing an action was successful, false otherwise
     */
    public boolean performeAction(Action act);

    /**
     * Method that should be called only after the entity gets to react on starting event.
     */
    public void startEntity();
}
