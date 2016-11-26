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

/**
 *
 * @author Roman Vais
 */
public class Building {

    private ArrayList<Room> rooms;

    public Building() {
        this.rooms = new ArrayList();
    }

    public List<Room> getRoomList() {
        return Collections.unmodifiableList(this.rooms);
    }

}
