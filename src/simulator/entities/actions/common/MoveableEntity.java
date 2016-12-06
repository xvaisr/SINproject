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
 *
 * @author Roman Vais
 */
public interface MoveableEntity extends Entity {

    public void changeRoom(Room r);
    public void travel(List<Room> path);

}
