package xrepik00.SINproject.GUIobjects;

import java.awt.*;

/**
 * Created by trepik on 5.12.2016.
 */
public class Roomba {
    private static final int r = 6;
    private Station home;
    private Slot slot;
    private Color color;

    Roomba(Station s) {
        this.home = s;
        this.slot = s.getSlot();
        this.color = Color.white;
    }

    public void draw(Graphics2D g) {
        g.setColor(this.color);
        g.fillOval(this.slot.getX() - r / 2, this.slot.getY() - r / 2, r, r);
    }


    public void moveViaDoor(Door d) {
        Slot c1 = d.getCords1();
        Slot c2 = d.getCords2();
        if (this.slot.equals(home.getSlot())) {
            this.leaveHome();
        }
        if (c1.equals(this.slot)) {
            this.slot = d.getCords2();
            System.out.println("bububu");
        } else if (c2.equals(this.slot)) {
            System.out.println("bububdskokadsou");
            this.slot = d.getCords1();
        } else {
            System.out.println("Wrong door");
        }
    }

    private void leaveHome() {
        Room r = this.home.getRoom();
        this.slot = r.getSlot();
    }

    private void goHome() {
        this.slot = this.home.getSlot();
    }
}
