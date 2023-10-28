package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DictionaryController extends Controller implements Initializable {
    @FXML
    public Tooltip tooltip1, tooltip2, tooltip3, tooltip4;
    @FXML
    public Button dictionaryBtn, gameBtn, APIBtn, exitBtn;
    @FXML
    public AnchorPane container;

    private void setNode(Node node) {
        container.getChildren().clear();
        container.getChildren().add(node);
    }

    private void showPane(String path) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(path));
            setNode(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tooltip1.setShowDelay(Duration.seconds(0.5));
        tooltip2.setShowDelay(Duration.seconds(0.5));
        tooltip3.setShowDelay(Duration.seconds(0.5));
        tooltip4.setShowDelay(Duration.seconds(0.5));

        // dictionaryBtn.setOnAction(actionEvent -> {
        // try {
        // AnchorPane pane =
        // FXMLLoader.load(getClass().getResource("/view/DictionaryView.fxml"));
        // container.getChildren().setAll(pane);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // });

        // gameBtn.setOnAction(actionEvent -> {
        // try {
        // AnchorPane pane =
        // FXMLLoader.load(getClass().getResource("/view/GameView.fxml"));
        // container.getChildren().setAll(pane);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // });

        dictionaryBtn.setOnAction(actionEvent -> {
            System.out.println("Dictionary");
        });

        gameBtn.setOnAction(actionEvent -> {
            System.out.println("Game");
        });

        APIBtn.setOnAction(actionEvent -> {
            try {
                showPane("/view/APIUI.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        exitBtn.setOnAction(actionEvent -> {
            System.exit(0);
        });

    }
}
