package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hangman extends GameManagement {
	static private final int maxWord = 77229;
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

	ArrayList<String> dataFile = new ArrayList<String>();
	private String word;
	private State state;
	private boolean[] guessed;

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

		boolean isCorrect = false;

		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == guess) {
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

	private void getDataFromFile() {
		System.out.println("Reading data from file...");

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

	private void getRandomWord() {
		Random rand = new Random();
		int index = rand.nextInt(maxWord);
		word = dataFile.get(index);
	}

	public void start() {
		System.out.println("You are playing Hangman");

		getDataFromFile();
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
