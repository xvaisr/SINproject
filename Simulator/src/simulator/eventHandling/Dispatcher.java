/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.eventHandling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import simulator.injection.Injection;


/**
 *
 * @author Roman Vais
 */

public class Dispatcher implements Runnable, EventInvoker, EventListener {

    public static final String FILTER_BY_ROOM = "location: ";
    public static final String FILTER_BY_TYPE = "type: ";

    private static final int QUEUE_CAPACITY = 1000;
    private static final int RULE_COUNT = 30;

    private final LinkedBlockingQueue<Event> queue;
    private final LinkedList<Event> list;
    private final ArrayList<String> filter;
    private volatile boolean running;

    @Injection
    private Scheduler schedular;



    public Dispatcher() {
        this.running = false;
        this.queue = new LinkedBlockingQueue(QUEUE_CAPACITY);
        this.list = new LinkedList<>();
        this.filter = new ArrayList<>(RULE_COUNT);
}

    @Override
    public void run() {
        this.running = true;

        while (this.running == true) {
        }

    }

    public  void stop() {
        this.running = false;
    }

    @Override
    public void addEventListener(EventListener li) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeEventListener(EventListener li) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fireEvent(Event ev) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void precieveEvent(Event ev) {
        if (ev == null) return;

        if (!ev.getIsScheduled()) {
            if (this.schedular != null) {
                this.schedular.scheduleEvent(ev);
            }
            return;
        }

        String type, location;
        type = ev.getType();
        if (ev.getSourceEntity().getLocation() != null) {
            location = ev.getSourceEntity().getLocation().getID();
        }
        else {
            location = "";
        }

        boolean pass = false;
        for (String f : filter) {
            if (f.startsWith(FILTER_BY_ROOM)) {

            }
        }

    }


}
