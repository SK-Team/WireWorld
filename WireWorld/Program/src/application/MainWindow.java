package application;
	
import java.io.IOException;

import data.Board;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import simulator.Simulator;


public class MainWindow extends Application {
	
	private final int INTERVAL_BEETWEEN_SIMULATIONS = 1000;
	
	private Board board;
	private Simulator simulator;
	private boolean simulationActive = false;
	@Override
	public void start(Stage primaryStage) {
		
		MainWindow wireWorldFunctionality = new MainWindow();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			
			
			Controller controller = loader.getController();
			controller.setWireWorldFunctionality(wireWorldFunctionality);
			
			
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	public void simulate(Canvas canvas, int howManyGenerations) {
		
		if(simulator==null) { //pierwsza symulacja
			simulator = new Simulator();
			System.out.println("Simulator initialized");
		}
		
		simulationActive = true;
		new Thread() {
			public void run() {
				for(int i=0; i<howManyGenerations && simulationActive==true; i++) {
					simulator.simulateGeneration(board);	
				}
				board.drawBoardToCanvas(canvas);
				try {
					Thread.sleep(INTERVAL_BEETWEEN_SIMULATIONS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		
		
		
	}
	public void stopSimulation() {
		simulationActive = false;
		
	}
	public void pauseSimulation() {
		simulationActive = false;
	}
	public void saveGeneration(String filePath) {
		
	}
	public void setBoard(String filePath) throws IOException{ //zastanowi� si� nad try catch
		board = new Board(filePath);
		System.out.println("setBoard called");
	}
}