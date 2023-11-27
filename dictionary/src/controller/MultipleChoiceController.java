package controller;

import game.GameManagement.State;
import game.MultipleChoice;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class MultipleChoiceController extends GameController {
    private static char noChoice = 'E';
    private static MultipleChoice multipleChoice = new MultipleChoice();
    @FXML
    private Label questionText, scoreText, healthText, resultText;
    @FXML
    private RadioButton answerABtn, answerBBtn, answerCBtn, answerDBtn;
    @FXML
    private Button confirmBtn, backBtn, reloadBtn;
    @FXML
    private Tooltip confirmBtnTip, backBtnTip, reloadBtnTip;

    private void handleConfirmBtn() {
        char selectedAnswer = getSelectedAnswer();
        char correctAnswer = multipleChoice.getCorrectAnswer();

        if (selectedAnswer == noChoice) {
            resultText.setText("Please choose an answer!");
            return;
        }

        if (selectedAnswer == correctAnswer) {
            resultText.setText("Correct!");
            multipleChoice.increasePoint();
        } else {
            resultText.setText("Wrong!" + "\n"
                    + "Correct answer is: " + correctAnswer + ". "
                    + multipleChoice.getAnswer(correctAnswer) + "." + "\n"
                    + "Correct sentence is: " + multipleChoice.getSentence());
            multipleChoice.decreaseHealth();
        }

        if (multipleChoice.getHealth() == 0) {
            resultText.setText("You lose!");
            multipleChoice.setState(State.LOSE);
        }

        if (multipleChoice.getPoint() == 10) {
            resultText.setText("You win!");
            multipleChoice.setState(State.WIN);
        }

        if (multipleChoice.getState() == State.PLAYING) {
            updateQuestion();
            scoreText.setText("Score: " + multipleChoice.getPoint());
            healthText.setText("Health: " + multipleChoice.getHealth());
        } else {
            scoreText.setText("Score: " + multipleChoice.getPoint());
            healthText.setText("Health: " + multipleChoice.getHealth());

            confirmBtn.setDisable(true);
            answerABtn.setDisable(true);
            answerBBtn.setDisable(true);
            answerCBtn.setDisable(true);
            answerDBtn.setDisable(true);
        }
    }

    private void handleBackBtn() {
        showPane("/view/GameUI.fxml");
    }

    private void handleReloadBtn() {
        multipleChoice.setPoint(0);
        multipleChoice.setHealth(3);
        multipleChoice.setState(State.PLAYING);

        confirmBtn.setDisable(false);
        answerABtn.setDisable(false);
        answerBBtn.setDisable(false);
        answerCBtn.setDisable(false);
        answerDBtn.setDisable(false);

        updateQuestion();
        scoreText.setText("Score: " + multipleChoice.getPoint());
        healthText.setText("Health: " + multipleChoice.getHealth());
        resultText.setText("");
    }

    private void handleAnswerBtn() {
        answerABtn.setOnAction(actionEvent -> {
            answerBBtn.setSelected(false);
            answerCBtn.setSelected(false);
            answerDBtn.setSelected(false);
        });

        answerBBtn.setOnAction(actionEvent -> {
            answerABtn.setSelected(false);
            answerCBtn.setSelected(false);
            answerDBtn.setSelected(false);
        });

        answerCBtn.setOnAction(actionEvent -> {
            answerABtn.setSelected(false);
            answerBBtn.setSelected(false);
            answerDBtn.setSelected(false);
        });

        answerDBtn.setOnAction(actionEvent -> {
            answerABtn.setSelected(false);
            answerBBtn.setSelected(false);
            answerCBtn.setSelected(false);
        });
    }

    private char getSelectedAnswer() {
        if (answerABtn.isSelected())
            return 'A';
        else if (answerBBtn.isSelected())
            return 'B';
        else if (answerCBtn.isSelected())
            return 'C';
        else if (answerDBtn.isSelected())
            return 'D';

        return noChoice;
    }

    private void updateQuestion() {
        multipleChoice.generateQuestion();
        multipleChoice.generateAnswer();

        answerABtn.setSelected(false);
        answerBBtn.setSelected(false);
        answerCBtn.setSelected(false);
        answerDBtn.setSelected(false);

        questionText.setText(multipleChoice.getQuestion());
        answerABtn.setText(multipleChoice.getAnswer(0));
        answerBBtn.setText(multipleChoice.getAnswer(1));
        answerCBtn.setText(multipleChoice.getAnswer(2));
        answerDBtn.setText(multipleChoice.getAnswer(3));
    }

    private void init() {
        multipleChoice.setHealth(3);
        multipleChoice.setPoint(0);
        multipleChoice.setState(State.PLAYING);
    }

    public void initialize() {
        confirmBtnTip.setShowDelay(Duration.seconds(0.5));
        backBtnTip.setShowDelay(Duration.seconds(0.5));
        reloadBtnTip.setShowDelay(Duration.seconds(0.5));

        init();
        updateQuestion();

        // Radio button that user can only choose 1 of them

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

        handleAnswerBtn();
    }
}
