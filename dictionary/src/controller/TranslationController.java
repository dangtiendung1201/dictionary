package controller;

import alert.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import trie.exception.AddWordException;
import trie.exception.RemoveWordException;
import trie.exception.SearchWordException;
import word.Word;
import word.exception.InvalidWordException;

import java.net.ConnectException;
import java.util.List;

import static service.SpeechAPI.getSpeechFromText;

public class TranslationController extends Controller {
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

    private int maxResultListSize = 100;

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
        resultList.getItems().clear();
        try {
            List<Word> searchList = management.dictionarySearcher(searchedWord);
            if (searchList.size() > maxResultListSize) {
                searchList = searchList.subList(0, maxResultListSize);
            }
            for (Word w : searchList) {
                String s = w.getWordTarget();
                resultList.getItems().add(s);
            }
        } catch (SearchWordException e) {
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
    }

    private void handleLookUpBtn() {
        String lookedUpWord = searchBox.getText();
        resultList.getItems().clear();
        clearAllBoxes();
        notAvailableAlert.setVisible(false);
        englishWord.setText(lookedUpWord);

        try {
            Word word = management.dictionaryLookUp(lookedUpWord);
            displayingWord(word);
        } catch (Exception e) {
            notAvailableAlert.setVisible(true);
            clearAllBoxes();
            resultList.getItems().clear();
            List<String> suggestions = management.searchSuggestions(lookedUpWord);
            for (String s : suggestions) {
                resultList.getItems().add(s);
            }

            currentState = STATE.NONE;
            deleteBtn.setVisible(false);
            updateBtn.setVisible(false);
        }
    }

    private void handleSoundBtn() {
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
            searchBox.setPromptText("Nhập từ bạn muốn thêm");

            pronunciationBox.setEditable(true);
            wordTypeBox.setEditable(true);
            meaningBox.setEditable(true);
            exampleBox.setEditable(true);
            relatedWordBox.setEditable(true);
            confirmBtn.setVisible(true);

        } else {
            currentState = STATE.NONE;
            searchBox.setPromptText("Nhập từ");
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

    /** avoid bug.
     *
     */
    private void eraseTabInAllBox() {
        pronunciationBox.setText(pronunciationBox.getText().replace("\t", ""));
        wordTypeBox.setText(wordTypeBox.getText().replace("\t", ""));
        meaningBox.setText(meaningBox.getText().replace("\t", ""));
        exampleBox.setText(exampleBox.getText().replace("\t", ""));
        relatedWordBox.setText(relatedWordBox.getText().replace("\t", ""));
        searchBox.setText(searchBox.getText().replace("\t", ""));
    }
    // confirm button show only when update or delete button clicked
    private void handleConfirmBtn() {
        if (confirmBtn.isVisible()) {
            String currentWord = englishWord.getText();
            if (currentState == STATE.UPDATING) {
                try {
                    Word word = management.dictionaryLookUp(currentWord);

                    management.removeWord(word);

                    eraseTabInAllBox();
                    word.setIPA(pronunciationBox.getText());
                    word.setRelatedWords(relatedWordBox.getText());
                    word.setWordExplain(meaningBox.getText());
                    word.setExamples(exampleBox.getText());
                    word.setWordTypes(wordTypeBox.getText());

                    management.addWord(word.toLine());
                } catch (RuntimeException e) {
                    Alert alert = new Alerts().error("Error", "Update word error!", e.getMessage());
                    alert.show();
                }
                currentState = STATE.DISPLAYING;
            } else if (currentState == STATE.DELETING) {
                try {
                    management.removeWord(currentWord);
                } catch (RemoveWordException removeWordException) {
                    System.out.println(removeWordException.getMessage());
                    Alert alert = new Alerts().error("Error", "Remove word error", removeWordException.getMessage());;
                    alert.show();
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
                    eraseTabInAllBox();
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
                    } catch (InvalidWordException | AddWordException e) {
                        System.out.println(e.getMessage());
                        Alert alert = new Alerts().error("Error", "Add word error!", e.getMessage());
                        alert.show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                currentState = STATE.NONE;
            }
        }
        confirmBtn.setVisible(false);
        setDefaultDisplayingState();
    }

    private void handleFavoriteOnBtn() {
        System.out.println("Favorite on button clicked");

        String currentWord = englishWord.getText();
        if (!searchBox.getText().isEmpty())
            management.myListRemoveWord(currentWord);

        favoriteOnBtn.setVisible(false);
        favoriteOffBtn.setVisible(true);
    }

    private void handleFavoriteOffBtn() {
        System.out.println("Favorite off button clicked");

        String currentWord = englishWord.getText();
        Word favouriteWord = management.dictionaryLookUp(currentWord);
        if (!searchBox.getText().isEmpty())
            management.myListAddWord(favouriteWord);

        favoriteOnBtn.setVisible(true);
        favoriteOffBtn.setVisible(false);
    }

    // Call lookup function
    private void handleResultList() {
        String chosenWord = resultList.getSelectionModel().getSelectedItem();
        englishWord.setText(chosenWord);
        notAvailableAlert.setVisible(false);
        try {
            Word word = management.dictionaryLookUp(chosenWord);
            try {
                if (management.myListLookUp(chosenWord) == null) {
                    favoriteOnBtn.setVisible(true);
                    favoriteOffBtn.setVisible(false);
                } else {
                    favoriteOnBtn.setVisible(false);
                    favoriteOffBtn.setVisible(true);
                }
            } catch (SearchWordException e) {
                favoriteOnBtn.setVisible(false);
                favoriteOffBtn.setVisible(true);
            }

            displayingWord(word);
            currentState = STATE.DISPLAYING;
            deleteBtn.setVisible(true);
            updateBtn.setVisible(true);
        } catch (SearchWordException e) {
            notAvailableAlert.setVisible(true);
            clearAllBoxes();
            currentState = STATE.NONE;
            deleteBtn.setVisible(false);
            updateBtn.setVisible(false);
        }
    }

    private void handleOnKeyTyped() {
        if (currentState == STATE.ADDING) {
            return;
        }
        resultList.getItems().clear();
        String searchKey = searchBox.getText().trim();
        try {
            notAvailableAlert.setVisible(false);
            List<Word> searchList = management.dictionarySearcher(searchKey);
            if (searchList.size() > maxResultListSize) {
                searchList = searchList.subList(0, maxResultListSize);
            }
            for (Word w : searchList) {
                String s = w.getWordTarget();
                resultList.getItems().add(s);
            }
        } catch (SearchWordException e) {
            notAvailableAlert.setVisible(true);
        }
    }

    private void setDefaultDisplayingState() {
        searchBox.setPromptText("Nhập từ");
        pronunciationBox.setEditable(false);
        wordTypeBox.setEditable(false);
        meaningBox.setEditable(false);
        exampleBox.setEditable(false);
        relatedWordBox.setEditable(false);
        confirmBtn.setVisible(false);
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

        setDefaultDisplayingState();

        // Set default value for confirmBtn
        confirmBtn.setVisible(false);
        deleteBtn.setVisible(false);
        updateBtn.setVisible(false);

        headerList.setText("Search result");

        notAvailableAlert.setVisible(false);

        // handle type key
        searchBox.setOnKeyTyped(keyEvent -> {
            if (!searchBox.getText().isEmpty()) {
                handleOnKeyTyped();
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
