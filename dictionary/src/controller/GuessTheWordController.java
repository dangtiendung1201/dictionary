package controller;

import game.GameManagement.State;
import game.GuessTheWord;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

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
        init();
    }

    private void handleBackBtn() {
        showPane("/view/GameUI.fxml");
    }

    private void handleConfirmBtn() {
        String input = inputText.getText().toLowerCase();
        String guess = input.split(" ")[0];
        if (guess.length() == 0) {
            resultText.setText("Please enter a word!");
            return;
        }

        inputText.clear();

        if (!guessTheWord.checkGuess(guess)) {
            resultText.setText("Wrong!" + "\n" + "Correct word is: " + guessTheWord.getCorrectWord());
            guessTheWord.decreaseHealth();
        } else {
            resultText.setText("Correct!");
            guessTheWord.increasePoint();
        }

        if (guessTheWord.getHealth() == 0) {
            guessTheWord.setState(State.LOSE);
        }

        if (guessTheWord.getState() == State.PLAYING) {
            updateQuestion();
            scoreText.setText("Score: " + guessTheWord.getPoint());
            healthText.setText("Health: " + guessTheWord.getHealth());
        } else {
            inputText.setDisable(true);
            confirmBtn.setDisable(true);

            scoreText.setText("Score: " + guessTheWord.getPoint());
            healthText.setText("Health: " + guessTheWord.getHealth());
        }
    }

    private void init() {
        guessTheWord.reset();
        guessTheWord.setState(State.PLAYING);

        scoreText.setText("Score: " + guessTheWord.getPoint());
        healthText.setText("Health: " + guessTheWord.getHealth());
        
        inputText.setDisable(false);
        confirmBtn.setDisable(false);
    }

    private void updateQuestion() {
        guessTheWord.getRandomWord();
        hintText.setText(guessTheWord.getHint());
    }

    public void initialize() {
        confirmBtnTip.setShowDelay(Duration.seconds(0.5));
        backBtnTip.setShowDelay(Duration.seconds(0.5));
        reloadBtnTip.setShowDelay(Duration.seconds(0.5));

        init();
        updateQuestion();

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
    }
}
