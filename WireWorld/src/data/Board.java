package data;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board {

    public static final int WIDTH = 60;
    public static final int HEIGHT = 50;
    public static final int CELL_WIDTH_AND_HEIGHT = 10;
    private final int WRONG_INPUT_FILE_FORMAT = 1;

    private List<Element> elements;
    private int[][] cells; // zmiana w stosunku do specyfikacji: zamiast points jest cells

    private Rectangle[][] rectangles;

    public Board() {
        elements = new ArrayList<>();
        cells = new int[HEIGHT][WIDTH]; // ze wzgl�du na r�nic� w orientacji uk�adu wsp�rz�dnych w tablicy i na
        // planszy
        rectangles = new Rectangle[HEIGHT][WIDTH];
        prepareRectangles();
        resetPoints();
    }

    public Board(String inputFile) throws IOException {
        this();
        int result = readBoardFromFile(inputFile);
    }

    private void resetPoints() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                cells[i][j] = Element.EMPTY_CELL;
            }
        }
    }

    private void closeResourcesAfterReading(FileReader fileReader, BufferedReader bufferedReader) throws IOException {
        if (bufferedReader != null) {
            bufferedReader.close();
        }
    }

    private void prepareRectangles() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                rectangles[i][j] = new Rectangle(j * CELL_WIDTH_AND_HEIGHT, i * CELL_WIDTH_AND_HEIGHT,
                        CELL_WIDTH_AND_HEIGHT, CELL_WIDTH_AND_HEIGHT);
            }
        }
    }

    public int readBoardFromFile(String path) throws IOException { // nale�y doda� obs�ug� wszystkich element�w opr�cz
        // diody
        FileReader fileReader = new FileReader(new File(path));
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        int type;
        int length;
        int x, y;
        String[] xySplit;
        while ((line = bufferedReader.readLine()) != null) {
            String[] split = line.split(" ");
            if (split.length >= 2) {
                xySplit = split[1].split(",");
                xySplit[1] = xySplit[1].trim();
                System.out.println("XYSPLIT: ");
                for (String s : xySplit) {
                    System.out.println(s);
                }
                if (xySplit.length == 2) {
                    x = Integer.valueOf(xySplit[0]);
                    y = Integer.valueOf(xySplit[1]);
                    // celowe odwr�cenie wsp�rz�dnych,poniewa� tablica
                    // ma na osi pionowej x, np.cells[2][3] oznacza pkt (3,2) na planszy
                    if (x < 0 || x > HEIGHT || y < 0 || y > WIDTH) {
                        closeResourcesAfterReading(fileReader, bufferedReader);
                        return WRONG_INPUT_FILE_FORMAT;
                    }
                } else {
                    closeResourcesAfterReading(fileReader, bufferedReader);
                    return WRONG_INPUT_FILE_FORMAT;
                }

                if (split[0].equals("ElectronHead:")) {
                    cells[y][x] = Element.ELECTRON_HEAD; // celowe wstawianie w ten spos�b, �eby nie "odwr�ci�" uk�adu
                    // wsp�rz�dnych
                } else if (split[0].equals("ElectronTail:")) {
                    cells[y][x] = Element.ELECTRON_TAIL;
                } else if (split[0].equals("Conductor:")) {
                    cells[y][x] = Element.CONDUCTOR;
                } else if (split[0].equals("Blank:")) {
                    cells[y][x] = Element.EMPTY_CELL;
                } else if (split[0].equals("Wire:")) {
                    length = 2;
                    type = Element.DEFAULT_TYPE;
                    if (split.length >= 3) {
                        if (split[2].equals("R")) {
                            type = Element.REVERSED_TYPE;
                        } else if (split[2].equals("D")) {
                            type = Element.DEFAULT_TYPE;
                        } else {
                            length = Integer.valueOf(split[2]);
                        }
                    }
                    if (split.length >= 4) {
                        if (split[3].equals("R")) {
                            type = Element.REVERSED_TYPE;
                        } else if (split[2].equals("D")) {
                            type = Element.DEFAULT_TYPE;
                        }
                    }
                    System.out.println("Mamy kabelek");
                    Wire w = new Wire(new Point(x, y), type, length);
                    elements.add(w);
                    fillWithElement(w);

                } else {

                    if (split.length >= 3) {
                        split[2] = split[2].trim();
                        if (split[2].equals("R")) {
                            type = Element.REVERSED_TYPE;
                        } else if (split[2].equals("D")) {
                            type = Element.DEFAULT_TYPE;
                        } else {
                            closeResourcesAfterReading(fileReader, bufferedReader);
                            return WRONG_INPUT_FILE_FORMAT;
                        }

                    } else {
                        type = Element.DEFAULT_TYPE;
                    }
                    if (split[0].equals("Diode:")) {
                        System.out.println("Mamy diod�");
                        Diode d = new Diode(new Point(x, y), type);
                        elements.add(d);
                        fillWithElement(d);
                    } else if (split[0].equals("OR:")) {
                        System.out.println("Mamy OR");
                        OrGate o = new OrGate(new Point(x, y), type);
                        elements.add(o);
                        fillWithElement(o);
                    } else if (split[0].equals("NOR:")) {
                        System.out.println("Mamy NOR");
                        NorGate n = new NorGate(new Point(x, y), type);
                        elements.add(n);
                        fillWithElement(n);
                    } else if (split[0].equals("AND:")) {
                        System.out.println("Mamy AND");
                        AndGate a = new AndGate(new Point(x, y), type);
                        elements.add(a);
                        fillWithElement(a);
                    }
                }

            }

        }

        closeResourcesAfterReading(fileReader, bufferedReader);
        return 1;
    }

    public int fillWithElement(Element e) {
        List<Point> elementLocation = e.getLocation();

        for (Point p : elementLocation) {
            System.out.println("Pkt do wype�nienia: " + p.x + " " + p.y);
            if (p.x >= 0 && p.x < WIDTH && p.y >= 0 && p.y < HEIGHT) {
                cells[p.y][p.x] = Element.CONDUCTOR;
                System.out.println("Wypelnieniono: " + p.x + " " + p.y);
                // celowe wstawianie w ten spos�b, �eby nie "odwr�ci�" uk�adu
                // wsp�rz�dnych
            } else {
                // po prostu nie wstawiamy tam przewodnika - uzyskamy efekt przesuni�cia za
                // plansz�, ale nie wyrzucimy b��du
            }

        }

        return 1;
    }

    public int printBoardToFile(String path) throws IOException {
        FileWriter fileWriter = new FileWriter(new File(path));
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        int[][] cellsToPrintWithoutElements = copyCells();

        for (Element e : elements) {
            int x = e.getLocation().get(0).x;
            int y = e.getLocation().get(0).y;
            char type = e.getType() == Element.REVERSED_TYPE ? 'R' : 'D';
            if (e instanceof Diode) { // mo�na poprawi� tworzenie napisu -> stworzy� za pomoc� StringBuildera
                bufferedWriter.write("Diode: " + x + "," + y + " " + type);
                bufferedWriter.newLine();
            } else if (e instanceof NorGate) {
                bufferedWriter.write("NOR: " + x + "," + y + " " + type);
                bufferedWriter.newLine();
            } else if (e instanceof OrGate) {
                bufferedWriter.write("OR: " + x + "," + y + " " + type);
                bufferedWriter.newLine();
            } else if (e instanceof AndGate) {
                bufferedWriter.write("AND: " + x + "," + y + " " + type);
                bufferedWriter.newLine();
            } else if (e instanceof Wire) {
                int length = ((Wire) e).getLength();
                bufferedWriter.write("Wire: " + x + "," + y + " " + length + " " + type);
                bufferedWriter.newLine();
            }
            // pętla, która zapobiega duplikacji w pliku wynikowym (gdyby nie ona, dla
            // każdego punktu zajmowanego przez element, program wypisywalby dodatkowo
            // przewodnik)
            for (Point p : e.getLocation()) {
                if (cells[p.x][p.y] == Element.CONDUCTOR) {
                    cellsToPrintWithoutElements[p.x][p.y] = Element.EMPTY_CELL;
                }
            }

        }
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                switch (cellsToPrintWithoutElements[i][j]) {
                    case Element.ELECTRON_HEAD:
                        bufferedWriter.write("ElectronHead: " + j + "," + i);
                        bufferedWriter.newLine();
                        break;
                    case Element.ELECTRON_TAIL:
                        bufferedWriter.write("ElectronTail: " + j + "," + i);
                        bufferedWriter.newLine();
                        break;
                    case Element.CONDUCTOR:
                        bufferedWriter.write("Conductor: " + j + "," + i);
                        bufferedWriter.newLine();
                        break;
                    /*
                     * case Element.EMPTY_CELL: bufferedWriter.write("Blank: " + j + "," + i);
                     * bufferedWriter.newLine(); break;
                     */
                }
            }
        }

        bufferedWriter.close();
        return 1;
    }

    public void drawBoardToCanvas(Canvas canvas) {
        System.out.println("DrawBoardToCanvas called");
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        DrawBoardToCanvasRunnable drawBoardRunnable = new DrawBoardToCanvasRunnable(graphicsContext);
        Platform.runLater(drawBoardRunnable);

    }

    public void drawElementToCanvas(Canvas canvas, List<Point> location) {
        System.out.println("drawElementToCanvas called");
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        DrawElementToCanvasRunnable drawElementToCanvasRunnable = new DrawElementToCanvasRunnable(graphicsContext, location);
        Platform.runLater(drawElementToCanvasRunnable);

    }

    public void drawCellToCanvas(Canvas canvas, Point point) {
        System.out.println("drawCellToCanvas called");
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        DrawCellToCanvasRunnable drawCellToCanvasRunnable = new DrawCellToCanvasRunnable(graphicsContext, point);
        Platform.runLater(drawCellToCanvasRunnable);

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

    public void setCells(int[][] cells) {
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
            System.out.println("");
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(cells[i][j] + " ");
            }
        }
    }

    class DrawBoardToCanvasRunnable implements Runnable {
        private GraphicsContext graphicsContext;
        private Color cellColor;

        public DrawBoardToCanvasRunnable(GraphicsContext graphicsContext) {
            this.graphicsContext = graphicsContext;
            cellColor = Color.WHITE;
            this.graphicsContext.setStroke(Element.CELL_STROKE_COLOR);
            this.graphicsContext.setLineWidth(Element.CELL_STROKE_LINE_WIDTH);
        }

        public void run() {
//            Rectangle rectToDraw;
            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {

                    cellColor =  setColor(i, j);

                    drawCell(i, j, graphicsContext, cellColor);

                    /*graphicsContext.setFill(cellColor);
                    graphicsContext.setStroke(Element.CELL_STROKE_COLOR);
                    graphicsContext.setLineWidth(Element.CELL_STROKE_LINE_WIDTH);
//                    rectToDraw = rectangles[i][j];
                    graphicsContext.fillRect(j * CELL_WIDTH_AND_HEIGHT, i*CELL_WIDTH_AND_HEIGHT, CELL_WIDTH_AND_HEIGHT,
                            CELL_WIDTH_AND_HEIGHT);
                    graphicsContext.strokeRect(j * CELL_WIDTH_AND_HEIGHT, i*CELL_WIDTH_AND_HEIGHT, CELL_WIDTH_AND_HEIGHT,
                            CELL_WIDTH_AND_HEIGHT);
//                    graphicsContext.fillRect(rectToDraw.getX(), rectToDraw.getY(), rectToDraw.getWidth(),
//                            rectToDraw.getHeight());
//                    graphicsContext.strokeRect(rectToDraw.getX(), rectToDraw.getY(), rectToDraw.getWidth(),
//                            rectToDraw.getHeight());*/

                }
            }
        }
    }

    class DrawElementToCanvasRunnable implements Runnable {
        private GraphicsContext graphicsContext;
        private Color cellColor;
        private List<Point> location;

        public DrawElementToCanvasRunnable(GraphicsContext graphicsContext, List<Point> location) {
            this.graphicsContext = graphicsContext;
            cellColor = Element.EMPTY_CELL_COLOR;
            this.location = location;
            this.graphicsContext.setStroke(Element.CELL_STROKE_COLOR);
            this.graphicsContext.setLineWidth(Element.CELL_STROKE_LINE_WIDTH);

        }

        public void run() {

            for (Point p: location) {
                int i = (int) p.getY();
                int j = (int) p.getX();
                System.out.println(i + " " + j);

                cellColor =  Element.CONDUCTOR_COLOR;

                drawCell(i, j, graphicsContext, cellColor);
            }
        }
    }

    class DrawCellToCanvasRunnable implements Runnable {
        private GraphicsContext graphicsContext;
        private Color cellColor;
        private Point point;

        public DrawCellToCanvasRunnable(GraphicsContext graphicsContext, Point point) {
            this.graphicsContext = graphicsContext;
            cellColor = Element.EMPTY_CELL_COLOR;
            this.point = point;
            this.graphicsContext.setStroke(Element.CELL_STROKE_COLOR);
            this.graphicsContext.setLineWidth(Element.CELL_STROKE_LINE_WIDTH);

        }

        public void run() {

                int i = (int) point.getY();
                int j = (int) point.getX();
                System.out.println(i + " " + j);

                cellColor =  setColor(i, j);

                drawCell(i, j, graphicsContext, cellColor);
        }
    }


    public Color setColor(int i, int j) {
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

    public void drawCell(int i, int j, GraphicsContext graphicsContext, Color color){
        graphicsContext.setFill(color);
        graphicsContext.fillRect(j * CELL_WIDTH_AND_HEIGHT, i*CELL_WIDTH_AND_HEIGHT, CELL_WIDTH_AND_HEIGHT,
                CELL_WIDTH_AND_HEIGHT);
        graphicsContext.strokeRect(j * CELL_WIDTH_AND_HEIGHT, i*CELL_WIDTH_AND_HEIGHT, CELL_WIDTH_AND_HEIGHT,
                CELL_WIDTH_AND_HEIGHT);
    }
}
