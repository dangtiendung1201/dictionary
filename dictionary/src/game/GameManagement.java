package game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameManagement {
    private int point;
    private int health;
    private int type;
    private int curGame = 0; // 0 is None, 1 is Hangman, 2 is Guess the Word, 3 is Multiple Choice

    public GameManagement() {
        this.point = 0;
        this.health = 5;
        this.type = 0;
        this.curGame = 1;
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
        return type;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPoint() {
        return point;
    }

    public void reduceHealth() {
        health--;
    }

    protected void printGap() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
        System.out.println("----------------------------------------");
    }

    public void printMenu() {
        System.out.println("Welcome to the game!");
        System.out.println("\n\n\n----------------------------------------\n\n\n");

        while (curGame != 0) {
            try {
                System.out.println("[0] Back to menu");
                System.out.println("[1] Hangman");
                System.out.println("[2] Guess The Word");
                System.out.println("[3] Multiple Choice");
                System.out.println("Your action: ");
                Scanner sc = new Scanner(System.in);
                curGame = sc.nextInt() - 1;
                if (curGame < 0 || curGame > 3)
                    throw new InputMismatchException();
                printGap();

                switch (curGame) {
                    case 0:
                        break;
                    case 1:
                        Hangman hangman = new Hangman();
                        hangman.start();
                        break;
                }
                printGap();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input!");
            }
        }
    }
}