package game;

import java.util.Scanner;

import word.Word;

public class Hangman extends GameManagement {
    static private final int maxGuess = 8;
    static private final String figure[] = {
            "   -------------    \n" +
                    "   |                \n" +
                    "   |                \n" +
                    "   |                \n" +
                    "   |                \n" +
                    "   |                  \n" +
                    " -----                \n",

            "   -------------    \n" +
                    "   |           |    \n" +
                    "   |                \n" +
                    "   |                \n" +
                    "   |                \n" +
                    "   |     \n" +
                    " -----   \n",

            "   -------------    \n" +
                    "   |           |    \n" +
                    "   |           0    \n" +
                    "   |                \n" +
                    "   |                \n" +
                    "   |     \n" +
                    " -----   \n",

            "   -------------    \n" +
                    "   |           |    \n" +
                    "   |           0    \n" +
                    "   |           |    \n" +
                    "   |                \n" +
                    "   |     \n" +
                    " -----   \n",

            "   -------------    \n" +
                    "   |           |    \n" +
                    "   |           0    \n" +
                    "   |          /|    \n" +
                    "   |                \n" +
                    "   |     \n" +
                    " -----   \n",

            "   -------------    \n" +
                    "   |           |    \n" +
                    "   |           0    \n" +
                    "   |          /|\\  \n" +
                    "   |                \n" +
                    "   |     \n" +
                    " -----   \n",

            "   -------------    \n" +
                    "   |           |    \n" +
                    "   |           0    \n" +
                    "   |          /|\\  \n" +
                    "   |          /     \n" +
                    "   |     \n" +
                    " -----   \n",

            "   -------------    \n" +
                    "   |           |    \n" +
                    "   |           0    \n" +
                    "   |          /|\\  \n" +
                    "   |          / \\  \n" +
                    "   |     \n" +
                    " -----   \n"
    };

    enum State {
        WIN, LOSE, PLAYING
    }

    private Word word;
    private State state;
    private boolean[] guessed;

    public Hangman() {
        health = maxGuess;
        point = 0;
        state = State.PLAYING;
    }

    private void printHint() {
        System.out.print("Hint: ");

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
        point++;
    }

    private void updateWrongGuess() {
        health--;
    }

    public void checkGuess(char guess) {
        guess = Character.toLowerCase(guess);

        boolean isCorrect = false;

        for (int i = 0; i < word.getWordTarget().length(); i++) {
            if (word.getWordTarget().charAt(i) == guess) {
                if (!guessed[i]) {
                    updateRightGuess(i);

                    isCorrect = true;
                } else {
                    System.out.println("You have already guessed this letter!");
                    return;
                }
            }
        }

        if (!isCorrect) {
            updateWrongGuess();
            System.out.println("Incorrect guess!");
            if (maxGuess - health >= 0 && maxGuess - health < figure.length)
                System.out.println(figure[maxGuess - health]);
        } else {
            System.out.println("Correct guess!");
        }
    }

    public void start() {
        System.out.println("You are playing Hangman");

        word = new Word("mississippi", "hihi");
        guessed = new boolean[word.getWordTarget().length()];
        for (int i = 0; i < word.getWordTarget().length(); i++) {
            guessed[i] = false;
        }

        while (state == State.PLAYING) {
            printHint();
            System.out.println("Guess a letter: ");

            Scanner sc = new Scanner(System.in);
            char guess = sc.next().charAt(0);

            checkGuess(guess);

            if (health == 0) {
                state = State.LOSE;
            }

            if (point == word.getWordTarget().length()) {
                state = State.WIN;
            }
        }

        if (state == State.WIN) {
            System.out.println("You win!");
            System.out.println("The word is: " + word.getWordTarget());
        } else {
            System.out.println("You lose!");
        }
    }
}
