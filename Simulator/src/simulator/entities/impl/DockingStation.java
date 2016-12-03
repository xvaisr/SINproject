/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.entities.impl;

import simulator.entities.AbstractEntity;
import simulator.entities.Entity;
import simulator.entities.actions.Action;
import simulator.environement.rooms.Room;
import simulator.eventHandling.Event;
import simulator.eventHandling.EventListener;

/**
 *
 * @author Roman Vais
 */
public class DockingStation extends AbstractEntity {

    private final int id;
    private Entity slot;
    private boolean functional;

    public DockingStation(int id) {
        this.id = id;
    }

    public boolean dockEntity(Entity en) {
        if (!(en instanceof Roomba) || slot != null || !this.functional) {
            return false;
        }

        Action act = new Action() {
            @Override
            public boolean bePerformedBy(Entity ent) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };

        en.performeAction(act);

        return true;
    }

    @Override
    public String toString() {
        return "Station:" + String.valueOf(this.id);
    }



    @Override
    public boolean performeAction(Action act) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void preciveEvent(Event ev) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Room getLocation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void precieveEvent(Event ev) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }





}
