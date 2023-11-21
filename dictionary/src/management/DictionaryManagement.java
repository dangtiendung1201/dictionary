package management;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import trie.Trie;
import word.Word;

public class DictionaryManagement {
    private final Trie T;

    public DictionaryManagement() {
        T = new Trie();
    }

    public static void main(String[] args) {
        String line = "again\tə'gen\tphó từ\t lại, lần nữa, nữa\tto be home again | trở lại về nhà | to be well again | khoẻ lại, bình phục | to answer again | trả lời lại; đáp lại | rocks echoed again | những vách đá vang dội lại | again, it is necessary to bear in mind that... | hơn nữa, cần phải nhớ rằng... | these again are more expensive | vả lại những cái này đắt hơn | again and again | nhiều lần, không biết bao nhiêu lần | as much (many) again | nhiều gấp đôi | as tall again as somebody | cao gấp đôi ai | ever and again | thỉnh thoảng, đôi khi | half as much again | half as high again as somebody', \"alf again somebody's height\", 'now and again | once and again | over again | time and again\tafresh, anew, anon, bis, come again, encore, freshly, newly, once more, one more time, over, over and over, recurrently, reiteratively, repeatedly, additionally, also, besides, further, furthermore, moreover, on the contrary, on the other hand, then, de novo, more, recur";
        DictionaryManagement dm = new DictionaryManagement();
        Word word = dm.getWordFromLine(line).getDisplayingWord();
        System.out.println(word.getExamples());
    }

    /**
     * Insert data from a file.
     *
     * @param path data file path
     */
    public void insertFromFile(String path) {
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                addWord(getWordFromLine(data));
            }
            System.out.println("Done.");
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }

    }

    /**
     * Export dictionary to a file.
     *
     * @param path file path
     */
    public void exportToFile(String path) {
        try {
            File myObj = new File(path);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            java.io.FileWriter myWriter = new java.io.FileWriter(path);
            ArrayList<Word> words = T.allWords();
            for (Word word : words) {
                myWriter.write(word.getWordTarget() + "\t"
                                + word.getIPA() + "\t"
                                + word.getWordTypes() + "\t"
                                + word.getWordExplain() + "\t"
                                + word.getExamples() + "\t"
                                + word.getRelatedWords() + "\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred!");
        }
    }

    /**
     * Get word from a line with English word and its definition are seperated by a
     * tab (/t)
     *
     * @param data a line with format: "wordTarget '\t' wordExplain"
     * @return Word
     */
    private Word getWordFromLine(String data) {
        String[] info = data.split("\t");
        String wordTarget = info[0];
        String wordExplain = info[3];
        String IPA = info[1];
        String wordTypes = info[2];
        String examples = info[4];
        String relatedWords = info[5];

        return new Word(wordTarget, wordExplain, IPA,
                wordTypes, examples, relatedWords);
    }

    public ArrayList<Word> allDictionaryWord() {
        return T.allWords();
    }

    public ArrayList<Word> dictionaryLookUp(String s) {
        return T.lookUpWord(s);
    }

    public ArrayList<Word> dictionarySearcher(String s) {
        return T.searchWord(s);
    }

    /**
     * If the user enters the wrong word, suggested words are listed.
     *
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

    public void showWordList(ArrayList<Word> wordList) {
        System.out.printf("%-10s %-32s %-32s\n", "STT", "| English", "| Vietnamese");
        for (int i = 0; i < wordList.size(); i++) {
            System.out.printf("%-10d %-32s %-32s\n", i + 1, "| " + wordList.get(i).getWordTarget(),
                    "| " + wordList.get(i).getWordExplain());
        }
    }
}