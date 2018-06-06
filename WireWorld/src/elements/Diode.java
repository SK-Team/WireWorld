package elements;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Diode extends Element {

    private List<Point> location;
    private int type;

    public Diode(Point startPoint, int type) {
        this.type = type;
        location = new ArrayList<>();
        createLocation(startPoint);

    }

    public Diode(Point startPoint) {
        this(startPoint, ElementConstans.DEFAULT_TYPE);
    }

    @Override
    public void createLocation(Point p) { // dioda ma po trzy przewodniki z obu stron
        int x = p.x;
        int y = p.y;

        if (type == ElementConstans.DEFAULT_TYPE) {
            location.add(p);
            location.add(new Point(x + 1, y));
            location.add(new Point(x + 2, y));
            location.add(new Point(x + 3, y));
            location.add(new Point(x + 5, y));
            location.add(new Point(x + 6, y));
            location.add(new Point(x + 7, y));
            location.add(new Point(x + 3, y - 1));
            location.add(new Point(x + 3, y + 1));
            location.add(new Point(x + 4, y - 1));
            location.add(new Point(x + 4, y + 1));
        } else if (type == ElementConstans.REVERSED_TYPE) {
            location.add(p);
            location.add(new Point(x + 1, y));
            location.add(new Point(x + 2, y));
            location.add(new Point(x + 4, y));
            location.add(new Point(x + 5, y));
            location.add(new Point(x + 6, y));
            location.add(new Point(x + 7, y));
            location.add(new Point(x + 3, y + 1));
            location.add(new Point(x + 3, y - 1));
            location.add(new Point(x + 4, y + 1));
            location.add(new Point(x + 4, y - 1));
        }

    }

    @Override
    public List<Point> getLocation() {
        return location;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        Point p = location.get(0);
        char t = type == ElementConstans.DEFAULT_TYPE ? 'D' : 'R';
        return "Diode: " + p.x + "," + p.y + " " + t;
    }
}
