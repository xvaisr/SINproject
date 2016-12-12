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
public class CommonRoom extends AbstractRoom {

    private boolean door;
    private final String name;
    private final Dimension size;
    private final List<Entity> entList;
    private final List<EventFilter> filters;

    public CommonRoom(String name, int width, int lenght) {
        this.name = name;
        this.size = new Dimension(width, lenght);
        this.entList = new LinkedList<>();
        this.filters = new LinkedList<>();
    }

    @Override
    public String getId() {
        return this.name;
    }

    @Override
    public Dimension getSize() {
        return new Dimension(this.size);
    }

    @Override
    public int getSurfaceArea() {
        return this.size.width * this.size.height;
    }

    @Override
    public List<Entity> getEntityList() {
        return Collections.unmodifiableList(entList);
    }

    @Override
    public boolean addEntity(Entity ent) {
        if (!this.entList.contains(ent)) {
            this.entList.add(ent);
            ent.addEventListener(this);
            ent.setLocation(this);
        }

        return true;
    }

    @Override
    public boolean removeEntity(Entity ent) {
        return this.entList.remove(ent);
    }

    @Override
    public void fireEvent(Event ev) {
        if (!ev.getIsScheduled()) {
            super.fireEvent(ev);
            return;
        }

        List<Entity> listeners;
        synchronized(this.entList) {
            listeners = Collections.unmodifiableList(this.entList);
        }
        for (Entity ent : listeners) {
            ent.perceiveEvent(ev);
        }
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

    @Override
    public boolean getHasDoor() {
        return true;
    }

    @Override
    public boolean getDoorOpened() {
        return this.door;
    }

    @Override
    public boolean setDoorOpened(boolean open) {
        boolean d = this.door;
        this.door = open;
        return d;
    }

}
