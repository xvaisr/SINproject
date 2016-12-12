/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.entities.actions.common;

import simulator.entities.Entity;
import simulator.entities.actions.Action;
import simulator.injection.Singleton;

/**
 *
 * @author Roman Vais
 */
@Singleton
public class StartEntity implements Action {

    @Override
    public boolean bePerformedBy(Entity ent) {
        ent.startEntity();
        return true;
    }

}
