package xrepik00.SINproject.GUIobjects;

import java.awt.*;

/**
 * Created by trepik on 5.12.2016.
 */
public class Roomba {
    private static final int r = 4;
    private Cords cords;
    private Color color;

    Roomba(Station s) {
        this.cords = s.getCords();
        this.color = Color.white;
    }

    public void draw(Graphics2D g) {
        g.setColor(this.color);
        g.fillOval(this.cords.getX() - r / 2, this.cords.getY() - r / 2, r, r);
    }


    public void moveViaDoor(Door d) {
        Cords c1 = d.getCords1();
        Cords c2 = d.getCords2();
        if (c1.equals(this.cords)) {
            this.cords = d.getCords2();
            System.out.println("bububu");
        } else if (c2.equals(this.cords)) {
            System.out.println("bububdskokadsou");
            this.cords = d.getCords1();
        } else {
            System.out.println("Wrong door");
        }
    }
}
