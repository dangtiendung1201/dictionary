package controller;

import javafx.fxml.FXML;
import javafx.util.Duration;
import service.TranslateAPI;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;

public class APIController extends Controller {
    private final String[] languages = { "English", "Vietnamese" };
    @FXML
    public Tooltip tooltip1;
    @FXML
    public TextArea inputBox;
    @FXML
    public TextArea outputBox;
    @FXML
    public ComboBox<String> originalLangBox, translatedLangBox;
    @FXML
    public Button translateBtn, swapBtn;

    @FXML
    private void handleSwapBtn() {
        String temp = originalLangBox.getValue();
        originalLangBox.setValue(translatedLangBox.getValue());
        translatedLangBox.setValue(temp);

        temp = inputBox.getText();
        inputBox.setText(outputBox.getText());
        outputBox.setText(temp);
    }

    @FXML
    private void handleTranslateBtn() {
        try {
            String sentence = inputBox.getText();
            String originalLanguage = originalLangBox.getValue();
            String translatedLanguage = translatedLangBox.getValue();

            TranslateAPI translateAPI = new TranslateAPI();
            String translatedSentence = translateAPI.translate(sentence, originalLanguage, translatedLanguage);

            outputBox.setText(translatedSentence);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void initialize() {
        tooltip1.setShowDelay(Duration.seconds(0.5));

        originalLangBox.getItems().addAll(languages);
        translatedLangBox.getItems().addAll(languages);

        originalLangBox.setValue("English");
        translatedLangBox.setValue("Vietnamese");

        outputBox.setEditable(false);

        translateBtn.setOnAction(actionEvent -> {
            handleTranslateBtn();
        });

        swapBtn.setOnAction(actionEvent -> {
            handleSwapBtn();
        });
    }
}

