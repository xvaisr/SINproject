package graphics.GUIobjects;

import java.awt.*;

/**
 * Created by trepik on 5.12.2016.
 */
public class Slot {
    private int x;
    private int y;
    private boolean empty;

    Slot(int x, int y) {
        this.x = x;
        this.y = y;
        this.empty = true;
    }

    int getX() {
        return this.x;
    }

    int getY() {
        return this.y;
    }

    public void fill() {
        this.empty = false;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(this.getClass()) && (this.x == ((Slot) obj).getX() && this.y == ((Slot) obj).getY());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.black);
        g.fillOval(this.x, this.y, 2, 2);
        g.drawString(this.x + " " + this.y, this.x, this.y);
    }
}
