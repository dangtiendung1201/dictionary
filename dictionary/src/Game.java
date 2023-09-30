package dictionary;

import dictionary.game.*;

public class Game {
    private int point;
    private int health;
    private int type;

    public Game() {
        this.point = 0;
        this.health = 5;
        this.type = 0;
    }

    public void setType(int type) {
        this.type = type;

        if (type == 1) {
            System.out.println("You are playing Hangman");
        } else if (type == 2) {
            System.out.println("You are playing Guess the Word");
        } else if (type == 3) {
            System.out.println("You are playing Multiple Choice");
        } else {
            System.out.println("You have not selected a game yet");
        }
    }

    public int getType() {
        return this.type;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPoint() {
        return this.point;
    }

    public void startGame() {
        if (this.type == 1) {
            Hangman hangman = new Hangman();
            hangman.start();
        }

        // if (this.type == 1) {
        // Hangman hangman = new Hangman();
        // hangman.start();
        // } else if (this.type == 2) {
        // GuessTheWord guessTheWord = new GuessTheWord();
        // guessTheWord.start();
        // } else if (this.type == 3) {
        // MultipleChoice multipleChoice = new MultipleChoice();
        // multipleChoice.start();
        // } else {
        // System.out.println("You have not selected a game yet");
        // }
    }
}
