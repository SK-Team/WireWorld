package data;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class OrGate extends Element {

    private List<Point> location;
    private int type;

    public OrGate(Point startPoint, int type) {
        this.type = type;
        location = new ArrayList<>();
        createLocation(startPoint);

    }

    public OrGate(Point startPoint) {
        this(startPoint, Element.DEFAULT_TYPE);
    }

    @Override
    public void createLocation(Point p) {
        int x = p.x;
        int y = p.y;

        if (type == Element.DEFAULT_TYPE) {
            location.add(p);
            location.add(new Point(x + 6, y - 1));
            location.add(new Point(x + 7, y - 1));
            for (int i = 1; i <= 5; i++)
                location.add(new Point(x + i, y));
            location.add(new Point(x + 8, y));
            for (int i = 7; i <= 20; i++)
                location.add(new Point(x + i, y + 1));
            for (int i = 0; i <= 5; i++)
                location.add(new Point(x + i, y + 2));
            location.add(new Point(x + 8, y + 2));
            location.add(new Point(x + 6, y + 3));
            location.add(new Point(x + 7, y + 3));

        } else if (type == Element.REVERSED_TYPE) {
            location.add(p);
            location.add(new Point(x + 13, y - 2));
            location.add(new Point(x + 14, y - 2));
            location.add(new Point(x + 12, y - 1));
            for (int i = 15; i <= 20; i++)
                location.add(new Point(x + i, y - 1));
            for (int i = 1; i <= 13; i++)
                location.add(new Point(x + i, y));
            location.add(new Point(x + 12, y + 1));
            for (int i = 15; i <= 20; i++)
                location.add(new Point(x + i, y + 1));
            location.add(new Point(x + 13, y + 2));
            location.add(new Point(x + 14, y + 2));
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
        char t = type == Element.DEFAULT_TYPE ? 'D' : 'R';
        return "OR: " + p.x + "," + p.y + " " + t;
    }

}
