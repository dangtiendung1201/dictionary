package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hangman extends GameManagement {
	private static final int maxWord = 77229;
	private static final int maxGuess = 8;
	private static final String figure[] = {
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

	private static ArrayList<String> dataFile = new ArrayList<String>();
	static {
		System.out.println("Reading data from Hangman...");

		try {
			Scanner sc = new Scanner(new File(System.getProperty("user.dir") + "/dictionary/data/HangmanData.txt"));

			while (sc.hasNext()) {
				String tmp = sc.nextLine();
				dataFile.add(tmp);
			}

			sc.close();
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}
	private String word;
	private State state;
	private boolean[] guessed;
	private boolean[] guessedCharacter = new boolean[26];

	public Hangman() {
		health = maxGuess;
		point = 0;
		state = State.PLAYING;
	}

	private void printHint() {
		System.out.print("Hint: ");

		for (int i = 0; i < word.length(); i++) {
			if (guessed[i]) {
				System.out.print(word.charAt(i));
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

		if (guessedCharacter[guess - 'a']) {
			System.out.println("You have already guessed this letter!");
			return;
		}

		boolean isCorrect = false;

		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == guess && !guessed[i]) {
				updateRightGuess(i);
				isCorrect = true;
			}
		}

		guessedCharacter[guess - 'a'] = true;

		if (!isCorrect) {
			updateWrongGuess();
			System.out.println("Incorrect guess!");
			if (maxGuess - health >= 0 && maxGuess - health < figure.length)
				System.out.println(figure[maxGuess - health]);
		} else {
			System.out.println("Correct guess!");
		}
	}

	private void getRandomWord() {
		Random rand = new Random();
		int index = rand.nextInt(maxWord);
		word = dataFile.get(index);
	}

	public void start() {
		System.out.println("You are playing Hangman");

		getRandomWord();

		guessed = new boolean[word.length()];
		for (int i = 0; i < word.length(); i++) {
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

			if (point == word.length()) {
				state = State.WIN;
			}
		}

		if (state == State.WIN) {
			System.out.println("You win!");
			System.out.println("The word is: " + word);
		} else {
			System.out.println("You lose!");
			System.out.println("The correct word is: " + word);
		}
	}
}
