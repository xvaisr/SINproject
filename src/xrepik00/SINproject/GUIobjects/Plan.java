package xrepik00.SINproject.GUIobjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by trepik on 5.12.2016.
 */
public class Plan extends JPanel {
    private static final int slotSize = 100;
    private Slot[] slots;
    private Map<String, Room> rooms;
    private Map<String, Station> stations;
    private Map<String, Roomba> roombas;
    private Map<String, Door> doors;
    private int slotsInColumns;
    private int slotsInRows;
    private int[] dx = {0, 1, 1, 1, 0, -1, -1, -1, -1, 0, 1, 2, 2, 2, 2, 2};
    private int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1, -2, -2, -2, -2, -1, 0, 1, 2};


    public Plan() {
        this.rooms = new HashMap<>();
        this.stations = new HashMap<>();
        this.roombas = new HashMap<>();
        this.doors = new HashMap<>();
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.slots = initSlots();
        initBuilding();
        repaint();
    }

    private void initBuilding() {
        addEntrance("1", 50);
        addRoom("1", "2", 30);
        addRoom("1", "3", 50);
        addRoom("2", "4", 25);
        addRoom("4", "5", 35);
        addRoom("1", "6", 53);
        addRoom("1", "7", 42);
        addStation("X", "1");
        placeRoomba("A", "X");
    }

    private Slot[] initSlots() {
        this.slotsInRows = this.getHeight() / slotSize;
        this.slotsInColumns = this.getWidth() / slotSize;
        Slot[] a = new Slot[this.slotsInColumns * this.slotsInRows];
        int x = slotSize / 2;
        int y = slotSize / 2;
        for (int i = 0; i < a.length; i++) {
            a[i] = new Slot(x, y);
            x += slotSize;
            if (x + slotSize / 2 > this.getWidth()) {
                x = slotSize / 2;
                y += slotSize;
            }
        }
        return a;
    }

    public void roombaMove(String bn, String dn) {
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
        Station s = new Station(r, findNeighbor(r));
        this.stations.put(id, s);
    }

    private void makeSomeMessIn(String id) {
        Room r = rooms.getOrDefault(id, null);
        if (r != null) {
            r.makeSomeMess();
        }
    }

    private void addRoom(String n1, String n2, int area) {
        Room r1 = rooms.getOrDefault(n1, null);
        if (r1 != null) {
            Slot s = findNeighbor(r1);
            Room r2 = new Room(s, area);
            this.rooms.put(n2, r2);
            Door d = new Door(r1, r2);
            this.doors.put(n1 + " " + n2, d);
        }
    }

    private Slot findNeighbor(Room r1) {
        Slot s = r1.getSlot();
        Slot sn = s;
        int j = 0;
        while (!sn.isEmpty()) {
            int xind = s.getX() / slotSize + this.dx[j];
            int yind = s.getY() / slotSize + this.dy[j];
            int index = xind + yind * this.slotsInColumns;
            if (index < 0 || index >= this.slots.length) {
                j += 1;
                continue;
            }
            sn = this.slots[index];
            j += 1;
        }
        return sn;
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
        for (Slot t : this.slots) {
            t.draw(g);
        }
        for (Door d : this.doors.values()) {
            d.draw(g);
        }
        for (Station s : this.stations.values()) {
            s.draw(g);
        }
        for (Room r : this.rooms.values()) {
            r.draw(g);
        }
        for (Roomba b : this.roombas.values()) {
            b.draw(g);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPlan((Graphics2D) g);
    }

    private void addEntrance(String id, int area) {
        Room r = new Room(this.slots[this.slots.length / 2], area);
        this.rooms.put(id, r);
    }

    public void roombaClean(String bn, String rn) {
        Roomba b = roombas.getOrDefault(bn, null);
        Room r = rooms.getOrDefault(rn, null);
        if (r != null && b != null && r.getSlot().equals(b.getSlot())) {
            b.startCleaning();
            r.startCleaning();
        }
    }
}
