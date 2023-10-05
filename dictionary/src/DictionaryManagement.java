import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

import static java.lang.Math.min;

public class DictionaryManagement {
    private final Trie T;

    DictionaryManagement() {
        T = new Trie();
    }

    /**
     * Insert data from a .txt file.
     * @param path  data file path
     */
    public void insertFromFile(String path) {
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                addWord(getWordFromLine(data));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        }

    }

    /**
     * Get word from a line with English word and its definition are seperated by a tab (/t)
     * @param data a line with format: "wordTarget '\t' wordExplain"
     * @return Word
     */
    private Word getWordFromLine(String data) {
        String wordTarget = "";
        String wordExplain = "";
        boolean isWordTarget = true;
        for (int i = 0; i < data.length(); i ++) {
            char cur = data.charAt(i);
            if (cur == '\t') {
                isWordTarget = false;
                continue;
            }
            if (isWordTarget) {
                wordTarget += cur;
            }
            else {
                wordExplain += cur;
            }
        }
        return new Word(wordTarget, wordExplain);
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

    public static void main(String[] args) {
        DictionaryManagement DM = new DictionaryManagement();
        DM.addWord(new Word("House", "Căn nhà"));
        DM.addWord(new Word("Home", "Ngôi nhà"));
        DM.addWord(new Word("Household", "Căn hộ"));
        DM.addWord(new Word("Mouse", "Con chuột"));
        Scanner s = new Scanner(System.in);
        String pat = s.nextLine();
        try{
            ArrayList<Word> arrayList = DM.dictionaryLookUp(pat);
            System.out.println(arrayList);
        } catch (IllegalArgumentException ignored){
            System.out.println(DM.searchSuggestions(pat));
        }
    }
}