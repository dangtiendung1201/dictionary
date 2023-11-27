package management;

import trie.Trie;
import trie.exception.RemoveWordException;
import word.Word;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {
    private final Trie T;
    private final Trie myList;

    public DictionaryManagement() {
        T = new Trie();
        myList = new Trie();
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
                word = word.toLine();
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

    public void insertMyWordListFromFile(String path) {
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                myList.addWord(getWordFromLine(data));
            }
            System.out.println("Done.");
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }


    public void exportMyWordListToFile(String path) {
        try {
            File myObj = new File(path);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
            java.io.FileWriter myWriter = new java.io.FileWriter(path);
            ArrayList<Word> words = myList.allWords();
            for (Word word : words) {
                word = word.toLine();
                myWriter.write(word.getWordTarget() + "\t"
                        + word.getIPA() + "\t"
                        + word.getWordTypes() + "\t"
                        + word.getWordExplain() + "\t"
                        + word.getExamples() + "\t"
                        + word.getRelatedWords() + "\n");
                System.out.println("Wrote " + word.getWordTarget() + " to file. \n");
            }
            myWriter.close();
            System.out.println("Successfully wrote my wordlist to the file.");
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
                wordTypes, examples, relatedWords).getDisplayingWord();
    }

    public ArrayList<Word> allDictionaryWord() {
        return T.allWords();
    }

    public Word dictionaryLookUp(String s) {
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

    public void removeWord(String wordTarget) throws RemoveWordException {
        T.removeWord(wordTarget);
    }

    public void showWordList(ArrayList<Word> wordList) {
        System.out.printf("%-10s %-32s %-32s\n", "STT", "| English", "| Vietnamese");
        for (int i = 0; i < wordList.size(); i++) {
            System.out.printf("%-10d %-32s %-32s\n", i + 1, "| " + wordList.get(i).getWordTarget(),
                    "| " + wordList.get(i).getWordExplain());
        }
    }

    public ArrayList<Word> allMyListWord() {
        return myList.allWords();
    }

    public Word myListLookUp(String s) {
        return myList.lookUpWord(s);
    }

    public ArrayList<Word> myListSearcher(String s) {
        return myList.searchWord(s);
    }

    public ArrayList<String> myListSearchSuggestion(String enteredWord) {
        return myList.searchSuggestions(enteredWord, 1000);
    }

    public void myListAddWord(Word word) {
        myList.addWord(word);
    }

    public void myListRemoveWord(Word word) {
        myList.removeWord(word);
    }

    public void myListRemoveWord(String wordTarget) {
        myList.removeWord(wordTarget);
    }

}