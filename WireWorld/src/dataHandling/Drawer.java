package dataHandling;

import elements.Element;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.List;

public class Drawer implements Runnable {
    private GraphicsContext graphicsContext;
    private Color cellColor;
    private boolean[][] changes;
    private int[][] cells;

    public Drawer(GraphicsContext graphicsContext, boolean[][] changes, int[][] cells) {
        this.graphicsContext = graphicsContext;
        cellColor = Color.WHITE;
        this.graphicsContext.setStroke(Element.CELL_STROKE_COLOR);
        this.graphicsContext.setLineWidth(Element.CELL_STROKE_LINE_WIDTH);
        this.cells = cells;
        this.changes = changes;
    }

    public Drawer(GraphicsContext graphicsContext, int[][] cells) {
        this.graphicsContext = graphicsContext;
        cellColor = Color.WHITE;
        this.graphicsContext.setStroke(Element.CELL_STROKE_COLOR);
        this.graphicsContext.setLineWidth(Element.CELL_STROKE_LINE_WIDTH);
        this.cells = cells;
        changes = new boolean[Board.HEIGHT][Board.WIDTH];
        for (int i = 0; i < Board.HEIGHT; i++)
            for (int j = 0; j < Board.WIDTH; j++)
                changes[i][j] = true;
    }

    public Drawer(GraphicsContext graphicsContext, List<Point> points, int[][] cells) {
        this.graphicsContext = graphicsContext;
        cellColor = Color.WHITE;
        this.graphicsContext.setStroke(Element.CELL_STROKE_COLOR);
        this.graphicsContext.setLineWidth(Element.CELL_STROKE_LINE_WIDTH);
        this.cells = cells;
        changes = new boolean[Board.HEIGHT][Board.WIDTH];
        for (int i = 0; i < Board.HEIGHT; i++)
            for (int j = 0; j < Board.WIDTH; j++)
                changes[i][j] = true;
        for (Point p : points) {
            if (p.x < Board.HEIGHT && p.y < Board.WIDTH)
                changes[p.x][p.y] = true;
        }
    }

    public Drawer(GraphicsContext graphicsContext, Point point, int[][] cells) {
        this.graphicsContext = graphicsContext;
        cellColor = Color.WHITE;
        this.graphicsContext.setStroke(Element.CELL_STROKE_COLOR);
        this.graphicsContext.setLineWidth(Element.CELL_STROKE_LINE_WIDTH);
        changes = new boolean[Board.HEIGHT][Board.WIDTH];
        this.cells = cells;
        for (int i = 0; i < Board.HEIGHT; i++)
            for (int j = 0; j < Board.WIDTH; j++)
                changes[i][j] = true;

        changes[point.x][point.y] = true;
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
        Color cellColor = Element.EMPTY_CELL_COLOR;
        switch (cells[i][j]) {
            case Element.ELECTRON_HEAD:
                cellColor = Element.ELECTRON_HEAD_COLOR;
                break;
            case Element.ELECTRON_TAIL:
                cellColor = Element.ELECTRON_TAIL_COLOR;
                break;
            case Element.CONDUCTOR:
                cellColor = Element.CONDUCTOR_COLOR;
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

