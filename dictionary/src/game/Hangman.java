package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hangman extends GameManagement {
	private static final int maxWord = 77229;
	private static final int maxGuess = 6;

	private static ArrayList<String> dataFile = new ArrayList<String>();
	static {
		System.out.println("Reading data from Hangman...");

		try {
			Scanner sc = new Scanner(
					new File(System.getProperty("user.dir") + "/dictionary/resourses/data/HangmanData.txt"));

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
	private boolean[] guessed;
	private boolean[] guessedCharacter = new boolean[26];

	public Hangman() {
		health = maxGuess;
		state = State.PLAYING;
	}

	public void reset() {
		health = maxGuess;
		state = State.PLAYING;
		guessed = new boolean[word.length()];
		guessedCharacter = new boolean[26];

		for (int i = 0; i < 26; i++) {
			guessedCharacter[i] = false;
		}
	}

	public void getRandomWord() {
		Random rand = new Random();
		int index = rand.nextInt(maxWord);
		word = dataFile.get(index);
	}

	public String getHint() {
		return "This word has" + " " + word.length() + " " + "characters";
	}

	public String getWord() {
		String res = "Please guess this word: ";

		for (int i = 0; i < word.length(); i++) {
			if (guessed[i]) {
				res += word.charAt(i);
			} else {
				res += "_";
			}
		}

		return res;
	}

	public String getCorretWord() {
		return word;
	}

	public boolean checkGuessedCharacter(char c) {
		return guessedCharacter[c - 'a'];
	}

	public int checkGuess(char c) {
		int res = 0;

		guessedCharacter[c - 'a'] = true;

		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == c) {
				guessed[i] = true;
				res++;
			}
		}

		return res;
	}

	public void updateState() {
		if (health == 0) {
			state = State.LOSE;
		}

		boolean check = true;
		for (int i = 0; i < word.length(); i++) {
			if (!guessed[i]) {
				check = false;
				break;
			}
		}

		if (check) {
			state = State.WIN;
		}
	}
}
