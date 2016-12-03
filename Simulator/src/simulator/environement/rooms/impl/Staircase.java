/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.environement.rooms.impl;

import java.awt.Dimension;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import simulator.entities.Entity;
import simulator.environement.rooms.AbstractRoom;
import simulator.eventHandling.Event;

/**
 *
 * @author Roman Vais
 */
public class Staircase extends AbstractRoom {

    private final String name;
    private final Dimension size;
    private final List<Entity> entList;

    public Staircase() {
        this.name = this.getClass().getSimpleName();
        this.size = new Dimension(150, 1000);
        this.entList = new LinkedList<>();
    }



    @Override
    public String getID() {
         return this.name + " " + this.hashCode();
    }

    @Override
    public Dimension getSize() {
        return this.size;
    }

    @Override
    public int getSurfaceArea() {
        return this.size.height * this.size.width;
    }

    @Override
    public List<Entity> getEntityList() {
        return Collections.unmodifiableList(this.entList);
    }

    @Override
    public boolean addEntity(Entity ent) {
        if (!this.entList.contains(ent)) {
            this.entList.add(ent);
        }

        return true;
    }

    @Override
    public boolean removeEntity(Entity ent) {
        return this.entList.remove(ent);
    }

    @Override
    public void precieveEvent(Event ev) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
