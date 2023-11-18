package controller;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.T2SThread;
import service.TranslateAPI;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static service.ImageAnalysisAPI.getTextFromImage;
import static service.SpeechRecognitionAPI.getTextFromSpeech;

public class APIController extends Controller {
    private final String[] languages = {"English", "Vietnamese"};
    @FXML
    public Tooltip tooltip1;
    @FXML
    public TextArea inputBox;
    @FXML
    public TextArea outputBox;
    @FXML
    public ComboBox<String> originalLangBox, translatedLangBox;
    @FXML
    public Button translateBtn, swapBtn, Speech2TextBtn, Image2TextBtn, originalSoundBtn, translatedSoundBtn;

    @FXML
    private void handleSpeech2TextBtn() {
        try {
            String sentence = getTextFromSpeech(originalLangBox.getValue());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Sound Error");
            alert.setContentText("Can't recognize your speech!");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    private void handleImage2SpeechBtn() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile == null) {
            return;
        }
        String path = selectedFile.getAbsolutePath();

        InputStream stream = null;
        try {
            stream = new FileInputStream(path);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Image image = new Image(stream);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setX(10);
        imageView.setY(10);
        imageView.setFitWidth(575);
        imageView.setPreserveRatio(true);
        Group root = new Group(imageView);
        Scene scene = new Scene(root, 595, 370);
        stage.setTitle("Image");
        stage.setScene(scene);
        stage.show();

        String sentence = "";
        try {
            sentence = getTextFromImage(path);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        inputBox.setText(sentence);
    }

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
    private void handleOriginalSoundBtn() {
        T2SThread t1 = new T2SThread();
        // get text from speech
        try {
            System.out.println("Handle sound btn");
            t1.getSpeechFromTextThread(inputBox.getText(), originalLangBox.getValue());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleTranslatedSoundBtn() {
        T2SThread t1 = new T2SThread();
        // get text from speech
        try {
            System.out.println("Handle sound btn");
            t1.getSpeechFromTextThread(outputBox.getText(), translatedLangBox.getValue());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        String sentence = outputBox.getText();
//        String originalLanguage = translatedLangBox.getValue();
//        try {
//            getSpeechFromText(sentence, originalLanguage);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
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

        Speech2TextBtn.setOnAction(actionEvent -> {
            handleSpeech2TextBtn();
        });

        Image2TextBtn.setOnAction(actionEvent -> {
            handleImage2SpeechBtn();
        });

        originalSoundBtn.setOnAction(actionEvent -> {
            handleOriginalSoundBtn();
        });

        translatedSoundBtn.setOnAction(actionEvent -> {
            handleTranslatedSoundBtn();
        });
    }
}

