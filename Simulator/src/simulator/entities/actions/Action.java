/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.entities.actions;

import simulator.entities.Entity;

/**
 * Interface Action is represents class implementing the action that should be performed on given entity.
 * Implementing class should be written for specific implementation of entity so it can utilize all methods of that
 * entity, that are not accessible through simulator.entities.Entity interface.
 * @author Roman Vais
 */
public interface Action {

    /**
     * Method tries to perform this action on given entity. Implementation should
     * try to cast entity to specific implementation and utilize methods
     * that are not accessible through Entity interface.
     * 
     * @param ent entity on which action is supposed to be performed
     * @return true if action performing was successful, false otherwise
     */
    public boolean bePerformedBy(Entity ent);

}
