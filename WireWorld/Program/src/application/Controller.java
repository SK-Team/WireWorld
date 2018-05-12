package application;

import java.io.IOException;

import data.Element;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Controller{

	private GraphicsContext graphicsContext;
	private MainWindow wireWorldFunctionality;

	@FXML
	private Canvas canvas;

	@FXML
	protected void handleFileChooseButton(ActionEvent event) {
		// zmieniæ !!!
		// sprawdziæ czy wireWorldFunctionallity mo¿e byæ nullem!
		try {
			wireWorldFunctionality.setBoard("C:\\Users\\sucha_rakzuks\\Desktop\\defaultInputFile.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	protected void handleSaveButton(ActionEvent event) {

	}

	@FXML
	protected void handleStartButton(ActionEvent event) {
		wireWorldFunctionality.simulate(canvas, 100);
	}

	@FXML
	protected void handlePauseButton(ActionEvent event) {

	}

	@FXML
	protected void handleStopButton(ActionEvent event) {

	}

	@FXML
	public void initialize() {
		graphicsContext = canvas.getGraphicsContext2D();
		/*Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				graphicsContext.setFill(Element.EMPTY_CELL_COLOR);
				graphicsContext.fillRect(0, 0, 700, 700);
				
			}
		});*/
		
		
		
		System.out.println("initialize controller: "+canvas + "   " + graphicsContext);

	}
	

	public void setWireWorldFunctionality(MainWindow functionallity) {
		this.wireWorldFunctionality = functionallity;
	}

	private void drawFirstStateOnCanvas(GraphicsContext gc) {
		gc.setFill(Color.WHITE);
		gc.setStroke(Color.BLACK);
		for (int i = 0; i < 10; i++) {
			gc.fillRect(10 * i, 10, 8, 8);
			gc.strokeRect(10 * i, 10, 8, 8);
		}
	}

}
