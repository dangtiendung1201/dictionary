package controller;

import game.GuessTheWord;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;

public class GuessTheWordController extends GameController {
    private static GuessTheWord guessTheWord = new GuessTheWord();
    @FXML
    private Label hintText, resultText, healthText, scoreText;
    @FXML
    private Button confirmBtn, backBtn, reloadBtn;
    @FXML
    private Tooltip confirmBtnTip, backBtnTip, reloadBtnTip;
    @FXML
    private TextField inputText;

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

        // updateHint();
        // updateScore();
        // updateHealth();
    }
}
