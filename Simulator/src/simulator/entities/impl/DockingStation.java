/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.entities.impl;

import simulator.entities.AbstractEntity;
import simulator.entities.Entity;
/**
 *
 * @author Roman Vais
 */
public class DockingStation extends AbstractEntity {

    private final String id;
    private Roomba slot;
    private boolean functional;
    private final double chargeRate; // miliapers per second provided


    public DockingStation(String id) {
        super();
        this.id = id;
        this.functional = true;
        this.chargeRate = 1250 / 3600.0;// 12500 mA in duration of 3600 sec
    }

    public boolean dockEntity(Entity en) {
        if (!(en instanceof Roomba) || slot != null || !this.functional) {
            return false;
        }

        this.slot =  (Roomba) en;
        return true;
    }

    public int getCharge(int sec) {
        // to simulate small differences in charging
        double charge = this.chargeRate + ((Math.random() - 0.5) / 10);
        return (int) Math.round(sec * charge);
    }

    public boolean undockEntity() {
        this.slot = null;
        return true;
    }

    public boolean disableSelf() {
        if (this.functional == true) {
            this.functional = false;
        }
        return !this.functional;
    }

    public boolean enableSelf() {
        if (this.functional == false) {
            this.functional = true;
        }
        return !this.functional;
    }

    public Roomba getRoombaInSlot() {
        return this.slot;
    }

    @Override
    public void startEntity() {
        this.logger.logSimulation("Docking station '" + this.getId() + "' is active.");
    }

    @Override
    public String toString() {
        return this.getId();
    }

    @Override
    public String getId() {
        return this.getType() + String.valueOf(this.id);
    }
}
