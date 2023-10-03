import java.util.ArrayList;
import java.util.Scanner;

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
        System.out.println(dictionarySearcher(""));
    }

    /**
     * Call insertFromCommandline() function from DictionaryManagement and showAllWords().
     */
    public void dictionaryBasic() {
    }

    // Search the word.
    public ArrayList<Word> dictionarySearcher(String searchWord) {
        return super.dictionarySearcher(searchWord);
    }

    /**
     * Look up for the word.
     */
    public void lookUpWord() {
        System.out.println("Look up word.");
        System.out.println("Input the word you want to look up: ");
        Scanner sc = new Scanner(System.in);
        String lookUpWord = sc.nextLine();
        System.out.println(dictionaryLookUp(lookUpWord));
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
            addWord(new Word(wordTarget, wordExplain));
        }
    }

    // Show the menu.
    public void dictionaryAdvanced() {
        while (curAction != 0) {
            System.out.println("Welcome to My Dictionary!");
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
            switch (curAction) {
                case 0:
                    break;
                case 1:
                    addWord();
                    break;
                case 2:
                    //removeWord();
                    break;
                case 3:
                    //updateWord();
                    break;
                case 4:
                    showAllWords();
                    break;
                case 5:
                    lookUpWord();
                    break;

            }
        }

    }
}