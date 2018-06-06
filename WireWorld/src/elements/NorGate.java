package elements;

import elements.Element;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class NorGate extends Element {

    private List<Point> location;
    private int type;

    public NorGate(Point startPoint, int type) {
        this.type = type;
        location = new ArrayList<>();
        createLocation(startPoint);

    }

    public NorGate(Point startPoint) {
        this(startPoint, Element.DEFAULT_TYPE);
    }

    @Override
    public void createLocation(Point p) {
        int x = p.x;
        int y = p.y;

        if (type == Element.DEFAULT_TYPE) {
            location.add(p);
            location.add(new Point(x + 7, y - 2));
            location.add(new Point(x + 8, y - 2));
            location.add(new Point(x + 6, y - 1));
            location.add(new Point(x + 9, y - 1));
            for (int i = 1; i <= 5; i++)
                location.add(new Point(x + i, y));
            for (int i = 8; i <= 11; i++)
                location.add(new Point(x + i, y));
            location.add(new Point(x + 8, y + 1));
            for (int i = 10; i <= 20; i++)
                location.add(new Point(x + i, y + 1));
            for (int i = 0; i <= 5; i++)
                location.add(new Point(x + i, y + 2));
            for (int i = 8; i <= 11; i++)
                location.add(new Point(x + i, y + 2));
            location.add(new Point(x + 9, y + 3));
            location.add(new Point(x + 6, y + 3));
            location.add(new Point(x + 7, y + 4));
            location.add(new Point(x + 8, y + 4));

        } else if (type == Element.REVERSED_TYPE) {
            location.add(p);
            location.add(new Point(x + 12, y - 3));
            location.add(new Point(x + 13, y - 3));
            location.add(new Point(x + 11, y - 2));
            location.add(new Point(x + 6, y - 2));
            for (int i = 9; i <= 12; i++)
                location.add(new Point(x + i, y - 1));
            for (int i = 15; i <= 20; i++)
                location.add(new Point(x + i, y - 1));
            for (int i = 1; i <= 10; i++)
                location.add(new Point(x + i, y));
            location.add(new Point(x + 12, y));
            for (int i = 9; i <= 12; i++)
                location.add(new Point(x + i, y + 1));
            for (int i = 15; i <= 20; i++)
                location.add(new Point(x + i, y + 1));
            location.add(new Point(x + 11, y + 2));
            location.add(new Point(x + 6, y + 2));
            location.add(new Point(x + 12, y + 3));
            location.add(new Point(x + 13, y + 3));
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
        return "NOR: " + p.x + "," + p.y + " " + t;
    }

}

