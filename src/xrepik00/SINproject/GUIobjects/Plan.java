package xrepik00.SINproject.GUIobjects;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by trepik on 5.12.2016.
 */
public class Plan extends JPanel {

    private Map<String, Room> rooms;
    private Map<String, Station> stations;
    private Map<String, Roomba> roombas;
    private Map<String, Door> doors;


    public Plan() {
        this.rooms = new HashMap<>();
        this.stations = new HashMap<>();
        this.roombas = new HashMap<>();
        this.doors = new HashMap<>();
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        addEntrance("1", 50);
        addRoom("1", "2", 30);
        addRoom("1", "3", 50);
        addStation("X", "1");
        placeRoomba("A", "X");
        selectRoom("1");
        moveRoomba("A", "1 2");
        startCleaningIn("2");
        makeSomeMessIn("3");
        repaint();
    }

    private void moveRoomba(String bn, String dn) {
        Roomba b = this.roombas.getOrDefault(bn, null);
        Door d = this.doors.getOrDefault(dn, null);
        if (d != null && b != null) {
            b.moveViaDoor(d);
        }
    }

    private void placeRoomba(String id, String sn) {
        Station s = this.stations.getOrDefault(sn, null);
        if (s != null) {
            Roomba b = s.placeRoomba();
            this.roombas.put(id, b);
        }
    }

    private void addStation(String id, String rn) {
        Room r = this.rooms.getOrDefault(rn, null);
        Station s = new Station(r);
        this.stations.put(id, s);
    }

    private void makeSomeMessIn(String id) {
        Room r = rooms.getOrDefault(id, null);
        if (r != null) {
            r.makeSomeMess();
        }
    }

    private void startCleaningIn(String id) {
        Room r = rooms.getOrDefault(id, null);
        if (r != null) {
            r.startCleaning();
        }
    }

    private void addRoom(String n1, String n2, int area) {
        Room r1 = rooms.getOrDefault(n1, null);
        if (r1 != null) {
            Cords c = r1.neighbor();
            Room r2 = new Room(c.getX(), c.getY(), area);
            this.rooms.put(n2, r2);
            Door d = new Door(r1, r2);
            this.doors.put(n1 + " " + n2, d);
        }
    }

    private void selectRoom(String id) {
        for (Entry<String, Room> e : rooms.entrySet()) {
            Room r = e.getValue();
            if (e.getKey().equals(id)) {
                r.select();
            } else {
                r.deselect();
            }
        }
    }

    private void drawPlan(Graphics2D g) {
        for (Door d : this.doors.values()) {
            d.draw(g);
        }
        for (Station s : this.stations.values()) {
            s.draw(g);
        }
        for (Roomba b : this.roombas.values()) {
            b.draw(g);
        }
        for (Room r : this.rooms.values()) {
            r.draw(g);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPlan((Graphics2D) g);
    }

    public void addEntrance(String id, int area) {
        Room r = new Room(this.getWidth() / 2, this.getHeight() / 2, area);
        this.rooms.put(id, r);
    }
}
