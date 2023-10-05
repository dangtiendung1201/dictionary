package game;

import java.util.Scanner;

import word.Word;

public class Hangman extends GameManagement {
    enum State {
        WIN, LOSE, PLAYING
    }

    private Word word;
    private State state;
    private boolean[] guessed;

    public Hangman() {
        super();
        state = State.PLAYING;
    }

    private void printHint() {
        for (int i = 0; i < word.getWordTarget().length(); i++) {
            if (guessed[i]) {
                System.out.print(word.getWordTarget().charAt(i));
            } else {
                System.out.print("_");
            }
        }

        System.out.println();
    }

    private void updateRightGuess(int pos) {
        guessed[pos] = true;
    }

    private void printRightGuess() {
        printGap();
        printHint();
    }

    private void printWrongGuess() {
        printGap();
        printHint();
    }
    
    private void updateWrongGuess() {
        reduceHealth();
    }

    private boolean checkGuess(String guess) {
        int pos = word.getWordTarget().indexOf(guess);

        if (pos == -1) {
            updateWrongGuess();
            printWrongGuess();
            return false;
        }

        updateRightGuess(pos);
        printRightGuess();
        return true;
    }

    public void start() {
        System.out.println("You are playing Hangman");

        word = new Word("Table", "Bàn");
        guessed = new boolean[word.getWordTarget().length()];

        while (state == State.PLAYING) {
            // Print the hint

            System.out.println("Guess a letter: ");

            Scanner sc = new Scanner(System.in);
            String guess = sc.nextLine().substring(0, 1);

            if (checkGuess(guess)) {
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect!");
            }
        }
    }
}
