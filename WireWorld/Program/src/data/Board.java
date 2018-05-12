package data;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Board {

	private final int WIDTH = 20;
	private final int HEIGHT = 20;
	private final int CELL_WIDTH_AND_HEIGHT = 10;
	private final int WRONG_INPUT_FILE_FORMAT = 1;

	private List<Element> elements;
	private int[][] cells; // zmiana w stosunku do specyfikacji: zamiast points jest cells

	private Rectangle[][] rectangles;

	public Board() {
		elements = new ArrayList<>();
		cells = new int[HEIGHT][WIDTH]; // ze wzglêdu na ró¿nicê w orientacji uk³adu wspó³rzêdnych w tablicy i na
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
			for (int j = 0; j <WIDTH; j++) {
				cells[i][j] = Element.EMPTY_CELL;
			}
		}
	}

	private void closeResourcesAfterReading(FileReader fileReader, BufferedReader bufferedReader) throws IOException {
		if (fileReader != null) {
			fileReader.close();
		}
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

	public int readBoardFromFile(String path) throws IOException {
		FileReader fileReader = new FileReader(new File(path));
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line;
		int type;
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
					// celowe odwrócenie wspó³rzêdnych,poniewa¿ tablica
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
					cells[y][x] = Element.ELECTRON_HEAD; // celowe wstawianie w ten sposób, ¿eby nie "odwróciæ" uk³adu
															// wspó³rzêdnych
				} else if (split[0].equals("ElectronTail:")) {
					cells[y][x] = Element.ELECTRON_TAIL;
				} else if (split[0].equals("Conductor:")) {
					cells[y][x] = Element.CONDUCTOR;
				} else if (split[0].equals("Blank:")) {
					cells[y][x] = Element.EMPTY_CELL;
				} else if (split[0].equals("Wire:")) {
					// obs³u¿yæ!
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
						System.out.println("Mamy diodê");
						Diode d = new Diode(new Point(x, y), type);
						elements.add(d);
						fillWithElement(d);
					} else if (split[0].equals("OR:")) {

					} else if (split[0].equals("NOR:")) {

					} else if (split[0].equals("AND:")) {

					} else if (split[0].equals("Wire:")) {

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
			System.out.println("Pkt do wype³nienia: " + p.x + " " + p.y);
			if (p.x >= 0 && p.x < HEIGHT & p.y >= 0 && p.y < WIDTH) {
				cells[p.y][p.x] = Element.CONDUCTOR; // celowe wstawianie w ten sposób, ¿eby nie "odwróciæ" uk³adu
														// wspó³rzêdnych
			} else {
				// po prostu nie wstawiamy tam przewodnika - uzyskamy efekt przesuniêcia za
				// planszê, ale nie wyrzucimy b³êdu
			}

		}

		return 1;
	}

	public int printBoardToFile(String path) {
		return 1;
	}

	public void drawBoardToCanvas(Canvas canvas) {
		System.out.println("DrawBoardToCanvas called");
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		DrawBoardToCanvasRunnable drawBoardRunnable = new DrawBoardToCanvasRunnable(graphicsContext);
		Platform.runLater(drawBoardRunnable);
		
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
			cellColor=Color.WHITE;
			this.graphicsContext.setFill(cellColor);
		}

		public void run() {
			Rectangle rectToDraw;
			for (int i = 0; i < HEIGHT; i++) {
				for (int j = 0; j < WIDTH; j++) {
					switch (cells[i][j]) {
					case Element.ELECTRON_HEAD:
						cellColor=Element.ELECTRON_HEAD_COLOR;
						break;
					case Element.ELECTRON_TAIL:
						cellColor=Element.ELECTRON_TAIL_COLOR;
						break;
					case Element.CONDUCTOR:
						cellColor=Element.CONDUCTOR_COLOR;
						break;
					case Element.EMPTY_CELL:
						cellColor=Element.EMPTY_CELL_COLOR;
						break;
					}
					
					graphicsContext.setFill(cellColor);
					rectToDraw = rectangles[i][j];
					graphicsContext.fillRect(rectToDraw.getX(), rectToDraw.getY(), rectToDraw.getWidth(), rectToDraw.getHeight());
					
					/*drawCellRunnable.setCoordinates(i, j);
					drawCellRunnable.setColor(cellColor);
					Platform.runLater(drawCellRunnable);*/
				}
			}
		}

		
		public void setColor(Color color) {
			graphicsContext.setFill(color);
		}
	}
}
