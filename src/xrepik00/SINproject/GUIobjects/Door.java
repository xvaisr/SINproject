package xrepik00.SINproject.GUIobjects;

import java.awt.*;

/**
 * Created by trepik on 5.12.2016.
 */
public class Door {
    private Room r1;
    private Room r2;

    Door(Room r1, Room r2) {
        this.r1 = r1;
        this.r2 = r2;
    }

    public void draw(Graphics2D g) {
        g.drawLine(this.r1.getSlot().getX(), this.r1.getSlot().getY(),
                this.r2.getSlot().getX(), this.r2.getSlot().getY());
    }

    public Slot getCords1() {
        return r1.getSlot();
    }

    public Slot getCords2() {
        return r2.getSlot();
    }
}
