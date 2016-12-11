/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.entities.impl;

import java.awt.Dimension;
import java.util.List;
import simulator.entities.AbstractEntity;
import simulator.entities.Entity;
import simulator.entities.actions.common.MoveableEntity;
import simulator.environement.Building;
import simulator.environement.rooms.Room;
import simulator.eventHandling.Event;
import simulator.eventHandling.EventFilter;
import simulator.injection.impl.Injector;
import simulator.utils.graph.Edge;

/**
 *
 * @author Roman Vais
 */
public class Roomba extends AbstractEntity implements MoveableEntity {

    public static final int STATUS_PERIOD = 360; // every 6 minutes (360 sec)
    private static final String[] ACCEPTED_EVENT_TYPES = {
            "StartingEvent", "MoveToRoom", "CommandSleep", "CommandWake", "CommandSpot", "ChangeHomeStation",
            "SendStatus", "CommandGoCharge", "RequestPath"

    };
    private static final double CONSUMPTION = 500 / 3600.0;// 500 mA consumption in duration of 3600 sec
    private static final int CLEANED_AREA = 2800;// roomba is able to clean 2800 cm^2 / sec
    private static final int BIN_FULL_AFTER = 300000; // bin full after300 000 cm^2


    private final String id;
    private final int batteryCapacity; // mAh
    private int battery;    // current charge
    private final double speed;   // meters per second
    private int cleaned;

    private boolean active;
    private String homeStation;

    public Roomba(String id) {
        super();
        this.id = id;
        this.batteryCapacity = 3000;
        this.battery = this.batteryCapacity;
        this.cleaned = 0;
        this.speed = 0.8;
        this.active = false;
        this.homeStation = null;

    }

    public int getBatteryCapacity() {
        return this.batteryCapacity;
    }

    public int getBatterystatus() {
        return this.battery;
    }

    @Override
    public boolean changeRoom(String roomID) {
        Building b = Injector.inject(Building.class);
        List<Room> list = b.getRoomList();

        Room r = null;
        for(Room found : list) {
            if(found.getId().equals(roomID)) {
                r = found;
                break;
            }
        }

        if (r == null) {
            this.logger.logError("Cannot identify room '%s'.", r.getId());
            return false;
        }

        boolean connection = false;
        List<Edge<Room>> edges = b.getRoomSchema().getEdgesLinkedTo(this.location);
        for (Edge<Room> e : edges) {
            if(e.doesLinkThis(r) && e.doesLinkThis(this.location)) {
                connection = true;
                break;
            }
        }

        if (!connection || !r.getDoorOpened() || !this.location.getDoorOpened()) {
            this.logger.logSimulation("Roomba '%s' cannot get to room '%s'." +
                    " Rooms are not connected or door are closed.", this.getId(), r.getId());

            return false;
        }

        this.setLocation(r);
        return true;
    }

    public int dischargeBattery(int sec) {
        // to simulate small differences in charging
        double discharge = CONSUMPTION + ((Math.random() - 0.5) / 10);
        return (int) Math.round(sec * discharge);
    }

    public void chargeBattery(int mAps) {
        if (mAps > 0) {
            this.battery = this.battery + mAps;
        }
    }

    public boolean cleanArea(int areaSize) {
        if (!this.active) {
            return false;
        }

        if (areaSize <= 0) {
            return true;
        }

        int discharge, sec = areaSize / CLEANED_AREA;
        discharge = this.dischargeBattery(sec);

        if ((this.battery - discharge) < 0) {
            return false;
        }

        this.battery = this.battery - discharge;
        this.cleaned = areaSize;
        return true;
    }

    public boolean moveAcrossTheRoom() {
        if (!this.active) {
            return false;
        }

        Dimension size = this.location.getSize();
        int sec; // round ( (reandom distance + 1) / speed )
        sec = (int) Math.round(((Math.random() * (size.height + size.width)) + 1) / this.speed);

        int discharge = this.dischargeBattery(sec);
        if ((this.battery - discharge) < 0) {
            return false;
        }

        this.battery = this.battery - discharge;
        return true;
    }

    public boolean getToChargingStation() {
        if (this.homeStation == null) {
            this.logger.logSimulation("Roombe '%s' does not have assigned home station yet.", this.getId());
        }

        List<Entity> list = this.location.getEntityList();
        DockingStation station = null;
        for (Entity e : list) {
            if (e instanceof DockingStation && e.getId().equals(this.homeStation)) {
                station = (DockingStation) e;
                break;
            }
        }

        if (station == null) {
            this.logger.logSimulation("Roombe '%s' could not find home station '%s'.", this.getId(), this.homeStation);
            return false;
        }

        return moveAcrossTheRoom() && station.dockEntity(this);
    }

    public boolean disableSelf() {
        if (this.active == true) {
            this.active = false;
        }
        return !this.active;
    }

    public boolean enableSelf() {
        if (this.active == false) {
            this.active = true;
        }
        return !this.active;
    }

    public void cleanDustBin() {
        this.cleaned = 0;
    }

    public boolean getDustBinFull() {
        return this.cleaned >= BIN_FULL_AFTER;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void startEntity() {
        this.logger.logSimulation("Roomba '%s' is active.", this.getId());
        this.addEventFilter(new TypeFilter(ACCEPTED_EVENT_TYPES));
    }

    @Override
    public void travel(List<Room> path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    private class TypeFilter implements EventFilter {

        private String[] types;

        public TypeFilter(String[] types) {
            this.types = types;
        }

        @Override
        public boolean eventPass(Event ev) {
            boolean pass = false;
            for (String type : this.types) {
                pass = ev.getType().equals(type);
                if (pass) break;
            }
            return pass;
        }
    }

}
