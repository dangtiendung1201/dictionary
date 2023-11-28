package controller;

import alert.Alerts;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import word.Word;

import java.net.ConnectException;
import java.util.List;

import static controller.APIController.apiCallCount;
import static service.SpeechAPI.getSpeechFromText;

public class MyListController extends TranslationController {
    public Label removeFromList;
    public Label addToList;
    @FXML
    private Tooltip searchBtnTip, lookUpBtnTip, soundBtnTip, addBtnTip, updateBtnTip, deleteBtnTip, confirmBtnTip,
            favoriteOnBtnTip, favoriteOffBtnTip;
    @FXML
    private Button searchBtn, lookUpBtn, soundBtn, addBtn, updateBtn, deleteBtn, confirmBtn, favoriteOnBtn, favoriteOffBtn;
    @FXML
    private TextField searchBox;
    @FXML
    private TextArea pronunciationBox, wordTypeBox, meaningBox, exampleBox, relatedWordBox;
    @FXML
    private Label englishWord, headerList, notAvailableAlert;
    @FXML
    private ListView<String> resultList;
    private STATE currentState = STATE.NONE;

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
        addToList.setVisible(false);
        removeFromList.setVisible(false);
        resultList.getItems().clear();
        try {
            List<Word> searchList = management.myListSearcher(searchedWord);
            for (Word w : searchList) {
                String s = w.getWordTarget();
                resultList.getItems().add(s);
            }
        } catch (IllegalArgumentException e) {
            notAvailableAlert.setVisible(true);
        }
    }

    private void displayingWord(Word word) {
        meaningBox.setText(word.getWordExplain());
        pronunciationBox.setText(word.getIPA());
        exampleBox.setText(word.getExamples());
        relatedWordBox.setText(word.getRelatedWords());
        wordTypeBox.setText(word.getWordTypes());
        currentState = STATE.DISPLAYING;
        deleteBtn.setVisible(true);
        updateBtn.setVisible(true);
        addToList.setVisible(false);
        removeFromList.setVisible(false);
    }

    private void handleLookUpBtn() {
        String lookedUpWord = searchBox.getText();
        resultList.getItems().clear();
        clearAllBoxes();
        notAvailableAlert.setVisible(false);
        addToList.setVisible(false);
        removeFromList.setVisible(false);

        englishWord.setText(lookedUpWord);

        try {
            Word lookUp = management.myListLookUp(lookedUpWord);
            displayingWord(lookUp);
        } catch (IllegalArgumentException e) {

            notAvailableAlert.setVisible(true);
            clearAllBoxes();

            resultList.getItems().clear();
            List<String> suggestions = management.myListSearchSuggestion(lookedUpWord);
            for (String s : suggestions) {
                resultList.getItems().add(s);
            }

            currentState = STATE.NONE;
            deleteBtn.setVisible(false);
            updateBtn.setVisible(false);
        }
    }

    private void handleSoundBtn() {
        setDefaultDisplayingState();

        Task<Void> apiCallTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    getSpeechFromText(englishWord.getText(), "English");
                } catch (ConnectException e) {
                    Alert alert = new Alerts().error("Error",
                            "No Internet Connection",
                            "Please check your internet connection.");
                    alert.show();
                } catch (Exception e) {
                    Alert alert = new Alerts().error("Error",
                            "Unknown Error",
                            "There is an error, please try again.");
                    alert.show();
                }

                return null;
            }
        };
        new Thread(apiCallTask).start();

    }

    private void handleAddBtn() {
        setDefaultDisplayingState();
        searchBox.clear();
        updateBtn.setVisible(false);
        deleteBtn.setVisible(false);
        if (currentState != STATE.ADDING) {

            currentState = STATE.ADDING;
            clearAllBoxes();
            englishWord.setText("");

            pronunciationBox.setEditable(true);
            wordTypeBox.setEditable(true);
            meaningBox.setEditable(true);
            exampleBox.setEditable(true);
            relatedWordBox.setEditable(true);
            confirmBtn.setVisible(true);

        } else {
            currentState = STATE.NONE;
            confirmBtn.setVisible(false);
            updateBtn.setVisible(false);
            deleteBtn.setVisible(false);
        }
    }

    private void handleUpdateBtn() {
        setDefaultDisplayingState();
        if (currentState != STATE.UPDATING) {
            currentState = STATE.UPDATING;

            pronunciationBox.setEditable(true);
            wordTypeBox.setEditable(true);
            meaningBox.setEditable(true);
            exampleBox.setEditable(true);
            relatedWordBox.setEditable(true);

            confirmBtn.setVisible(true);
        } else {
            currentState = STATE.DISPLAYING;
        }
    }

    private void handleDeleteBtn() {
        setDefaultDisplayingState();
        if (currentState != STATE.DELETING) {
            currentState = STATE.DELETING;
            confirmBtn.setVisible(true);
        } else {
            currentState = STATE.DISPLAYING;
        }
    }

    // confirm button show only when update or delete button clicked
    private void handleConfirmBtn() {
        if (confirmBtn.isVisible()) {
            String currentWord = englishWord.getText();
            if (currentState == STATE.UPDATING) {
                try {
                    Word word = management.dictionaryLookUp(currentWord);

                    management.removeWord(word);

                    word.setIPA(pronunciationBox.getText());
                    word.setRelatedWords(relatedWordBox.getText());
                    word.setWordExplain(meaningBox.getText());
                    word.setExamples(exampleBox.getText());
                    word.setWordTypes(wordTypeBox.getText());

                    management.addWord(word);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                currentState = STATE.DISPLAYING;
            } else if (currentState == STATE.DELETING) {
                try {
                    management.removeWord(currentWord);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                currentState = STATE.NONE;

                englishWord.setText("");
                clearAllBoxes();
                resultList.getItems().clear();
                currentState = STATE.NONE;
                deleteBtn.setVisible(false);
                updateBtn.setVisible(false);


            } else if (currentState == STATE.ADDING) {
                try {
                    String wordTarget = searchBox.getText().isEmpty() ? "N/A" : searchBox.getText();
                    String wordExplain = meaningBox.getText().isEmpty() ? "N/A" : meaningBox.getText();
                    String IPA = pronunciationBox.getText().isEmpty() ? "N/A" : pronunciationBox.getText();
                    String wordTypes = wordTypeBox.getText().isEmpty() ? "N/A" : wordTypeBox.getText();
                    String examples = exampleBox.getText().isEmpty() ? "N/A" : exampleBox.getText();
                    String relatedWords = relatedWordBox.getText().isEmpty() ? "N/A" : relatedWordBox.getText();
                    try {
                        System.out.println(wordTarget);
                        management.addWord(new Word(wordTarget, wordExplain, IPA, wordTypes,
                                examples, relatedWords));
                    } catch (IllegalArgumentException ignored) {
                        System.out.println("Từ được thêm vào không hợp lệ!");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                currentState = STATE.NONE;
            }
        }
        addToList.setVisible(false);
        removeFromList.setVisible(false);
        confirmBtn.setVisible(false);
        setDefaultDisplayingState();
    }

    private void handleFavoriteOnBtn() {
        System.out.println("Favorite on button clicked");

        String currentWord = englishWord.getText();
        management.myListRemoveWord(currentWord);

        addToList.setVisible(false);
        removeFromList.setVisible(true);

        favoriteOnBtn.setVisible(false);
        favoriteOffBtn.setVisible(true);
    }

    private void handleFavoriteOffBtn() {
        System.out.println("Favorite off button clicked");

        String currentWord = englishWord.getText();
        Word favouriteWord = management.dictionaryLookUp(currentWord);
        management.myListAddWord(favouriteWord);

        addToList.setVisible(true);
        removeFromList.setVisible(false);

        favoriteOnBtn.setVisible(true);
        favoriteOffBtn.setVisible(false);
    }

    // Call lookup function
    private void handleResultList() {
        String chosenWord = resultList.getSelectionModel().getSelectedItem();
        System.out.println("Word " + chosenWord + " is chosen\n");
        englishWord.setText(chosenWord);
        notAvailableAlert.setVisible(false);
        try {
            Word word = management.myListLookUp(chosenWord);
            favoriteOnBtn.setVisible(true);
            favoriteOffBtn.setVisible(false);
            if (word == null) {
                System.out.println("Word " + chosenWord +
                        " is not in my list, searching in dictionary.\n");
                word = management.dictionaryLookUp(chosenWord);
                favoriteOnBtn.setVisible(false);
                favoriteOffBtn.setVisible(true);
            }
            displayingWord(word);
            currentState = STATE.DISPLAYING;
            deleteBtn.setVisible(true);
            updateBtn.setVisible(true);
        } catch (IllegalArgumentException e) {
            try {
                Word word = management.dictionaryLookUp(chosenWord);
                displayingWord(word);
                favoriteOnBtn.setVisible(false);
                favoriteOffBtn.setVisible(true);
                displayingWord(word);
                currentState = STATE.DISPLAYING;
                deleteBtn.setVisible(true);
                updateBtn.setVisible(true);
            } catch (IllegalArgumentException ignored) {
                notAvailableAlert.setVisible(true);
                clearAllBoxes();
                currentState = STATE.NONE;
                deleteBtn.setVisible(false);
                updateBtn.setVisible(false);
            }
        }
    }

    private void handleOnKeyTyped() {
        resultList.getItems().clear();
        String searchKey = searchBox.getText().trim();
        try {
            List<Word> searchList = management.myListSearcher(searchKey);
            for (Word w : searchList) {
                String s = w.getWordTarget();
                resultList.getItems().add(s);
            }
        } catch (IllegalArgumentException e) {
            notAvailableAlert.setVisible(true);
        }
    }

    private void showMyList() {
        resultList.getItems().clear();
        try {
            List<Word> searchList = management.allMyListWord();
            for (Word w : searchList) {
                String s = w.getWordTarget();
                resultList.getItems().add(s);
            }
        } catch (IllegalArgumentException e) {
            notAvailableAlert.setVisible(true);
        }
    }

    private void setDefaultDisplayingState() {
        pronunciationBox.setEditable(false);
        wordTypeBox.setEditable(false);
        meaningBox.setEditable(false);
        exampleBox.setEditable(false);
        relatedWordBox.setEditable(false);
        confirmBtn.setVisible(false);
        addToList.setVisible(false);
        removeFromList.setVisible(false);
    }

    @FXML
    public void initialize() {
        // Set tooltip for buttons
        searchBtn.setTooltip(searchBtnTip);
        lookUpBtn.setTooltip(lookUpBtnTip);
        soundBtn.setTooltip(soundBtnTip);
        addBtn.setTooltip(addBtnTip);
        updateBtn.setTooltip(updateBtnTip);
        deleteBtn.setTooltip(deleteBtnTip);
        confirmBtn.setTooltip(confirmBtnTip);
        favoriteOnBtn.setTooltip(favoriteOnBtnTip);
        favoriteOffBtn.setTooltip(favoriteOffBtnTip);

        favoriteOnBtn.setVisible(false);
        favoriteOffBtn.setVisible(false);

        addToList.setVisible(false);
        removeFromList.setVisible(false);

        showMyList();

        setDefaultDisplayingState();

        // Set default value for confirmBtn
        confirmBtn.setVisible(false);
        deleteBtn.setVisible(false);
        updateBtn.setVisible(false);

        headerList.setText("Word List");

        notAvailableAlert.setVisible(false);

        // handle type key
        searchBox.setOnKeyTyped(keyEvent -> {
            if (!searchBox.getText().isEmpty()) {
                handleOnKeyTyped();
            } else {
                showMyList();
            }
        });

        searchBtn.setOnAction(e -> {
            handleSearchBtn();
        });

        lookUpBtn.setOnAction(e -> {
            handleLookUpBtn();
        });

        soundBtn.setOnAction(e -> {
            handleSoundBtn();
        });

        addBtn.setOnAction(e -> {
            handleAddBtn();
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

    private enum STATE {
        NONE,
        DISPLAYING,
        UPDATING,
        DELETING,
        ADDING
    }
}
