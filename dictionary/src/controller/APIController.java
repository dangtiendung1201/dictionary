package controller;

import alert.Alerts;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.TranslateAPI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;

import static service.ImageAnalysisAPI.getTextFromImage;
import static service.SpeechAPI.getSpeechFromText;
import static service.SpeechRecognitionAPI.getTextFromSpeech;

public class APIController extends Controller {
    public static int apiCallCount = 0;
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
    public ImageView audioOriginalOn;
    public ImageView audioTranslatedOn;
    public ImageView audioOriginalOff;
    public ImageView audioTranslatedOff;
    public ImageView voiceOn;
    public ImageView voiceOff;

    @FXML
    private void handleSpeech2TextBtn() {
        int thisApiCallNumber = ++apiCallCount;

        voiceOn.setVisible(true);
        voiceOff.setVisible(false);

        Task<Void> apiCallTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String sentence = "";
                try {
                    sentence = getTextFromSpeech(originalLangBox.getValue());
                } catch (ConnectException e) {
                    Alert alert = new Alerts().error("Error",
                            "No Internet Connection",
                            "Please check your internet connection.");
                    alert.show();
                } catch (IOException e) {
                    Alert alert = new Alerts().error("Error",
                            "Can't recognize speech",
                            "Please check your language.");
                    alert.show();
                } catch (Exception e) {
                    Alert alert = new Alerts().error("Error",
                            "Unknown Error",
                            "There is an error, please try again.");
                    alert.show();
                }
                String finalSentence = sentence;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (thisApiCallNumber == apiCallCount) {
                            inputBox.setText(finalSentence);
                        }

                        voiceOn.setVisible(false);
                        voiceOff.setVisible(true);
                    }
                });

                return null;
            }
        };
        new Thread(apiCallTask).start();
    }

    private void handleImage2TextBtn() {
        int thisApiCallNumber = ++apiCallCount;
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);

        String path = selectedFile.getAbsolutePath();

        InputStream stream = null;
        try {
            stream = new FileInputStream(path);
        } catch (Exception e) {
            Alert alert = new Alerts().error("Error",
                    "Unknown Error",
                    "There is an error, please try again.");
            alert.show();
        }
        assert stream != null;
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

        final String[] sentence = {""};
        Task<Void> apiCallTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                
                try {
                    sentence[0] = getTextFromImage(path);
                } catch (ConnectException e) {
                    Alert alert = new Alerts().error("Error",
                            "No Internet Connection",
                            "Please check your internet connection.");
                    alert.show();
                } catch (Exception e) {
                    Alert alert = new Alerts().error("Error",
                            "Unknown Error",
                            "There is an error, please try again.");
                    alert.show();
                }
                String finalSentence = sentence[0];
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (thisApiCallNumber == apiCallCount) {
                            inputBox.setText(finalSentence);
                        }
                    }
                });

                return null;
            }
        };
        new Thread(apiCallTask).start();

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
        int thisApiCallNumber = ++apiCallCount;

        audioOriginalOff.setVisible(false);
        audioOriginalOn.setVisible(true);
        Task<Void> apiCallTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    getSpeechFromText(inputBox.getText(), originalLangBox.getValue());
                } catch (ConnectException e) {
                    Alert alert = new Alerts().error("Error",
                            "No Internet Connection",
                            "Please check your internet connection.");
                    alert.show();
                } catch (Exception e) {
                    Alert alert = new Alerts().error("Error",
                            "Unknown Error",
                            "There is an error, please try again.");
                    alert.show();
                }

                Platform.runLater(() -> {
                    audioOriginalOff.setVisible(true);
                    audioOriginalOn.setVisible(false);
                });
                return null;
            }
        };
        new Thread(apiCallTask).start();
    }

    @FXML
    private void handleTranslatedSoundBtn() {
        int thisApiCallNumber = ++apiCallCount;

        audioTranslatedOff.setVisible(false);
        audioTranslatedOn.setVisible(true);

        Task<Void> apiCallTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    getSpeechFromText(outputBox.getText(), translatedLangBox.getValue());
                } catch (ConnectException e) {
                    Alert alert = new Alerts().error("Error",
                            "No Internet Connection",
                            "Please check your internet connection.");
                    alert.show();
                } catch (Exception e) {
                    Alert alert = new Alerts().error("Error",
                            "Unknown Error",
                            "There is an error, please try again.");
                    alert.show();
                }
                Platform.runLater(() -> {
                    audioTranslatedOff.setVisible(true);
                    audioTranslatedOn.setVisible(false);
                });
                return null;
            }
        };
        new Thread(apiCallTask).start();
    }

    @FXML
    private void handleTranslateBtn() {
        int thisApiCallNumber = ++apiCallCount;

        Task<Void> apiCallTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String sentence = inputBox.getText();
                String originalLanguage = originalLangBox.getValue();
                String translatedLanguage = translatedLangBox.getValue();
                TranslateAPI translateAPI = new TranslateAPI();
                String resultText = translateAPI.translate
                        (sentence, originalLanguage, translatedLanguage);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if (thisApiCallNumber == apiCallCount) {
                            outputBox.setText(resultText);
                        }
                    }
                });

                return null;
            }
        };

        new Thread(apiCallTask).start();
    }

    @FXML
    public void initialize() {
        tooltip1.setShowDelay(Duration.seconds(0.5));

        originalLangBox.getItems().addAll(languages);
        translatedLangBox.getItems().addAll(languages);

        originalLangBox.setValue("English");
        translatedLangBox.setValue("Vietnamese");

        outputBox.setEditable(false);
        audioTranslatedOn.setVisible(false);
        audioOriginalOn.setVisible(false);

        voiceOn.setVisible(false);

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
            handleImage2TextBtn();
        });

        originalSoundBtn.setOnAction(actionEvent -> {
            handleOriginalSoundBtn();
        });

        translatedSoundBtn.setOnAction(actionEvent -> {
            handleTranslatedSoundBtn();
        });
    }
}

