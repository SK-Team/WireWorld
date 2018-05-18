package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class Controller implements Initializable {

    private MainWindow wireWorldFunctionality;

    // i tak mamy inne ï¿½cieï¿½ki, bo robimy w innych ï¿½rodowiskach

    // public static final String DEFAULT_TEXT_FILE_PATH =

    // "C:\\Users\\Dell\\IdeaProjects\\2018L_JIMP2_repozytorium_gr11\\WireWorld\\Program\\src\\defaultInputFile.txt";
    public static final String DEFAULT_TEXT_FILE_PATH = "C:\\Users\\sucha_rakzuks\\Desktop\\defaultInputFile.txt";

    private final String FILE_CHOOSER_TITLE = "Wybierz plik wejï¿½ciowy";

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
    private TextArea currentInputFileTextAreaView;

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
            wireWorldFunctionality.setBoard(canvas, filePath); // tu powinniï¿½my obsï¿½uï¿½yï¿½ jakï¿½ï¿½ wartoï¿½ï¿½ zwracanï¿½ w razie bï¿½ï¿½dnego
            // formatu pliku
            startButton.setDisable(false);
            stopButton.setDisable(false);
            pauseButton.setDisable(false);


            currentInputFileTextAreaView.setText(filePath);
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
        if (savedFile == null) {
            return;
        } else {
            savedFilePath = savedFile.getAbsolutePath();
        }
        try {
            wireWorldFunctionality.saveGeneration(savedFilePath);
        } catch (IOException e) {
            // TODO Auto-generated catch block //obsï¿½uï¿½yï¿½ ten bï¿½ï¿½d !!!
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleStartButton(ActionEvent event) {
        saveButton.setDisable(true);
        wireWorldFunctionality.simulate(canvas, 100);
    }

    @FXML
    protected void handlePauseButton(ActionEvent event) {
        wireWorldFunctionality.pauseSimulation();
        saveButton.setDisable(false);
    }

    @FXML
    protected void handleStopButton(ActionEvent event) {
        try {
            saveButton.setDisable(true);
			wireWorldFunctionality.returnToFirstBoardState(canvas);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @FXML
    protected void handleMouseClickedOnCanvas(MouseEvent event) {
    	System.out.println("Klikniêto: "+event.getX()+" "+event.getY());
    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initFileChooser();
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				handleMouseClickedOnCanvas(event);
				
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

    public void drawFirstBoard(){
        wireWorldFunctionality.drawEmptyBoard(canvas);
    }


}
