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

    private void resetHangmanImg() {
        cnt = 0;
        hangmanImg.setImage(hangmanState[cnt]);
    }

    private void changeHangmanImg() {
        cnt++;
        if (cnt == 7)
            cnt = 0;
        hangmanImg.setImage(hangmanState[cnt]);
    }

    private void handleReloadBtn() {
        init();
    }

    private void handleBackBtn() {
        showPane("/view/GameUI.fxml");
    }

    private void handleConfirmBtn() {
        String input = String.valueOf(inputText.getText().charAt(0)).toLowerCase();

        if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
            resultText.setText("Please enter a character!");
            return;
        }

        char c = input.charAt(0);

        if (hangman.checkGuessedCharacter(c)) {
            resultText.setText("You have guessed this character already!");
            return;
        }

        int cnt = hangman.checkGuess(c);

        inputText.clear();

        if (cnt == 0) {
            changeHangmanImg();
            hangman.decreaseHealth();

            resultText.setText("Wrong character " + c + "!. " + hangman.getHealth() + " guesses left.");
        } else {
            resultText.setText("Correct characters! There is(are) " + cnt + " " + c + " in the word.");
            updateWordText();
        }

        if (hangman.getHealth() == 0) {
            resultText.setText("You lose!");
            hangman.setState(Hangman.State.LOSE);
        }

        hangman.updateState();
        if (hangman.getState() == Hangman.State.WIN) {
            resultText.setText("You win!");
            
            confirmBtn.setDisable(true);
        }
        else if (hangman.getState() == Hangman.State.LOSE) {
            resultText.setText("You lose! The word is: " + hangman.getCorretWord() + ".");

            confirmBtn.setDisable(true);
        }
    }

    private void updateWordText() {
        wordText.setText(hangman.getWord());
    }

    private void init() {
        hangman.getRandomWord();
        hangman.reset();

        hintText.setText(hangman.getHint());
        wordText.setText(hangman.getWord());
        resultText.setText("");

        resetHangmanImg();
    }

    public void initialize() {
        confirmBtnTip.setShowDelay(Duration.seconds(0.5));
        backBtnTip.setShowDelay(Duration.seconds(0.5));
        reloadBtnTip.setShowDelay(Duration.seconds(0.5));

        init();

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
