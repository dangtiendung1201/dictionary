package management;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import trie.Trie;
import word.Word;

public class DictionaryManagement {
    private final Trie T;

    public DictionaryManagement() {
        T = new Trie();
    }

    public static void main(String[] args) {
        DictionaryManagement DM = new DictionaryManagement();
        DM.addWord(new Word("House", "Căn nhà"));
        DM.addWord(new Word("Home", "Ngôi nhà"));
        DM.addWord(new Word("Household", "Căn hộ"));
        DM.addWord(new Word("Mouse", "Con chuột"));
        Scanner s = new Scanner(System.in);
        String pat = s.nextLine();
        try {
            ArrayList<Word> arrayList = DM.dictionaryLookUp(pat);
            System.out.println(arrayList);
        } catch (IllegalArgumentException ignored) {
            System.out.println(DM.searchSuggestions(pat));
        }
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
            int x = 0;
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
        String examples = info[4], relatedWords = info[5];

        /*
        int cnt = 0;
        for(int i = 0; i < examples.length(); i ++) {
            if (examples.charAt(i) == '|') {
                cnt ++;
                if (cnt % 2 == 1) {
                    examples = examples.substring(0, i)
                            + ":" + examples.substring(i + 1);
                }
                else {
                    examples = examples.substring(0, i)
                            + "\n" + examples.substring(i + 1);
                }
            }
        }*/

        relatedWords = relatedWords.replace(" |", ",");
        return new Word(info[0], info[3], info[1], info[2], examples, relatedWords);
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