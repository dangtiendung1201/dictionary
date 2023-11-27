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
    private Tooltip dictionaryBtnTip, gameBtnTip, APIBtnTip, exitBtnTip, myListBtnTip, homeTip;
    @FXML
    private Button dictionaryBtn, gameBtn, APIBtn, exitBtn, myListBtn, homeButton;
    @FXML
    private AnchorPane container;

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
        showPane("/view/HomeUI.fxml");
        homeButton.setOnAction(actionEvent -> {
            try {
                container.getChildren().clear();
                showPane("/view/HomeUI.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        myListBtn.setOnAction(actionEvent -> {
            try {
                showPane("/view/MyListUI.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        dictionaryBtn.setOnAction(actionEvent -> {
            try {
                showPane("/view/TranslationUI.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        gameBtn.setOnAction(actionEvent -> {
            try {
                showPane("/view/GameUI.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        APIBtn.setOnAction(actionEvent -> {
            try {
                showPane("/view/APIUI.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        exitBtn.setOnAction(actionEvent -> {
            management.exportToFile("dictionary/resources/data/merged.txt");
            management.exportMyWordListToFile("dictionary/resources/data/myList.txt");
            System.exit(0);
        });

    }

}
