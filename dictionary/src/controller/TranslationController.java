package controller;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.util.Duration;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import service.SpeechAPI;
import word.Word;

import java.util.ArrayList;
import java.util.List;

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
    private Label englishWord, headerList, notAvailableAlert;
    @FXML
    private ListView<String> resultList;

    private void clearAllBoxes() {
        searchBox.clear();
        pronunciationBox.clear();
        wordTypeBox.clear();
        meaningBox.clear();
        exampleBox.clear();
        relatedWordBox.clear();
    }

    private void handleSearchBtn() {
        String searchedWord = searchBox.getText();
        clearAllBoxes();
        notAvailableAlert.setVisible(false);
        try {
            List<Word> searchList = management.dictionarySearcher(searchedWord);
            resultList.getItems().clear();
            for(Word w : searchList) {
                String s = w.getWordTarget();
                resultList.getItems().add(s);
            }
        } catch (IllegalArgumentException e) {
            notAvailableAlert.setVisible(true);
        }
    }

    private void setLookedUpWord(Word word) {
        meaningBox.setText(word.getWordExplain());
        pronunciationBox.setText(word.getIPA());
        exampleBox.setText(word.getExamples());
        relatedWordBox.setText(word.getRelatedWords());
        wordTypeBox.setText(word.getWordTypes());
    }
    private void handleLookUpBtn() {
        String lookedUpWord = searchBox.getText();
        resultList.getItems().clear();
        notAvailableAlert.setVisible(false);
        englishWord.setText(lookedUpWord);
        meaningBox.clear();
        try {
            List<Word> searchList = management.dictionaryLookUp(lookedUpWord);
            setLookedUpWord(searchList.get(0));
        } catch (IllegalArgumentException e) {
            notAvailableAlert.setVisible(true);
            clearAllBoxes();

            resultList.getItems().clear();
            List<String> suggestions = management.searchSuggestions(lookedUpWord);
            for(String s : suggestions) {
                resultList.getItems().add(s);
            }
        }
    }

    private void handleSoundBtn() {
        SpeechAPI voice = new SpeechAPI();
        try {
            voice.speak(englishWord.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleUpdateBtn() {
        if (confirmBtn.isVisible()) {
            confirmBtn.setVisible(false);
        } else {
            confirmBtn.setVisible(true);
        }
    }

    private void handleDeleteBtn() {
        System.out.println("Delete button clicked");
        if (confirmBtn.isVisible()) {
            confirmBtn.setVisible(false);
        } else {
            confirmBtn.setVisible(true);
        }
    }

    // confirm button show only when update or delete button clicked
    private void handleConfirmBtn() {
        if (confirmBtn.isVisible()) {
            String currentWord = englishWord.getText();
            try {
                ArrayList<Word> wordList = management.dictionaryLookUp(currentWord);
                Word word = wordList.get(0);
                management.removeWord(word);
                word.setIPA(pronunciationBox.getText());
                word.setRelatedWords(relatedWordBox.getText());
                word.setWordExplain(meaningBox.getText());
                word.setExamples(exampleBox.getText());
                word.setWordTypes(wordTypeBox.getText());
                management.addWord(word);
            } catch (IllegalArgumentException ignored) {

            }
        }
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
        String chosenWord = resultList.getSelectionModel().getSelectedItem();
        englishWord.setText(chosenWord);
        notAvailableAlert.setVisible(false);
        try {
            List<Word> searchList = management.dictionaryLookUp(chosenWord);
            setLookedUpWord(searchList.get(0));
        } catch (IllegalArgumentException e){
            notAvailableAlert.setVisible(true);
            clearAllBoxes();
        }
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

        headerList.setText("Search result");

        notAvailableAlert.setVisible(false);

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
