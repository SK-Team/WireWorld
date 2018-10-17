package dataHandling;

import elements.ElementConstans;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.List;

public class Drawer implements Runnable {

    public static final Color ELECTRON_HEAD_COLOR = Color.DEEPSKYBLUE;
    public static final Color ELECTRON_TAIL_COLOR = Color.INDIANRED;
    public static final Color CONDUCTOR_COLOR = Color.GOLD;
    public static final Color EMPTY_CELL_COLOR = Color.BLACK;
    public static final Color CELL_STROKE_COLOR = Color.WHITE;
    public static final double CELL_STROKE_LINE_WIDTH = 0.3;

    private GraphicsContext graphicsContext;
    private Color cellColor;
    private boolean[][] changes;
    private int[][] cells;

    public Drawer(GraphicsContext graphicsContext, boolean[][] changes, int[][] cells) {
        this.graphicsContext = graphicsContext;
        cellColor = Color.WHITE;
        this.graphicsContext.setStroke(CELL_STROKE_COLOR);
        this.graphicsContext.setLineWidth(CELL_STROKE_LINE_WIDTH);
        this.cells = cells;
        this.changes = changes;
    }

    public Drawer(GraphicsContext graphicsContext, int[][] cells) {
        this.graphicsContext = graphicsContext;
        cellColor = Color.WHITE;
        this.graphicsContext.setStroke(CELL_STROKE_COLOR);
        this.graphicsContext.setLineWidth(CELL_STROKE_LINE_WIDTH);
        this.cells = cells;
        changes = new boolean[Board.HEIGHT][Board.WIDTH];
        for (int i = 0; i < Board.HEIGHT; i++)
            for (int j = 0; j < Board.WIDTH; j++)
                changes[i][j] = true;
    }

    public Drawer(GraphicsContext graphicsContext, List<Point> points, int[][] cells) {
        this.graphicsContext = graphicsContext;
        cellColor = Color.WHITE;
        this.graphicsContext.setStroke(CELL_STROKE_COLOR);
        this.graphicsContext.setLineWidth(CELL_STROKE_LINE_WIDTH);
        this.cells = cells;
        changes = new boolean[Board.HEIGHT][Board.WIDTH];
        for (int i = 0; i < Board.HEIGHT; i++)
            for (int j = 0; j < Board.WIDTH; j++)
                changes[i][j] = true;
        for (Point p : points) {
            if (p.x < Board.HEIGHT && p.x > 0 && p.y > 0 && p.y < Board.WIDTH)
                changes[p.x][p.y] = true;
        }
    }

    public Drawer(GraphicsContext graphicsContext, Point point, int[][] cells) {
        this.graphicsContext = graphicsContext;
        cellColor = Color.WHITE;
        this.graphicsContext.setStroke(CELL_STROKE_COLOR);
        this.graphicsContext.setLineWidth(0);
        changes = new boolean[Board.HEIGHT][Board.WIDTH];
        this.cells = cells;
        for (int i = 0; i < Board.HEIGHT; i++)
            for (int j = 0; j < Board.WIDTH; j++)
                changes[i][j] = true;

        changes[point.y][point.x] = true;
    }

    public void run() {
        for (int i = 0; i < Board.HEIGHT; i++) {
            for (int j = 0; j < Board.WIDTH; j++) {
                if (changes[i][j]) {
                    cellColor = setColor(i, j, cells);
                    drawCell(i, j, graphicsContext, cellColor);
                }
            }
        }
    }

    public Color setColor(int i, int j, int[][] cells) {
        Color cellColor = EMPTY_CELL_COLOR;
        switch (cells[i][j]) {
            case ElementConstans.ELECTRON_HEAD:
                cellColor = ELECTRON_HEAD_COLOR;
                break;
            case ElementConstans.ELECTRON_TAIL:
                cellColor = ELECTRON_TAIL_COLOR;
                break;
            case ElementConstans.CONDUCTOR:
                cellColor = CONDUCTOR_COLOR;
                break;
        }
        return cellColor;
    }

    public void drawCell(int i, int j, GraphicsContext graphicsContext, Color color) {
        graphicsContext.setFill(color);
        graphicsContext.fillRect(j * Board.CELL_WIDTH_AND_HEIGHT, i * Board.CELL_WIDTH_AND_HEIGHT, Board.CELL_WIDTH_AND_HEIGHT,
                Board.CELL_WIDTH_AND_HEIGHT);
        graphicsContext.strokeRect(j * Board.CELL_WIDTH_AND_HEIGHT, i * Board.CELL_WIDTH_AND_HEIGHT, Board.CELL_WIDTH_AND_HEIGHT,
                Board.CELL_WIDTH_AND_HEIGHT);
    }

}

