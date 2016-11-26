/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.entities.actions;

import simulator.entities.Entity;

/**
 *
 * @author Roman Vais
 */
public interface Action {

    public boolean bePerformedBy(Entity ent);

}
