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
import simulator.eventHandling.EventFilter;

/**
 *
 * @author Roman Vais
 */
public class Staircase extends AbstractRoom {

    private final String name;
    private final Dimension size;
    private final List<Entity> entList;
    private final List<EventFilter> filters;

    public Staircase() {
        this.name = this.getClass().getSimpleName();
        this.size = new Dimension(150, 1000);
        this.entList = new LinkedList<>();
        this.filters = new LinkedList<>();
    }



    @Override
    public String getId() {
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
    public boolean getHasDoor() {
        return false;
    }

    @Override
    public boolean getDoorOpened() {
        return true;
    }

    @Override
    public boolean setDoorOpened(boolean open) {
        return true;
    }

    @Override
    public void perceiveEvent(Event ev) {
        for (EventFilter f : this.filters) {
            if(!f.eventPass(ev)) {
                return;
            }
        }
        for (Entity ent : this.entList) {
            ent.perceiveEvent(ev);
        }
    }

    @Override
    public boolean addEventFilter(EventFilter f) {
        if (this.filters.contains(f)) {
            return false;
        }
        this.filters.add(f);
        return true;
    }
}
