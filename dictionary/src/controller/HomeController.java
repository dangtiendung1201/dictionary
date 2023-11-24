package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController extends Controller implements Initializable {

    @FXML
    private Label englishApplicationText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        englishApplicationText.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
    }
}
