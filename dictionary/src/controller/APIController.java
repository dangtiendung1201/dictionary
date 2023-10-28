package controller;

import javafx.fxml.FXML;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;

public class APIController extends Controller {
    private final String[] languages = { "English", "Vietnamese" };
    @FXML
    public Tooltip tooltip1;
    @FXML
    public TextArea inputBox, outputBox;
    @FXML
    public ComboBox<String> originalLangBox, translatedLangBox;
    @FXML
    public Button translateBtn, swapBtn;
    
    @Override
    public void initialize() {
        tooltip1.setShowDelay(Duration.seconds(0.5));

        originalLangBox.getItems().addAll(languages);
        translatedLangBox.getItems().addAll(languages);
    }
}

