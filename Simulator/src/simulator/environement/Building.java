/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.environement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import simulator.environement.rooms.Room;
import simulator.environement.rooms.impl.Enterance;
import simulator.utils.graph.Graph;
import simulator.utils.graph.Node;

/**
 *
 * @author Roman Vais
 */
public class Building {

    private ArrayList<Room> rooms;
    private Graph<Room> schema;

    public Building() {
        Room enterance = new Enterance();
        this.rooms = new ArrayList();
        this.rooms.add(enterance);
        this.schema = new Graph<>(new Node<>(enterance));
    }

    public List<Room> getRoomList() {
        return Collections.unmodifiableList(this.rooms);
    }

}
