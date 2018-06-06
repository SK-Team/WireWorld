package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionController
        implements Initializable {

    @FXML
    private Button okButton;
    @FXML
    private Label messageLabel;

    private Stage dialogStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMessageLabel(String message) {
        messageLabel.setText(message);
    }

    @FXML
    private void handleOkButton() {
        dialogStage.close();
    }


}
