package application;

import dataHandling.Board;
import dataHandling.WrongInputFileException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import simulator.Simulator;

import java.io.IOException;

public class MainWindow extends Application {

	private final int INTERVAL_BEETWEEN_SIMULATIONS = 1000;
	private String boardBeforeAnySimulationFilePath;
	private Board board;
	private Simulator simulator;
	private boolean simulationActive = false;
	private boolean simulationPaused = false;
	private int interval = INTERVAL_BEETWEEN_SIMULATIONS;
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {

		MainWindow wireWorldFunctionality = new MainWindow();

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
			AnchorPane root = (AnchorPane) loader.load();

			Controller guiController = loader.getController();
			guiController.setWireWorldFunctionality(wireWorldFunctionality);
			guiController.drawEmptyBoard();

			Scene scene = new Scene(root, 850, 630);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void simulate(Canvas canvas, int howManyGenerations, Controller guiController) {

		if (simulator == null) { // pierwsza symulacja
			simulator = new Simulator();
			System.out.println("Simulator initialized");
		}
		board.printToConsole();

		simulationActive = true;
		simulationPaused = false;

		new Thread() {
			public void run() {
				System.out.println("Pocz¹tek!");
				for (int i = 0; i < howManyGenerations && simulationActive == true && simulationPaused == false; i++) {
					System.out.println("Symulacja nr " + i);
					simulator.simulateGeneration(board);
					if (!simulator.isAnyChange()) {
						simulationActive = false;
					}
					board.drawBoardToCanvas(canvas, simulator.getChanges());
					board.printToConsole();

					try {
						Thread.sleep(interval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				simulationActive = false;
				if (!simulationPaused) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							guiController.handleSimulationFinished();
							showNoChangesDialog();
						}
					});
				}
			}
		}.start();
	}

	public void returnToFirstBoardState(Canvas canvas) throws IOException, WrongInputFileException {
		simulationActive = false;
		setBoardFromFile(canvas, boardBeforeAnySimulationFilePath);

	}

	public void pauseSimulation() {
		simulationPaused = true;
	}

	public void saveGeneration(String filePath) throws IOException {
		board.printBoardToFile(filePath);
	}

	public void setBoardFromFile(Canvas canvas, String filePath) throws IOException, WrongInputFileException {
		board = new Board(filePath);
		boardBeforeAnySimulationFilePath = filePath;
		System.out.println("setBoardFromFile called");
		board.drawBoardToCanvas(canvas);
	}

	public void drawEmptyBoard(Canvas canvas) {
		board = new Board();
		board.drawBoardToCanvas(canvas);
	}

	public void addSelectedToBoard(double x, double y, int cellType, Canvas canvas) {

		board.addToBoard(x, y, cellType, canvas);

	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;

	}

	public void showNoChangesDialog() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("NoChangesDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Koniec symulacji");
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			NoChangesDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);

			dialogStage.showAndWait();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
