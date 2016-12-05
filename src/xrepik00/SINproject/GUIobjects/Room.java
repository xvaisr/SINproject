package xrepik00.SINproject.GUIobjects;

import java.awt.*;


/**
 * Created by trepik on 5.12.2016.
 */
public class Room {
    private static final short[] n = {0, -100, 100, 0, 0, 100, -100, 0, 60, 60, 60, -60, -60, -60, -60, 60};
    private Cords cords;
    private int area;
    private Color color;
    private boolean selected;
    private int i = 0;

    public Room(int x, int y, int a) {
        this.cords = new Cords(x, y);
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
        g.fillOval(this.cords.getX() - this.area / 2, this.cords.getY() - this.area / 2,
                this.area, this.area);
        if (this.selected) {
            g.setColor(Color.orange);
            g.setStroke(new BasicStroke(4));
            g.drawOval(this.cords.getX() - this.area / 2, this.cords.getY() - this.area / 2,
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

    public Cords neighbor() {
        return new Cords(this.cords.getX() + n[this.i++], this.cords.getY() + n[this.i++]);
    }

    public Cords getCords() {
        return this.cords;
    }
}
