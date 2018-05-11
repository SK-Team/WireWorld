package data;

import java.awt.Point;
import java.util.List;

import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;

public abstract class Element {

	private List<Point> location;
	private int type;

	public static final int ELECTRON_HEAD = 1;
	public static final int ELECTRON_TAIL = 2;
	public static final int CONDUCTOR = 3;
	public static final int EMPTY_CELL = 4;
	public static final int REVERSED_TYPE = 10;
	public static final int DEFAULT_TYPE = 11;
	public static final Color ELECTRON_HEAD_COLOR = Color.RED;
	public static final Color ELECTRON_TAIL_COLOR = new Color(1,1,0,1);
	public static final Color CONDUCTOR_COLOR = Color.BLACK;
	public static final Color EMPTY_CELL_COLOR = Color.WHITE;

	public abstract void createLocation(Point p);

	public abstract List<Point> getLocation();

	public void setLocation(List<Point> location) {
		this.location = location;
	}

	public abstract int getType();

}
