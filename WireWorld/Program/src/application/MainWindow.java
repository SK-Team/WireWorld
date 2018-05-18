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

    private String boardBeforeAnySimulationFilePath;
    private Board board;
    private Simulator simulator;
    private boolean simulationActive = false;
    private int interval = INTERVAL_BEETWEEN_SIMULATIONS;

    @Override
    public void start(Stage primaryStage) {

        MainWindow wireWorldFunctionality = new MainWindow();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
            AnchorPane root = (AnchorPane) loader.load();

            Controller controller = loader.getController();
            controller.setWireWorldFunctionality(wireWorldFunctionality);
            controller.drawFirstBoard();

            Scene scene = new Scene(root, 750, 450);
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

    public void simulate(Canvas canvas, int howManyGenerations) {

        if (simulator == null) { // pierwsza symulacja
            simulator = new Simulator();
            System.out.println("Simulator initialized");
        }
        board.printToConsole();

        simulationActive = true;
        new Thread() {
            public void run() {
                for (int i = 0; i < howManyGenerations && simulationActive == true; i++) {
                    System.out.println("Symulacja nr " + i);
                    simulator.simulateGeneration(board);
                    board.drawBoardToCanvas(canvas);
                    board.printToConsole();

                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    public void returnToFirstBoardState(Canvas canvas) throws IOException {
        simulationActive = false;
        setBoardFromFile(canvas,boardBeforeAnySimulationFilePath);

    }

    public void pauseSimulation() {
        simulationActive = false;
    }

    public void saveGeneration(String filePath) throws IOException {
        board.printBoardToFile(filePath);
    }

    public void setBoardFromFile(Canvas canvas, String filePath) throws IOException { // zastanowiæ siê nad try catch
        board = new Board(filePath);
        boardBeforeAnySimulationFilePath = filePath;
        System.out.println("setBoardFromFile called");
        board.drawBoardToCanvas(canvas);
    }

    public void drawEmptyBoard(Canvas canvas){
        board = new Board();
        board.drawBoardToCanvas(canvas);
    }

    public Board getBoard(){
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(double interval) {
        this.interval = (int)interval;
    }
}
