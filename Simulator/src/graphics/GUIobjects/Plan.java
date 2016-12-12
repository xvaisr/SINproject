package graphics.GUIobjects;

import simulator.entities.Entity;
import simulator.entities.impl.DockingStation;
import simulator.environement.Building;
import simulator.injection.impl.Injector;
import simulator.utils.graph.Edge;
import simulator.utils.graph.ImmutableGraph;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by trepik on 5.12.2016.
 */
public class Plan extends JPanel {
    private static final int slotSize = 100;
    private static final int ENTRANCE = 0;
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

    private void initBuilding() {

        Building b = Injector.inject(Building.class);
        List<simulator.environement.rooms.Room> roomList;
        ImmutableGraph<simulator.environement.rooms.Room> schema;

        roomList = new LinkedList<>(b.getRoomList());
        schema = b.getRoomSchema();

        List<Edge<simulator.environement.rooms.Room>> edges, unusedEdges;
        unusedEdges = new LinkedList<>(b.getRoomSchema().getAllEdges());
        simulator.environement.rooms.Room r = roomList.get(ENTRANCE);
        addEntrance(r.getId(), r.getSurfaceArea());
        roomList.remove(roomList.indexOf(r));

        while (roomList.size() > 0) {

            edges = new LinkedList<>(schema.getEdgesLinkedTo(r));
            edges.retainAll(unusedEdges);
            if (edges.isEmpty()){
                roomList.remove(roomList.indexOf(r));
            }
            if (rooms.containsKey(r.getId())) { // room is already in GUI
                for (Edge<simulator.environement.rooms.Room> ed : edges) {
                    simulator.environement.rooms.Room left, right;
                    left = ed.getNode(true).getObject();
                    right = ed.getNode(false).getObject();

                    if (r == left) {
//                        System.out.println("Left: " + left.getId() + " " + right.getId());
                        addRoom(left.getId(), right.getId(), right.getSurfaceArea());
                        unusedEdges.remove(unusedEdges.indexOf(ed));
                    } else {
//                        System.out.println("Right: " + left.getId() + " " + right.getId());
                        addRoom(right.getId(), left.getId(), left.getSurfaceArea());
                        unusedEdges.remove(unusedEdges.indexOf(ed));
                    }
                    List<Entity> entList = r.getEntityList();
                    for (Entity ent : entList) {
                        if (ent instanceof DockingStation) {
//                            System.out.println(ent.getId());
                            addStation(ent.getId(), ent.getLocation().getId());
                        }

                        if (ent instanceof simulator.entities.impl.Roomba) {
//                            System.out.println(ent.getId());
                            placeRoomba(ent.getId(), ent.getLocation().getId());
                        }
                    }
                }
            }
            if (roomList.isEmpty()) {
                break;
            }
            r = roomList.get(ThreadLocalRandom.current().nextInt(0, roomList.size()));
        }

        for (Edge<simulator.environement.rooms.Room> ed : unusedEdges) {
            Room left = rooms.getOrDefault(ed.getNode(true).getObject().getId(), null);
            Room right = rooms.getOrDefault(ed.getNode(false).getObject().getId(), null);
            if (left != null && right != null) {
                addDoor(left, right);
            }
        }
    }

    private void addDoor(Room left, Room right) {
        Door d = new Door(left, right);
        this.doors.put(left.getID() + " " + right.getID(), d);
    }

    private void addEntrance(String id, int area) {
        Room r = new Room(this.slots[this.slots.length / 2], id, area);
        this.rooms.put(id, r);
    }

    private void addRoom(String n1, String n2, int area) {
        Room r1 = rooms.getOrDefault(n1, null);
        if (r1 != null) {
            Slot s = findNeighbor(r1);
            Room r2 = new Room(s, n2, area);
            this.rooms.put(n2, r2);
            Door d = new Door(r1, r2);
            this.doors.put(n1 + " " + n2, d);
        }
    }

    private void addStation(String id, String rn) {
        Room r = this.rooms.getOrDefault(rn, null);
        Station s = new Station(r, findNeighbor(r));
        this.stations.put(id, s);
    }

    private void placeRoomba(String id, String sn) {
        Station s = this.stations.getOrDefault(sn, null);
        if (s != null) {
            Roomba b = s.placeRoomba();
            this.roombas.put(id, b);
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

    private void drawPlan(Graphics2D g) {
        /*for (Slot t : this.slots) {
            t.draw(g);
        }*/
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

    private void roomSelect(String id) {
        for (Entry<String, Room> e : rooms.entrySet()) {
            Room r = e.getValue();
            if (e.getKey().equals(id)) {
                r.select();
            } else {
                r.deselect();
            }
        }
    }

    public void roomMakeMess(String rn) {
        Room r = rooms.getOrDefault(rn, null);
        if (r != null) {
            r.makeSomeMess();
        }
    }

    public void roomOccupied(String rn) {
        Room r = rooms.getOrDefault(rn, null);
        if (r != null) {
            r.occupy();
        }
    }

    public void roomClear(String rn) {
        Room r = rooms.getOrDefault(rn, null);
        if (r != null) {
            r.clear();
        }
    }

    public void roombaMove(String bn, String dn) {
        Roomba b = this.roombas.getOrDefault(bn, null);
        Door d = this.doors.getOrDefault(dn, null);
        if (d != null && b != null) {
            b.moveViaDoor(d);
        }
    }

    public void roombaClean(String bn, String rn) {
        Roomba b = roombas.getOrDefault(bn, null);
        Room r = rooms.getOrDefault(rn, null);
        if (r != null && b != null && r.getSlot().equals(b.getSlot())) {
            b.startCleaning();
            r.startCleaning();
        }
    }

    public void roombaDone(String bn, String rn) {
        Room r = rooms.getOrDefault(rn, null);
        Roomba b = roombas.getOrDefault(bn, null);
        if (r != null && b != null) {
            r.wasCleaned();
            b.isReady();
        }
    }

    public void roombaNewHome(String bn, String sn) {
        Roomba b = roombas.getOrDefault(bn, null);
        Station s = stations.getOrDefault(sn, null);
        if (s != null && b != null) {
            b.newHome(s);
        }
    }

    public void roombaGoHome(String bn) {
        Roomba b = roombas.getOrDefault(bn, null);
        if (b != null) {
            b.goHome();
        }
    }
}
