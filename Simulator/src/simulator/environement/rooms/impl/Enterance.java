/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.environement.rooms.impl;

import java.awt.Dimension;
import java.util.Collections;
import java.util.List;
import simulator.entities.Entity;
import simulator.environement.rooms.AbstractRoom;
import simulator.environement.rooms.Room;

/**
 *
 * @author Roman Vais
 */
public class Enterance extends AbstractRoom {

    @Override
    public String getID() {
        return "Enterance";
    }

    @Override
    public Dimension getSize() {
        return new Dimension(0, 0);
    }

    @Override
    public int getSurfaceArea() {
        return 0;
    }

    @Override
    public List<Entity> getEntityList() {
        return Collections.emptyList();
    }

    @Override
    public boolean addEntity(Entity ent) {
        return true;
    }

    @Override
    public boolean removeEntity(Entity ent) {
        return false;
    }

}
