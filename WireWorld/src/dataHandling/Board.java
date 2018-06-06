package dataHandling;

import elements.*;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    public static final int WIDTH = 60;
    public static final int HEIGHT = 50;
    public static final int CELL_WIDTH_AND_HEIGHT = 10;

    private List<Element> elements;
    private int[][] cells; // zmiana w stosunku do specyfikacji: zamiast points jest cells

    public Board() {
        elements = new ArrayList<>();
        cells = new int[HEIGHT][WIDTH]; // ze wzgledu na roznice w orientacji ukladu wspolrzednych w tablicy i na
        // planszy, odwracam spolrzedne
        resetPoints();
    }

    public Board(String inputFile) throws IOException, WrongInputFileException {
        this();
        readBoardFromFile(inputFile);
    }

    private void resetPoints() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                cells[i][j] = ElementConstans.EMPTY_CELL;
            }
        }
    }

    private void closeResourcesAfterReading(FileReader fileReader, BufferedReader bufferedReader) throws IOException {
        if (bufferedReader != null) {
            bufferedReader.close();
        }
    }

    public void readBoardFromFile(String path) throws IOException, WrongInputFileException {
        FileReader fileReader = new FileReader(new File(path));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        int x, y;
        Element element;
        while ((line = bufferedReader.readLine()) != null) {
            String[] split = line.split(" ");
            element = null;
            if (split.length >= 2) {
                try {
                    Point point = InputFileParser.parseCoordinatesFromLineSplit(split);
                    x = point.x;
                    y = point.y;
                } catch (WrongInputFileException e) {
                    closeResourcesAfterReading(fileReader, bufferedReader);
                    throw new WrongInputFileException();
                }
                Integer tmpCellState = InputFileParser.parseCellStateFromLineSplit(split);
                if (tmpCellState != null) {
                    cells[y][x] = tmpCellState;
                    continue;
                }
                element = InputFileParser.parseWireFromLineSplit(split, new Point(x, y));
                if (element == null) {
                    element = InputFileParser.parseElementExceptWireFromLineSplit(split, new Point(x, y));
                }
                if (element != null) {
                    elements.add(element);
                    fillWithElement(element);
                } else {
                    closeResourcesAfterReading(fileReader, bufferedReader);
                    throw new WrongInputFileException();
                }
            } else {
                closeResourcesAfterReading(fileReader, bufferedReader);
                throw new WrongInputFileException();
            }
        }
        closeResourcesAfterReading(fileReader, bufferedReader);
    }

    public int fillWithElement(Element e) {
        List<Point> elementLocation = e.getLocation();

        for (Point p : elementLocation) {
            if (p.x >= 0 && p.x < WIDTH && p.y >= 0 && p.y < HEIGHT) {
                cells[p.y][p.x] = ElementConstans.CONDUCTOR;
                // celowe wstawianie w ten sposob, zeby nie "odwrocic" ukladu
                // wspolrzednych
            } else {
                // po prostu nie wstawiamy tam przewodnika - uzyskamy efekt przesuniecia za
                // plansze, ale nie wyrzucimy bledu
            }

        }

        return 0;
    }

    public int printBoardToFile(String path) throws IOException {
        FileWriter fileWriter = new FileWriter(new File(path));
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        int[][] cellsToPrintWithoutElements = copyCells();

        for (Element e : elements) {

            bufferedWriter.write(e.toString());
            bufferedWriter.newLine();

            // peta, ktora zapobiega duplikacji w pliku wynikowym (gdyby nie ona, dla
            // kazdego punktu zajmowanego przez element, program wypisywalby dodatkowo
            // przewodnik)

            for (Point p : e.getLocation()) {
                if (cells[p.x][p.y] == ElementConstans.CONDUCTOR) {
                    cellsToPrintWithoutElements[p.x][p.y] = ElementConstans.EMPTY_CELL;
                }
            }

        }
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                switch (cellsToPrintWithoutElements[i][j]) {
                    case ElementConstans.ELECTRON_HEAD:
                        bufferedWriter.write("ElectronHead: " + j + "," + i);
                        bufferedWriter.newLine();
                        break;
                    case ElementConstans.ELECTRON_TAIL:
                        bufferedWriter.write("ElectronTail: " + j + "," + i);
                        bufferedWriter.newLine();
                        break;
                    case ElementConstans.CONDUCTOR:
                        bufferedWriter.write("Conductor: " + j + "," + i);
                        bufferedWriter.newLine();
                        break;
                }
            }
        }

        bufferedWriter.close();
        return 0;
    }


    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int[][] getCells() {
        return cells;
    }

    private void setCellsFromArrayWithWrongDimensions(int[][] someCells) {
        cells = new int[HEIGHT][WIDTH];
        resetPoints();
        for (int i = 0; i < someCells.length; i++) {
            for (int j = 0; j < someCells[i].length; j++) {
                cells[i][j] = someCells[i][j];
            }
        }

    }

    public void setCells(int[][] cells) {
        if (cells.length != HEIGHT) {
            setCellsFromArrayWithWrongDimensions(cells);
            return;
        }
        for (int i = 0; i < cells.length; i++) {
            if (cells[i].length != WIDTH) {
                setCellsFromArrayWithWrongDimensions(cells);
                return;
            }
        }
        this.cells = cells;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public int[][] copyCells() {
        int[][] copy = new int[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            copy[i] = Arrays.copyOf(cells[i], WIDTH);
        }
        return copy;
    }

    public void printToConsole() {
        for (int i = 0; i < HEIGHT; i++) {
            System.out.println();
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(cells[i][j] + " ");
            }
        }
    }


    public void addToBoard(double x, double y, int cellType, Canvas canvas) {

        int type = ElementConstans.DEFAULT_TYPE;
        if (cellType == ElementConstans.ELECTRON_HEAD || cellType == ElementConstans.ELECTRON_TAIL ||
                cellType == ElementConstans.CONDUCTOR || cellType == ElementConstans.EMPTY_CELL) {
            int cells[][] = getCells();
            cells[(int) (y / CELL_WIDTH_AND_HEIGHT)][(int) (x / CELL_WIDTH_AND_HEIGHT)]
                    = cellType;
            setCells(cells);
            drawCellToCanvas(canvas, new Point((int) (x / CELL_WIDTH_AND_HEIGHT),
                    (int) (y / CELL_WIDTH_AND_HEIGHT)));

        } else if (cellType == ElementConstans.DIODE || cellType == ElementConstans.AND_GATE ||
                cellType == ElementConstans.OR_GATE || cellType == ElementConstans.NOR_GATE) {
            Element e = null;

            if (cellType == ElementConstans.DIODE) {
                e = new Diode(new Point((int) x / CELL_WIDTH_AND_HEIGHT,
                        (int) y / CELL_WIDTH_AND_HEIGHT), type);
            } else if (cellType == ElementConstans.AND_GATE) {
                e = new AndGate(new Point((int) x / CELL_WIDTH_AND_HEIGHT,
                        (int) y / CELL_WIDTH_AND_HEIGHT), type);
            } else if (cellType == ElementConstans.OR_GATE) {
                e = new OrGate(new Point((int) x / CELL_WIDTH_AND_HEIGHT,
                        (int) y / CELL_WIDTH_AND_HEIGHT), type);
            } else if (cellType == ElementConstans.NOR_GATE) {
                e = new NorGate(new Point((int) x / CELL_WIDTH_AND_HEIGHT,
                        (int) y / CELL_WIDTH_AND_HEIGHT), type);

            }
            getElements().add(e);
            fillWithElement(e);
            drawElementToCanvas(canvas, e.getLocation());
        }
    }

    public void drawBoardToCanvas(Canvas canvas) {
        System.out.println("DrawBoardToCanvas called 2");
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        Drawer drawer = new Drawer(graphicsContext, cells);
        Platform.runLater(drawer);

    }

    public void drawBoardToCanvas(Canvas canvas, boolean[][] changes) {
        System.out.println("DrawBoardToCanvas called 1");
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        Drawer drawer = new Drawer(graphicsContext, changes, cells);
        Platform.runLater(drawer);
    }

    public void drawElementToCanvas(Canvas canvas, List<Point> location) {
        System.out.println("drawElementToCanvas called");
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        Drawer drawer = new Drawer(graphicsContext, location, cells);
        Platform.runLater(drawer);

    }

    public void drawCellToCanvas(Canvas canvas, Point point) {
        System.out.println("drawCellToCanvas called");
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        Drawer drawer = new Drawer(graphicsContext, point, cells);
        Platform.runLater(drawer);

    }

}
