package application;

import dataHandling.WrongInputFileException;
import elements.ElementConstans;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

	public static final String WRONG_INPUT_FILE_MESSAGE = "B³êdny format pliku wejœciowego.";
	public static final String UNSUCCESSFUL_FILE_LOADING_MESSAGE = "Nie uda³o siê wczytaæ pliku";
	public static final String UNSUCCESSFUL_SAVING_MESSAGE = "Nie uda³o siê zapisaæ generacji.";

	private final String FILE_CHOOSER_TITLE = "Wybierz plik wejœciowy";

	private MainWindow wireWorldFunctionality;
	private FileChooser fileChooser;
	private int cellType;
	private int type;

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
	private TextArea currentInputFileTextAreaView;
	@FXML
	private ToggleGroup toggleGroup;
	@FXML
	private CheckBox userDrawingCheckBox;
	@FXML
	private CheckBox elementTypeCheckBox;
	@FXML
	private VBox radioButtonsVBox;
	@FXML
	private Slider speedSlider;

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
			wireWorldFunctionality.setBoardFromFile(canvas, filePath);
			startButton.setDisable(false);
			pauseButton.setDisable(false);
			stopButton.setDisable(false);
			speedSlider.setDisable(false);

			currentInputFileTextAreaView.setText(filePath);

		} catch (IOException e) {
			wireWorldFunctionality.showNoChangesDialog(UNSUCCESSFUL_FILE_LOADING_MESSAGE);
			e.printStackTrace();
		} catch (WrongInputFileException e) {
			wireWorldFunctionality.showNoChangesDialog(WRONG_INPUT_FILE_MESSAGE);
		}

	}

	@FXML
	protected void handleSaveButton(ActionEvent event) {

		Node source = (Node) event.getSource();
		Window stage = source.getScene().getWindow();

		String savedFilePath;
		File savedFile = fileChooser.showSaveDialog(stage);
		if (savedFile == null) {
			return;
		} else {
			savedFilePath = savedFile.getAbsolutePath();
		}
		try {
			wireWorldFunctionality.saveGeneration(savedFilePath);
		} catch (IOException e) {
			wireWorldFunctionality.showNoChangesDialog(UNSUCCESSFUL_SAVING_MESSAGE);
			e.printStackTrace();
		}
	}

	@FXML
	protected void handleStartButton(ActionEvent event) {
		saveButton.setDisable(true);
		selectInputFileButton.setDisable(true);

		pauseButton.setDisable(false);

		if (toggleGroup.getSelectedToggle() != null)
			toggleGroup.getSelectedToggle().setSelected(false);
		radioButtonsVBox.setDisable(true);
		userDrawingCheckBox.setDisable(true);
		userDrawingCheckBox.setSelected(false);
		elementTypeCheckBox.setDisable(true);
		elementTypeCheckBox.setSelected(false);

		wireWorldFunctionality.simulate(canvas, 100000, this);
	}

	@FXML
	protected void handlePauseButton(ActionEvent event) {
		wireWorldFunctionality.pauseSimulation();
		userDrawingCheckBox.setDisable(false);
		saveButton.setDisable(false);
		selectInputFileButton.setDisable(false);
	}

	@FXML
	protected void handleStopButton(ActionEvent event) {
		try {
			wireWorldFunctionality.pauseSimulation();
			userDrawingCheckBox.setDisable(false);
			saveButton.setDisable(false);
			saveButton.setDisable(true);
			wireWorldFunctionality.returnToFirstBoardState(canvas);
			selectInputFileButton.setDisable(false);
		} catch (IOException e) {
			wireWorldFunctionality.showNoChangesDialog(UNSUCCESSFUL_FILE_LOADING_MESSAGE);
			e.printStackTrace();
		} catch (WrongInputFileException e) {
			wireWorldFunctionality.showNoChangesDialog(WRONG_INPUT_FILE_MESSAGE);
		}
	}

	@FXML
	protected void handleuserDrawingCheckBox(ActionEvent event) {
		if (userDrawingCheckBox.isSelected() == true) {
			radioButtonsVBox.setDisable(false);
			elementTypeCheckBox.setDisable(false);
		} else {
			if (toggleGroup.getSelectedToggle() != null)
				toggleGroup.getSelectedToggle().setSelected(false);
			radioButtonsVBox.setDisable(true);
			elementTypeCheckBox.setDisable(true);
		}

	}

	@FXML
	protected void handleElementTypeCheckBox(ActionEvent event) {
		if (elementTypeCheckBox.isSelected())
			type = ElementConstans.REVERSED_TYPE;
		else
			type = ElementConstans.DEFAULT_TYPE;

	}

	@FXML
	protected void handleEmptyCellRadioButton(ActionEvent event) {
		cellType = ElementConstans.EMPTY_CELL;
	}

	@FXML
	protected void handleElectronTailRadioButton(ActionEvent event) {
		cellType = ElementConstans.ELECTRON_TAIL;
	}

	@FXML
	protected void handleElectronHeadRadioButton(ActionEvent event) {
		cellType = ElementConstans.ELECTRON_HEAD;
	}

	@FXML
	protected void handleConductorRadioButton(ActionEvent event) {
		cellType = ElementConstans.CONDUCTOR;
	}

	@FXML
	protected void handleDiodeRadioButton(ActionEvent event) {
		cellType = ElementConstans.DIODE;
	}

	@FXML
	protected void handleAndGateRadioButton(ActionEvent event) {
		cellType = ElementConstans.AND_GATE;
	}

	@FXML
	protected void handleOrGateRadioButton(ActionEvent event) {
		cellType = ElementConstans.OR_GATE;
	}

	@FXML
	protected void handleNorGateRadioButton(ActionEvent event) {
		cellType = ElementConstans.NOR_GATE;
	}

	@FXML
	protected void handleClearBoardButton(ActionEvent event) {
		wireWorldFunctionality.drawEmptyBoard(canvas);
	}

	@FXML
	protected void handleMouseClickedOnCanvas(MouseEvent event) {
		if (userDrawingCheckBox.isSelected() == true && toggleGroup.getSelectedToggle() != null) {

			wireWorldFunctionality.addSelectedToBoard(event.getX(), event.getY(), cellType, canvas, type);

			startButton.setDisable(false);
			speedSlider.setDisable(false);
			pauseButton.setDisable(false);
			selectInputFileButton.setDisable(false);
		}

	}

	public void handleSimulationFinished() {
		saveButton.setDisable(false);
		pauseButton.setDisable(true);
		selectInputFileButton.setDisable(false);
		userDrawingCheckBox.setDisable(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initFileChooser();
		type = ElementConstans.DEFAULT_TYPE;
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				handleMouseClickedOnCanvas(event);

			}

		});

		speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				wireWorldFunctionality.setInterval((int) (1000 / newValue.doubleValue()));
			}
		});

	}

	public void setWireWorldFunctionality(MainWindow functionallity) {
		this.wireWorldFunctionality = functionallity;
	}

	private void initFileChooser() {
		fileChooser = new FileChooser();
		fileChooser.setTitle(FILE_CHOOSER_TITLE);
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt files", "*.txt"));
	}

	public void drawEmptyBoard() {
		wireWorldFunctionality.drawEmptyBoard(canvas);
	}

}
