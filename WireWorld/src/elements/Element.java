package elements;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.List;

public abstract class Element {

    private List<Point> location;
    private int type;

    public abstract void createLocation(Point p);

    public abstract List<Point> getLocation();

    public void setLocation(List<Point> location) {
        this.location = location;
    }

    public abstract int getType();

    @Override
    public String toString() {
        Point p = location.get(0);
        char t = type == ElementConstans.DEFAULT_TYPE ? 'D' : 'R';
        return "Element: " + p.x + "," + p.y + " " + t;
    }
}
