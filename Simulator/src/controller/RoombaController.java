package controller;

import graphics.GUIobjects.Plan;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by trepik on 9.12.2016.
 */
public class RoombaController {

    private Queue<String[]> events;
    private Plan plan;
    private int MOVEMENT_SENSOR_THRESHOLD;
    private int DOOR_SENSOR_THRESHOLD;

    public RoombaController(int mst, int dst) {
        this.MOVEMENT_SENSOR_THRESHOLD = mst;
        this.MOVEMENT_SENSOR_THRESHOLD = dst;
    }

    private void generateEvents() {
        this.events = new PriorityQueue<>();
        // movement in room 1
        this.events.offer(new String[]{"movement", "1"});
        // movement in room 2
        this.events.offer(new String[]{"movement", "2"});
        // async singal to clean room 4
        this.events.offer(new String[]{"forceClean", "4"});
        // async singal to clean room 4
        //this.events.offer(new String[]{"approved", "4"});
        // movement in room 2
        this.events.offer(new String[]{"movement", "2"});
        // movement in room 4
        this.events.offer(new String[]{"movement", "4"});
    }

    private String[] getEvent() {
        if (this.events.size() > 0) {
            return this.events.poll();
        } else {
            return null;
        }
    }

    private void control(Plan p) {
        this.plan = p;
        String[] e = this.getEvent();
        while (e != null) {
            if (e[0].equals("movement")) {
                movementIn(e[1]);
            } else if (e[0].equals("forceClean")) {
                schedulePriorityCleaning(e[1]);
            }
            e = this.getEvent();
        }
    }

    private void schedulePriorityCleaning(String rn) {
        // for all not working robots
        // create a path to the room
        // pick the robot with the shortest path
        // assign the cleaning job to that robot
        // show that room is dirty in GUI
        plan.roomMakeMess(rn);
    }

    private void movementIn(String rn) {
        // chceck paths through rn
        // halt robots in rn
        // turn on door neighboring door sensors
        // reschedule room cleaning
        // show in GUI
        plan.roomOccupied(rn);
    }

}
