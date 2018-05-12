package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Controller implements Initializable{
	
	private GraphicsContext graphicsContext;
	private MainWindow wireWorldFunctionality;

	public static final String DEFAULT_TEXT_FILE_PATH = "C:\\Users\\Dell\\IdeaProjects\\2018L_JIMP2_repozytorium_gr11\\WireWorld\\Program\\src\\defaultInputFile.txt";
	
	@FXML private Canvas canvas;
	
	@FXML
	protected void handleFileChooseButton(ActionEvent event) {
		//zmieniæ !!!
		//sprawdziæ czy wireWorldFunctionallity mo¿e byæ nullem!
		try {
			wireWorldFunctionality.setBoard(DEFAULT_TEXT_FILE_PATH);
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


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		graphicsContext = canvas.getGraphicsContext2D();
		
		
	}
	
	public void setWireWorldFunctionality(MainWindow functionallity) {
		this.wireWorldFunctionality = functionallity;
	}
	
	private void drawFirstStateOnCanvas(GraphicsContext gc) {
		gc.setFill(Color.WHITE);
		gc.setStroke(Color.BLACK);
		for(int i=0; i<10; i++)
		{
			gc.fillRect(10*i, 10, 8, 8);
			gc.strokeRect(10*i, 10, 8, 8);
		}
	}

}
