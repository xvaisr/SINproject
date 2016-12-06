package xrepik00.SINproject.GUIobjects;

import java.awt.*;

/**
 * Created by trepik on 5.12.2016.
 */
public class Station {
    private static final int r = 14;
    private Slot slot;
    private Room room;
    private Roomba roomba = null;

    Station(Room r, Slot s) {
        this.room = r;
        this.slot = s;
    }

    public Slot getSlot() {
        return this.slot;
    }


    public Roomba placeRoomba() {
        Roomba b = new Roomba(this);
        if (b != null) {
            this.roomba = b;
            return b;
        }
        return null;
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.darkGray);
        g.fillOval(this.slot.getX() - r / 2, this.slot.getY() - r / 2, r, r);
        g.setStroke(new BasicStroke(1));
        g.drawLine(this.slot.getX(), this.slot.getY(),
                this.room.getSlot().getX(), this.room.getSlot().getY());
    }

    public Room getRoom() {
        return this.room;
    }
}
