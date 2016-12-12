/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.entities.impl;

import simulator.entities.AbstractEntity;
import simulator.entities.actions.common.MoveableEntity;
import simulator.eventHandling.Event;
import simulator.eventHandling.EventFilter;

/**
 *
 * @author Roman Vais
 */
public class MovementSenzor extends AbstractEntity {

    private static final String[] ACCEPTED_EVENT_TYPES = {"StartingEvent", "Movement", "CommandSleep", "CommandWake"};
    private static final String[] SOURCELESS_EVENT_TYPES = {"StartingEvent", "CommandSleep", "CommandWake"};


    private final String id;
    private boolean active;

    public MovementSenzor(String id) {
        super();
        this.id = id;
        this.active = true;
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

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void startEntity() {
        this.logger.logSimulation("Senzor '" + this.getId() + "' is active.");
        this.addEventFilter(new TypeFilter(ACCEPTED_EVENT_TYPES));
        this.addEventFilter(new MoveableFilter(SOURCELESS_EVENT_TYPES));
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

    private class MoveableFilter implements EventFilter {

        private String[] types;

        public MoveableFilter(String[] types) {
            this.types = types;
        }

        @Override
        public boolean eventPass(Event ev) {
            boolean pass = false;
            for (String type : this.types) {
                pass = ev.getType().equals(type);
                if (pass) break;
            }

            return (ev.getSourceEntity(MoveableEntity.class) != null) || pass;
        }
    }
}
