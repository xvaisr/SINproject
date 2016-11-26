/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.environement.rooms;

import java.awt.Dimension;
import java.util.List;
import simulator.entities.Entity;

/**
 *
 * @author Roman Vais
 */
public interface Room {

    public String getID();
    public Dimension getSize();
    public int getSurfaceArea();
    public List<Entity> getEntityList();

    public boolean addEntity(Entity ent);
    public boolean removeEntity(Entity ent);

}
