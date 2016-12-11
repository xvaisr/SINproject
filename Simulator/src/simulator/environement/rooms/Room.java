/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.environement.rooms;

import java.awt.Dimension;
import java.util.List;
import simulator.entities.Entity;
import simulator.eventHandling.EventInvoker;
import simulator.eventHandling.EventListener;

/**
 * Model representation of room.
 * @author Roman Vais
 */
public interface Room extends EventListener, EventInvoker {

    /**
     * Returns identification string given to the room,
     *
     * @return identification string of room (should be unique)
     */
    public String getId();

    /**
     * Returns size of room as object containing individual dimensions.
     * Dimensions class is used and dimensions of the room are width and length.
     * Metric values are supposed to be in cm
     *
     * @return dimensions of the room
     */
    public Dimension getSize();

    /**
     * Returns surface area of a room.
     * Area is in cm^2.
     *
     * @return surface area of the room
     */
    public int getSurfaceArea();

    /**
     * Returns list of entities contained by this room.
     *
     * @return list of entities
     */
    public List<Entity> getEntityList();

    /**
     * Returns whether or not this room has doors.
     * Useful for Roombas when traversing building
     * @return true if room as a doors
     */
    public boolean getHasDoor();

    /**
     * Returns whether or not room doors are opened.
     * Useful for Roombas when traversing building
     * @return true if door are open
     */
    public boolean getDoorOpened();

    /**
     * Changes whether or not room doors are opened to given value and returns previous state.
     * humans can prevent Roombas from leaving the room.
     * @param open new state of the door
     * @return previous state of a door.
     */
    public boolean setDoorOpened(boolean open);

    /**
     * Places given entity to this room.
     * @param ent entity to place to the room
     * @return true if successful;
     */
    public boolean addEntity(Entity ent);

    /**
     * Removes given entity from this room.
     * @param ent entity to remove from the room
     * @return true if successful;
     */
    public boolean removeEntity(Entity ent);

}
