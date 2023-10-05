package game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameManagement {
    protected int point;
    protected int health;
    private int curGame; // 0 is None, 1 is Hangman, 2 is Guess the Word, 3 is Multiple Choice

    public GameManagement() {
        curGame = 1;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPoint() {
        return point;
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
            System.out.println("[0] Back to menu");
            System.out.println("[1] Hangman");
            System.out.println("[2] Guess The Word");
            System.out.println("[3] Multiple Choice");
            System.out.println("Your action: ");
            Scanner sc = new Scanner(System.in);
            curGame = sc.nextInt();

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
        }
    }
}