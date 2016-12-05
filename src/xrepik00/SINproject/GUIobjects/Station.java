package xrepik00.SINproject.GUIobjects;

import java.awt.*;

/**
 * Created by trepik on 5.12.2016.
 */
public class Station {
    private static final int r = 8;
    private Cords cords;
    private Room room;
    private Roomba roomba = null;

    Station(Room r) {
        this.room = r;
        this.cords = r.neighbor();
    }

    public Cords getCords() {
        return this.cords;
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
        g.fillOval(this.cords.getX() - r / 2, this.cords.getY() - r / 2, r, r);
        g.setStroke(new BasicStroke(1));
        g.drawLine(this.cords.getX(), this.cords.getY(),
                this.room.getCords().getX(), this.room.getCords().getY());
    }
}
