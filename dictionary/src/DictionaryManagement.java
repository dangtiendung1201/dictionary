import java.util.ArrayList;

import static java.lang.Math.min;

public class DictionaryManagement {
    private final Trie T;

    DictionaryManagement() {
        T = new Trie();
    }
    public void insertFromFile(String path) {

    }

    public ArrayList<Word> dictionaryLookUp(String s) {
        return T.lookUpWord(s);
    }

    public ArrayList<Word> dictionarySearcher(String s) {
        return T.searchWord(s);
    }

    /** If the user enters the wrong word, suggested words are listed.
     * @return suggested words.
     */
    public ArrayList<String> searchSuggestions(String enteredWord) {
        return T.searchSuggestions(enteredWord);
    }

    public void addWord(Word word) {
        T.addWord(word);
    }

    public void removeWord(Word word) {
        T.removeWord(word);
    }

    public void removeWord(String wordTarget) {
        T.removeWord(wordTarget);
    }

    public void dictionaryExportToFile(String path) {

    }
}