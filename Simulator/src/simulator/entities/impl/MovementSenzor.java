/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.entities.impl;

import simulator.entities.AbstractEntity;
import simulator.entities.actions.Action;
import simulator.entities.actions.common.MoveableEntity;
import simulator.eventHandling.Event;
import simulator.eventHandling.EventFilter;

/**
 *
 * @author Roman Vais
 */
public class MovementSenzor extends AbstractEntity {

    private final String[] ACCEPTED_EVENT_TYPES = {"StartingEvent", "Movement", "CommandSleep", "CommandWake"};

    private final String id;

    public MovementSenzor(String id) {
        super();
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public boolean performeAction(Action act) {
        return false;
    }

    @Override
    public boolean addEventFilter(EventFilter f) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void startEntity() {
        this.logger.logSimulation("Senzor '" + this.getId() + "' is active.");
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

        private String type;

        public MoveableFilter(String t) {
            this.type = t;
        }

        @Override
        public boolean eventPass(Event ev) {
            if (!ev.getType().equals(this.type)) {
                return true;
            }

            return ev.getSourceEntity(MoveableEntity.class) != null;
        }
    }
}
