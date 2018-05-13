package data;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class AndGate extends Element {

    private List<Point> location;
    private int type;

    public AndGate(Point startPoint, int type) {
        this.type = type;
        location = new ArrayList<>();
        createLocation(startPoint);

    }

    public AndGate(Point startPoint) {
        this(startPoint, Element.DEFAULT_TYPE);
    }

    @Override
    public void createLocation(Point p) {
        int x = p.x;
        int y = p.y;

        if (type == Element.DEFAULT_TYPE) {
            location.add(p);
            for (int i = 1; i <= 12; i++)
                location.add(new Point(x + i, y));
            location.add(new Point(x + 13, y + 1));
            location.add(new Point(x + 14, y + 1));
            location.add(new Point(x + 15, y + 1));
            location.add(new Point(x + 10, y + 2));
            location.add(new Point(x + 12, y + 2));
            location.add(new Point(x + 16, y + 2));
            for (int i = 0; i < 6; i++)
                location.add(new Point(x + i, y + 3));
            location.add(new Point(x + 9, y + 3));
            location.add(new Point(x + 10, y + 3));
            location.add(new Point(x + 11, y + 3));
            location.add(new Point(x + 16, y + 3));
            location.add(new Point(x + 6, y + 4));
            location.add(new Point(x + 8, y + 4));
            location.add(new Point(x + 10, y + 4));
            location.add(new Point(x + 12, y + 4));
            location.add(new Point(x + 14, y + 4));
            location.add(new Point(x + 16, y + 4));
            location.add(new Point(x + 19, y + 4));
            location.add(new Point(x + 20, y + 4));
            location.add(new Point(x + 6, y + 5));
            location.add(new Point(x + 8, y + 5));
            location.add(new Point(x + 13, y + 5));
            location.add(new Point(x + 14, y + 5));
            location.add(new Point(x + 15, y + 5));
            location.add(new Point(x + 18, y + 5));
            location.add(new Point(x + 6, y + 6));
            location.add(new Point(x + 8, y + 6));
            location.add(new Point(x + 14, y + 6));
            location.add(new Point(x + 16, y + 6));
            location.add(new Point(x + 17, y + 6));
            location.add(new Point(x + 7, y + 7));
        } else if (type == Element.REVERSED_TYPE) {
            location.add(p);
            location.add(new Point(x + 13, y + 3));
            location.add(new Point(x + 3, y + 2));
            location.add(new Point(x + 4, y + 2));
            location.add(new Point(x + 6, y + 2));
            location.add(new Point(x + 12, y + 2));
            location.add(new Point(x + 14, y + 2));
            location.add(new Point(x + 2, y + 1));
            location.add(new Point(x + 5, y + 1));
            location.add(new Point(x + 6, y + 1));
            location.add(new Point(x + 7, y + 1));
            location.add(new Point(x + 12, y + 1));
            location.add(new Point(x + 14, y + 1));
            location.add(new Point(x + 1, y));
            location.add(new Point(x + 4, y));
            location.add(new Point(x + 6, y));
            location.add(new Point(x + 8, y));
            location.add(new Point(x + 10, y));
            location.add(new Point(x + 12, y));
            location.add(new Point(x + 14, y));
            location.add(new Point(x + 4, y - 1));
            location.add(new Point(x + 9, y - 1));
            location.add(new Point(x + 10, y - 1));
            location.add(new Point(x + 11, y - 1));
            for (int i = 15; i < 21; i++)
                location.add(new Point(x + i, y - 1));
            location.add(new Point(x + 4, y - 2));
            location.add(new Point(x + 8, y - 2));
            location.add(new Point(x + 10, y - 2));
            location.add(new Point(x + 5, y - 3));
            location.add(new Point(x + 6, y - 3));
            location.add(new Point(x + 7, y - 3));
            for (int i = 8; i < 21; i++)
                location.add(new Point(x + i, y - 4));
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

}
