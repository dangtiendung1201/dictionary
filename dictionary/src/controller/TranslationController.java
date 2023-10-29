package controller;

import javafx.util.Duration;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class TranslationController extends Controller {
    @FXML
    private Tooltip searchBtnTip, lookUpBtnTip, soundBtnTip, updateBtnTip, deleteBtnTip, confirmBtnTip, favoriteOnBtnTip, favoriteOffBtnTip;
    @FXML
    private Button searchBtn, lookUpBtn, soundBtn, updateBtn, deleteBtn, confirmBtn, favoriteOnBtn, favoriteOffBtn;
    @FXML
    private TextField searchBox;
    @FXML
    private TextArea pronunciationBox, wordTypeBox, meaningBox, exampleBox, relatedWordBox;
    @FXML
    private Label englishWord, headerList;
    @FXML
    private ListView<String> resultList;

    private void handleSearchBtn() {
        System.out.println("Search button clicked");
        System.out.println(searchBox.getText());
    }

    private void handleLookUpBtn() {
        System.out.println("Look up button clicked");
        System.out.println(searchBox.getText());
    }

    private void handleSoundBtn() {
        System.out.println("Sound button clicked");
    }

    private void handleUpdateBtn() {
        System.out.println("Update button clicked");
        if (confirmBtn.isVisible()) {
            confirmBtn.setVisible(false);
        }
        else {
            confirmBtn.setVisible(true);
        }
    }

    private void handleDeleteBtn() {
        System.out.println("Delete button clicked");
        if (confirmBtn.isVisible()) {
            confirmBtn.setVisible(false);
        }
        else {
            confirmBtn.setVisible(true);
        }
    }

    // confirm button show only when update or delete button clicked
    private void handleConfirmBtn() {
        System.out.println("Confirm button clicked");
        confirmBtn.setVisible(false);
    }

    private void handleFavoriteOnBtn() {
        System.out.println("Favorite on button clicked");
        favoriteOnBtn.setVisible(false);
        favoriteOffBtn.setVisible(true);
    }

    private void handleFavoriteOffBtn() {
        System.out.println("Favorite off button clicked");
        favoriteOnBtn.setVisible(true);
        favoriteOffBtn.setVisible(false);
    }

    // Call lookup fuction
    private void handleResultList() {
        System.out.println(resultList.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void initialize() {
        // Set tooltip for buttons
        searchBtn.setTooltip(searchBtnTip);
        lookUpBtn.setTooltip(lookUpBtnTip);
        soundBtn.setTooltip(soundBtnTip);
        updateBtn.setTooltip(updateBtnTip);
        deleteBtn.setTooltip(deleteBtnTip);
        confirmBtn.setTooltip(confirmBtnTip);
        favoriteOnBtn.setTooltip(favoriteOnBtnTip);
        favoriteOffBtn.setTooltip(favoriteOffBtnTip);

        // Set default value for confirmBtn
        confirmBtn.setVisible(false);

        // Set default value for searchField
        // searchBox.setText("Ahihi");

        // Set default value for pronunciationBox
        pronunciationBox.setText("Ahihi");

        // Set default value for wordTypeBox
        wordTypeBox.setText("Ahihi");

        // Set default value for meaningBox
        meaningBox.setText("Ahihi");

        // Set default value for exampleBox
        exampleBox.setText("Ahihi");

        // Set default value for relatedWordBox
        relatedWordBox.setText("Ahihi");

        // Set default value for englishWord
        englishWord.setText("Ahihi");

        // Set default value for favoriteOnBtn
        favoriteOnBtn.setVisible(false);
        favoriteOffBtn.setVisible(true);

        // Set default value for headerList
        headerList.setText("Search result");

        // Set default value for resultList
        resultList.getItems().add("No result");
        resultList.getItems().add("Truong");
        resultList.getItems().add("Dai");
        resultList.getItems().add("Hoc");
        resultList.getItems().add("Cong");
        resultList.getItems().add("Nghe");
        resultList.getItems().add("Khung");
        resultList.getItems().add("Hon");
        resultList.getItems().add("Bach");
        resultList.getItems().add("Khoa");
        resultList.getItems().add("Ha");
        resultList.getItems().add("Noi");

        searchBtn.setOnAction(e -> {
            handleSearchBtn();   
        });

        lookUpBtn.setOnAction(e -> {
            handleLookUpBtn();   
        });

        soundBtn.setOnAction(e -> {
            handleSoundBtn();   
        });

        updateBtn.setOnAction(e -> {
            handleUpdateBtn();   
        });

        deleteBtn.setOnAction(e -> {
            handleDeleteBtn();   
        });

        confirmBtn.setOnAction(e -> {
            handleConfirmBtn();   
        });

        favoriteOnBtn.setOnAction(e -> {
            handleFavoriteOnBtn();
        });

        favoriteOffBtn.setOnAction(e -> {
            handleFavoriteOffBtn();
        });

        // handle click on word in resultList
        resultList.setOnMouseClicked(e -> {
            handleResultList();
        });

    }
}
