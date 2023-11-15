package controller;

import game.Hangman;
import javafx.fxml.FXML;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class HangmanController extends GameController {
    private static Hangman hangman = new Hangman();
    @FXML
    private static Image[] hangmanState = new Image[7];
    static {
        for (int i = 0; i < 7; i++) {
            hangmanState[i] = new Image("/icon/hangman/hangman" + i + ".png");
        }
    }
    @FXML
    private ImageView hangmanImg;
    @FXML
    private Label hintText, wordText, resultText;
    @FXML
    private Button confirmBtn, backBtn, reloadBtn;
    @FXML
    private Tooltip confirmBtnTip, backBtnTip, reloadBtnTip;
    @FXML
    private TextField inputText;

    private int cnt = 0;

    private void handleReloadBtn() {
        System.out.println("Reload button clicked!");
    }

    private void handleBackBtn() {
        showPane("/view/GameUI.fxml");
    }

    private void handleConfirmBtn() {
        System.out.println(inputText.getText());
    }

    public void initialize() {
        confirmBtnTip.setShowDelay(Duration.seconds(0.5));
        backBtnTip.setShowDelay(Duration.seconds(0.5));
        reloadBtnTip.setShowDelay(Duration.seconds(0.5));
        hangmanImg.setImage(hangmanState[cnt]);

        confirmBtn.setOnAction(actionEvent -> {
            try {
                handleConfirmBtn();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        backBtn.setOnAction(actionEvent -> {
            try {
                handleBackBtn();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        reloadBtn.setOnAction(actionEvent -> {
            try {
                handleReloadBtn();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        hangmanImg.setOnMouseClicked(mouseEvent -> {
            cnt++;
            if (cnt == 7)
                cnt = 0;
            hangmanImg.setImage(hangmanState[cnt]);
        });
    }
}
