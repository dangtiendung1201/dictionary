import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import word.Word;
import game.*;

public class DictionaryCommandLine extends DictionaryManagement {
    private int curAction = 1;

    public static void main(String[] args) {
        DictionaryCommandLine test = new DictionaryCommandLine();
        test.dictionaryAdvanced();
    }

    /**
     * Show all words alphabetically.
     */
    public void showAllWords() {
        System.out.println("Show all words. ");
        showWordList(allDictionaryWord());
        waitEnter();
    }

    /**
     * Call insertFromCommandline() function from DictionaryManagement and
     * showAllWords().
     */
    public void dictionaryBasic() {
    }

    /**
     * Look up for the word.
     */
    public void lookUpWord() {
        System.out.println("Look up word.");
        System.out.println("Input the word you want to look up: ");
        Scanner sc = new Scanner(System.in);
        String lookUpWord = sc.nextLine();
        try {
            ArrayList<Word> words = dictionaryLookUp(lookUpWord);
            showWordList(words);
        } catch (IllegalArgumentException ignored) {
            System.out.println("This word doesn't exist in the dictionary!");
            ArrayList<String> suggestions = searchSuggestions(lookUpWord);
            System.out.println("Did you mean: ");
            for (int i = 0; i < suggestions.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + suggestions.get(i));
            }  
        }
        waitEnter();
    }

    // Add word to dictionary.
    public void addWord() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Add new words to dictionary.");
        System.out.println("Input the number of words: ");
        int n = sc.nextInt();
        String tmp = sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("Input vocabulary: ");
            String wordTarget = sc.nextLine();
            System.out.println("Input word's explanation: ");
            String wordExplain = sc.nextLine();
            try {
                addWord(new Word(wordTarget, wordExplain));
            } catch (IllegalArgumentException ignored) {
                System.out.println("This word has some invalid characters");
            }
        }
        waitEnter();
    }

    /**
     * Insert data from file.
     */
    public void insertFromFile() {
        System.out.println("Add vocabulary from file. ");
        // System.out.println("[1] Input file path: ");
        // System.out.println("[2] Input file name with current path: ");
        System.out.println("Input file name: ");
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();
        path = System.getProperty("user.dir") + "/dictionary/data/" + path;
        System.out.println(path);
        insertFromFile(path);
        waitEnter();
    }

    private void printGap() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
        System.out.println("----------------------------------------");
    }

    private void waitEnter() {
        System.out.println("Press enter to continue...");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }

    // Show the menu.
    public void dictionaryAdvanced() {
        System.out.println("\n\nWelcome to My Dictionary!\n\n");

        System.out.println("\n\n\n----------------------------------------\n\n\n");
        while (curAction != 0) {
            try {
                System.out.println("[0] Exit");
                System.out.println("[1] Add");
                System.out.println("[2] Remove");
                System.out.println("[3] Update");
                System.out.println("[4] Display");
                System.out.println("[5] Lookup");
                System.out.println("[6] Search");
                System.out.println("[7] Game");
                System.out.println("[8] Import from file");
                System.out.println("[9] Export to file");
                System.out.println("Your action: ");
                Scanner sc = new Scanner(System.in);
                curAction = sc.nextInt();
                if (curAction < 0 || curAction > 9)
                    throw new InputMismatchException();
                printGap();

                switch (curAction) {
                    case 0:
                        break;
                    case 1:
                        addWord();
                        break;
                    case 2:
                        removeWord();
                        break;
                    case 3:
                        updateWord();
                        break;
                    case 4:
                        showAllWords();
                        break;
                    case 5:
                        lookUpWord();
                        break;
                    case 6:
                        searchWord();
                        break;
                    case 7:
                        startGame();
                        break;
                    case 8:
                        insertFromFile();
                        break;
                    case 9:
                        exportToFile();
                        break;
                }
                printGap();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input!");
            }
        }

    }

    private void searchWord() {
        try {
            System.out.println("Search word.");
            System.out.println("Input the word you want to search: ");
            Scanner sc = new Scanner(System.in);
            String searchWord = sc.nextLine();
            ArrayList<Word> words = dictionarySearcher(searchWord);
            showWordList(words);
        } catch (IllegalArgumentException ignored) {
            System.out.println("This prefix doesn't exist in the dictionary!");
        }
        waitEnter();
    }

    private void exportToFile() {
        System.out.println("Export to file.");
        System.out.println("Input file name: ");
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine();
        path = System.getProperty("user.dir") + "/dictionary/data/" + path;
        exportToFile(path);
        waitEnter();
    }

    private void updateWord() {
        try {
            System.out.println("Update word.");
            System.out.println("Input the word you want to update: ");
            Scanner sc = new Scanner(System.in);
            String updateWord = sc.nextLine();
            ArrayList<Word> words = dictionaryLookUp(updateWord);
            showWordList(words);
            System.out.println("Choose number of the word you want to update: ");
            int updateIndex = sc.nextInt();
            Word targetWord = words.get(updateIndex - 1);
            if (updateIndex < 1 || updateIndex > words.size())
                throw new IllegalArgumentException();
            System.out.println("Input new explanation: ");
            String newExplain = sc.nextLine();
            newExplain = sc.nextLine();
            removeWord(targetWord);
            addWord(new Word(targetWord.getWordTarget(), newExplain));
        } catch (IllegalArgumentException ignored) {
            System.out.println("This word doesn't exist in the dictionary!");
        }
        waitEnter();
    }

    private void removeWord() {
        try {
            System.out.println("Remove word.");
            System.out.println("Input the word you want to remove: ");
            Scanner sc = new Scanner(System.in);
            String removeWord = sc.nextLine();
            ArrayList<Word> words = dictionaryLookUp(removeWord);
            showWordList(words);
            System.out.println("Choose number of the word you want to remove: ");
            int removeIndex = sc.nextInt();
            if (removeIndex < 1 || removeIndex > words.size())
                throw new IllegalArgumentException();
            removeWord(words.get(removeIndex - 1));
        } catch (IllegalArgumentException ignored) {
            System.out.println("This word doesn't exist in the dictionary!");
        }
        waitEnter();
    }

    private void startGame() {
        GameManagement game = new GameManagement();
        game.printMenu();
    }
}