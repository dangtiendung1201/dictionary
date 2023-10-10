package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import question.Question;
import word.Word;

public class GuessTheWord extends GameManagement {
	static private final int maxWord = 82084;
	static private final int maxGuess = 5;

	private ArrayList<Word> dataFile = new ArrayList<Word>();
	private Word word;
	private State state;

	public GuessTheWord() {
		health = maxGuess;
		point = 0;
		state = State.PLAYING;
	}

	private void printHint() {
		System.out.println("This word has " + word.getWordTarget().length() + " characters.");
		System.out.println("Meaning: " + word.getWordExplain());
	}

	private void checkGuess(String guess) {
		guess = guess.toLowerCase();

		if (guess.equals(word.getWordTarget())) {
			state = State.WIN;
			return;
		}

		health--;
		System.out.println("Wrong guess! You have " + health + " guesses left.");
	}

	private void getDataFromFile() {
		System.out.println("Reading data from file...");

        try {
            Scanner sc = new Scanner(new File(System.getProperty("user.dir") + "/dictionary/data/GuessTheWordData.txt"));
            
            while (sc.hasNext()) {
                String tmp[] = sc.nextLine().split("\t");
                dataFile.add(new Word(tmp[0], tmp[1]));
            }
            
            sc.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
	}

	private void getRandomWord() {
		Random rand = new Random();
        int index = rand.nextInt(maxWord);
        word = dataFile.get(index);
	}

	public void start() {
		System.out.println("You are playing Guess The Word");

		getDataFromFile();
		getRandomWord();

		printHint();

		while (state == State.PLAYING) {
			System.out.println("Guess a word: ");

			Scanner sc = new Scanner(System.in);
			String guess = sc.nextLine();

			checkGuess(guess);

			if (health == 0) {
				state = State.LOSE;
			}
		}

		if (state == State.WIN) {
			System.out.println("You win!");
			System.out.println("The word is: " + word.getWordTarget());
		} else {
			System.out.println("You lose!");
			System.out.println("The correct word is: " + word.getWordTarget());
		}
	}
}
