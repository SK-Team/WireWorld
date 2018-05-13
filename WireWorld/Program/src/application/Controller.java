package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class Controller implements Initializable {

	private MainWindow wireWorldFunctionality;

	// i tak mamy inne œcie¿ki, bo robimy w innych œrodowiskach

	// public static final String DEFAULT_TEXT_FILE_PATH =
	// "C:\\Users\\Dell\\IdeaProjects\\2018L_JIMP2_repozytorium_gr11\\WireWorld\\Program\\src\\defaultInputFile.txt";
	public static final String DEFAULT_TEXT_FILE_PATH = "C:\\Users\\sucha_rakzuks\\Desktop\\defaultInputFile.txt";

	private final String FILE_CHOOSER_TITLE = "Wybierz plik wejœciowy";

	private FileChooser fileChooser;

	@FXML
	private Canvas canvas;
	@FXML
	private Button selectInputFileButton;
	@FXML
	private Button startButton;
	@FXML
	private Button stopButton;
	@FXML
	private Button pauseButton;
	@FXML
	private Button saveButton;
	@FXML
	private TextField currentInputFileTextView;

	@FXML
	protected void handleFileChooseButton(ActionEvent event) {

		Node source = (Node) event.getSource();
		Window stage = source.getScene().getWindow();

		String filePath;
		File file = fileChooser.showOpenDialog(stage);
		if (file == null) {
			return;
		} else {
			filePath = file.getAbsolutePath();
		}

		try {
			wireWorldFunctionality.setBoard(canvas,filePath); // tu powinniœmy obs³u¿yæ jak¹œ wartoœæ zwracan¹ w razie b³êdnego
														// formatu pliku
			startButton.setDisable(false);
			stopButton.setDisable(false);
			pauseButton.setDisable(false);

			currentInputFileTextView.setText(filePath);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	protected void handleSaveButton(ActionEvent event) {
		
		Node source = (Node) event.getSource();
		Window stage = source.getScene().getWindow();
		
		String savedFilePath;
		File savedFile = fileChooser.showSaveDialog(stage);
		if(savedFile==null) {
			return ;
		}
		else {
			savedFilePath = savedFile.getAbsolutePath();
		}
		try {
			wireWorldFunctionality.saveGeneration(savedFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block //obs³u¿yæ ten b³¹d !!!
			e.printStackTrace();
		}
	}

	@FXML
	protected void handleStartButton(ActionEvent event) {
		wireWorldFunctionality.simulate(canvas, 100);
	}

	@FXML
	protected void handlePauseButton(ActionEvent event) {
		wireWorldFunctionality.pauseSimulation();
		saveButton.setDisable(false);
	}

	@FXML
	protected void handleStopButton(ActionEvent event) {
		wireWorldFunctionality.stopSimulation();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initFileChooser();
	}

	public void setWireWorldFunctionality(MainWindow functionallity) {
		this.wireWorldFunctionality = functionallity;
	}

	private void initFileChooser() {
		fileChooser = new FileChooser();
		fileChooser.setTitle(FILE_CHOOSER_TITLE);
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt files", "*.txt"));
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
