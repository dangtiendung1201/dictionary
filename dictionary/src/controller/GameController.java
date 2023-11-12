package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

public class GameController extends Controller {
    @FXML
    protected AnchorPane gamePane;
    @FXML
    private Button hangmanBtn, mcBtn, gtwBtn;
    @FXML
    private Tooltip hangmanBtnTip, mcBtnTip, gtwBtnTip;

    protected void setNode(Node node) {
        gamePane.getChildren().clear();
        gamePane.getChildren().add(node);
    }

    protected void showPane(String path) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(path));
            setNode(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        hangmanBtnTip.setShowDelay(Duration.seconds(0.5));
        mcBtnTip.setShowDelay(Duration.seconds(0.5));
        gtwBtnTip.setShowDelay(Duration.seconds(0.5));

        hangmanBtn.setOnAction(actionEvent -> {
            try {
                // showPane("/view/HangmanUI.fxml");
                System.out.println("Hangman");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mcBtn.setOnAction(actionEvent -> {
            try {
                showPane("/view/MultipleChoiceUI.fxml");
                // System.out.println("Multiple Choice");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        gtwBtn.setOnAction(actionEvent -> {
            try {
                // showPane("/view/GuessTheWordUI.fxml");
                System.out.println("Guess The Word");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}
