/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.eventHandling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import simulator.injection.Singleton;
import simulator.injection.impl.Injector;
import simulator.logging.LoggerFactory;
import simulator.logging.SimulationLogger;
import simulator.utils.modeltime.TimeStamp;

/**
 *
 * @author Roman Vais
 */

/*
    while (kalendář.empty() == False) {
	vezmi první záznam z kalendáře
	if (aktivační čas události > koncový čas)
		konec simulace
	nastav čas simulace na aktivační čas události
	proveď popis chování události
}
*/


@Singleton
public class Scheduler implements EventInvoker, EventListener {

    private static final int FILTER_COUNT = 30;

    private final List<EventListener> listeners;
    private final LinkedList<Event> qeueu;
    private final List<EventFilter> filters;

    private int currentTime;
    private int leastRecentTime;
    private final SimulationLogger logger;

    public Scheduler() {
        this.currentTime = 0;
        this.leastRecentTime = 0;
        this.qeueu = new LinkedList<>();
        this.listeners = new ArrayList<>();
        this.filters = new ArrayList<>(FILTER_COUNT);

        this.logger = Injector.inject(LoggerFactory.class).getLogger(this.getClass().getSimpleName());
        logger.logDebug("Scheduler ready!");
    }

    public void scheduleEvent(Event ev) {

        if (ev.getTimestamp().getTime() < 0) {
            logger.logDebug("Attempt to schedule event to the past. Skipping operation...");
            return;
        }

        // if you cancled event, remove it from qeueu
        if (ev.getIsScheduled() && ev.getIsCancled()) {
            this.qeueu.remove(ev);

            logger.logDebug("Event of type '" + ev.getType() + "' scheduled for : " + ev.getTimestamp().toString() +
                    " was cancled.");
            return;
        }

        // don't add event to qeueu twice and don't schedule cancled events
        if (ev.getIsScheduled() || ev.getIsCancled()) {
            logger.logDebug("Attempt to schedule already scheduled or cancled event. Skipping operation...");
            return;
        }

        // create scheduling timestamp
        TimeStamp scheduled = ev.getTimestamp().getIncreasedTimeStamp(this.currentTime);

        // if qeueu is empty or we are plannig to the end just add to list
        if (this.qeueu.isEmpty() || scheduled.getTime() > this.leastRecentTime) {
            this.qeueu.add(ev);
            ev.setScheduledTo(scheduled);

            // if event is alst on the list, it's time is least recent known time
            if (scheduled.getTime() > this.leastRecentTime) {
               this.leastRecentTime = scheduled.getTime();
            }

            logger.logDebug("Event of type '" + ev.getType() + "' scheduled for : " + ev.getTimestamp().toString());
            return;
        }

        // try to compute where in the qeueu might be this event placed
        int step = (this.leastRecentTime - this.currentTime) / this.qeueu.size();
        int index = (ev.getTimestamp().getTime() / step) - 1;
        ListIterator<Event> i = this.qeueu.listIterator(index);

        // back track if the picked element has larger timestamp
        Event ref;
        do {
            if (!i.hasPrevious()) {
                break;
            }
            ref = i.previous();
        } while (ref != null && ref.getTimestamp().getTime() > scheduled.getTime());

        // skip the elements with smaller or equal timestamp
        do {
            if (!i.hasNext()) {
                break;
            }
            ref = i.next();
        } while (ref != null && ref.getTimestamp().getTime() <= scheduled.getTime());

        // schedule event and put it into the qeueu
        ev.setScheduledTo(scheduled);
        i.set(ev);
        logger.logDebug("Event of type '" + ev.getType() + "' scheduled for : " + ev.getTimestamp().toString());
    }

    public TimeStamp getCurrentTime() {
        return new TimeStamp(null, this.currentTime);
    }

    public boolean fireNextEvent() {
        if (this.qeueu.isEmpty()) {
            return false;
        }

        Event nextEvent = this.qeueu.pollFirst();
        this.currentTime = nextEvent.getTimestamp().getTime();

        this.fireEvent(nextEvent);

        return true;
    }

    @Override
    public void addEventListener(EventListener li) {
        if (this.listeners.contains(li)) {
            return;
        }
        this.listeners.add(li);
    }

    @Override
    public void removeEventListener(EventListener li) {
        this.listeners.remove(li);
    }

    @Override
    public void fireEvent(Event ev) {
        for (EventListener li : this.listeners) {
            li.perceiveEvent(ev);
        }
    }

    @Override
    public void perceiveEvent(Event ev) {
        for (EventFilter f : this.filters) {
            if(!f.eventPass(ev)) {
                return;
            }
        }
        this.scheduleEvent(ev);
    }

    @Override
    public boolean addEventFilter(EventFilter f) {
        if (this.filters.contains(f)) {
            return false;
        }
        this.filters.add(f);
        return true;
    }
}
