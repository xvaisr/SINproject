package xrepik00.SINproject.GUIobjects;

import java.awt.*;


/**
 * Created by trepik on 5.12.2016.
 */
public class Room {
    private static final short[] n = {0, -100, 100, 0, 0, 100, -100, 0, 60, 60, 60, -60, -60, -60, -60, 60};
    private Slot slot;
    private int area;
    private Color color;
    private boolean selected;
    private int i = 0;

    public Room(int x, int y, int a) {
        this.slot = new Slot(x, y);
        this.area = a;
        this.color = Color.lightGray;
        this.selected = false;
    }

    public Room(Slot s, int a) {
        this.slot = s;
        s.fill();
        this.area = a;
        this.color = Color.lightGray;
        this.selected = false;
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
        if (this.selected) {
            g.setColor(Color.orange);
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
}
