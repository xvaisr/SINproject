/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.entities.impl;

import simulator.entities.AbstractEntity;
import simulator.entities.Entity;
import simulator.entities.actions.Action;

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

        this.slot = en;
        return true;
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

    @Override
    public boolean performeAction(Action act) {
        return false;
    }





}
