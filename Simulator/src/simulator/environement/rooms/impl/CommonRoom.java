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
import simulator.environement.rooms.Room;
import simulator.eventHandling.Event;
import simulator.eventHandling.EventInvoker;
import simulator.eventHandling.EventListener;

/**
 *
 * @author Roman Vais
 */
public class CommonRoom extends AbstractRoom {

    private final String name;
    private final Dimension size;
    private final List<Entity> entList;

    public CommonRoom(String name, int width, int lenght) {
        this.name = name;
        this.size = new Dimension(width, lenght);
        this.entList = new LinkedList<>();

    }

    @Override
    public String getID() {
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
        }

        return true;
    }

    @Override
    public boolean removeEntity(Entity ent) {
        return this.entList.remove(ent);
    }

}
