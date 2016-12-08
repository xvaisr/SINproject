/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.entities.actions.common;

import java.util.List;
import simulator.entities.Entity;
import simulator.environement.rooms.Room;

/**
 * Interface for Entities that can be detected by MovementSenzor. Extends Entity interface for methods that
 * can be used for Action implementation to move these entities from one room of a building to another.
 * @author Roman Vais
 */
public interface MoveableEntity extends Entity {

    /**
     * Changes the room in which this entity is currently located.
     *
     * @param r room form model representation
     */
    public void changeRoom(Room r);

    /**
     * Performs planning of several events that gradually change the location of this entity. Entity will plan
     * based on it's speed and distances individual events, that changes the location of this entity to another
     * room and this movement should be also detected by movement sensor.
     *
     * @param path list of rooms through which this entity should travel.
     */
    public void travel(List<Room> path);

}
