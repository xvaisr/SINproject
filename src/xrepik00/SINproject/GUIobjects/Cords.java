package xrepik00.SINproject.GUIobjects;

/**
 * Created by trepik on 5.12.2016.
 */
public class Cords {
    private int x;
    private int y;

    Cords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getX() {
        return this.x;
    }

    int getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(this.getClass()) && (this.x == ((Cords) obj).getX() && this.y == ((Cords) obj).getY());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
