package data;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Wire extends Element {

    private List<Point> location;
    private int type;
    private int length;

    public Wire(Point startPoint, int type, int length) {
        this.type = type;
        this.length = length;
        location = new ArrayList<>();
        createLocation(startPoint);

    }

    public Wire(Point startPoint) {
        this(startPoint, Element.DEFAULT_TYPE, 2);
    }

    public Wire(Point startPoint, int length){
        this(startPoint, Element.DEFAULT_TYPE, length);
    }

    @Override
    public void createLocation(Point p) {
        int x = p.x;
        int y = p.y;

        if (type == Element.DEFAULT_TYPE) {
            location.add(p);
            for( int i = 1; i < this.length; i++)
                location.add(new Point(x + i, y));


        } else if (type == Element.REVERSED_TYPE) {
            location.add(p);
            for( int i = 1; i < this.length; i++)
                location.add(new Point(x, y + i));

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

    public int getLength(){
        return length;
    }
    @Override
    public String toString() {
        Point p = location.get(0);
        char t = type == Element.DEFAULT_TYPE ? 'D' : 'R';
        return "Wire: " + p.x + "," + p.y + " " + t;
    }


}
