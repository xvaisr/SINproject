package xrepik00.SINproject.GUIobjects;

import java.awt.*;


/**
 * Created by trepik on 5.12.2016.
 */
public class Room {
    private Slot slot;
    private int area;
    private Color color;
    private boolean selected;
    private boolean occupied;

    public Room(Slot s, int a) {
        this.slot = s;
        s.fill();
        this.area = a;
        this.color = Color.lightGray;
        this.selected = false;
        this.occupied = false;
    }

    public void select() {
        this.selected = true;
    }

    public void deselect() {
        this.selected = false;
    }

    public void draw(Graphics2D g) {
        g.setColor(this.color);
        g.fillOval(this.slot.getX() - this.area / 2, this.slot.getY() - this.area / 2,
                this.area, this.area);
        if (this.occupied) {
            g.setColor(Color.magenta);
            if (this.selected) {
                g.setColor(Color.orange);
            }
            g.setStroke(new BasicStroke(4));
            g.drawOval(this.slot.getX() - this.area / 2, this.slot.getY() - this.area / 2,
                    this.area, this.area);
        }
    }

    public void startCleaning() {
        this.color = Color.cyan;
    }

    public void wasCleaned() {
        this.color = Color.lightGray;
    }

    public void makeSomeMess() {
        this.color = Color.pink;
    }

    public Slot getSlot() {
        return this.slot;
    }

    public void occupy() {
        this.occupied = true;
    }

    public void clear() {
        this.occupied = false;
    }
}
