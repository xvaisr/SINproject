/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.eventHandling;

import simulator.injection.Singleton;

/**
 *
 * @author Roman Vais
 */

@Singleton
public class Scheduler implements Runnable {

    public Scheduler() {
    }

    public void scheduleEvent(Event ev) {
    }

/*
    while (kalendář.empty() == False) {
	vezmi první záznam z kalendáře
	if (aktivační čas události > koncový čas)
		konec simulace
	nastav čas simulace na aktivační čas události
	proveď popis chování události
}
*/

    @Override
    public void run() {

    }
}
