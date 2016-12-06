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
 *
 * @author Roman Vais
 */
public interface Room extends EventListener, EventInvoker {

    public String getId();
    public Dimension getSize();
    public int getSurfaceArea();
    public List<Entity> getEntityList();

    public boolean getHasDoor();
    public boolean getDoorOpened();
    public boolean setDoorOpened(boolean open);

    public boolean addEntity(Entity ent);
    public boolean removeEntity(Entity ent);

}
