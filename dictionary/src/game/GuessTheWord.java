package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import word.Word;

public class GuessTheWord extends GameManagement {
	private static final int maxWord = 76260;
	private static final int maxGuess = 10;

	private static ArrayList<Word> dataFile = new ArrayList<Word>();
	static {
		System.out.println("Reading data from Guess The Word...");

		try {
			Scanner sc = new Scanner(
					new File(System.getProperty("user.dir") + "/dictionary/resourses/data/GuessTheWordData.txt"));

			while (sc.hasNext()) {
				String tmp[] = sc.nextLine().split("\t");
				dataFile.add(new Word(tmp[0], tmp[1], tmp[2], tmp[3]));
			}

			sc.close();
		} catch (IOException e) {
			System.out.println("Error: " + e);
		}
	}
	private Word word;
	public State state;

	public GuessTheWord() {
		health = maxGuess;
		point = 0;
		state = State.PLAYING;
	}

	public void reset() {
		health = maxGuess;
		point = 0;
		state = State.PLAYING;
	}

	public String getHint() {
		String res = "";
		res += "This word has " + word.getWordTarget().length() + " characters." + "\n";
		res += "Pronunciation: " + word.getIPA() + "\n";
		res += "Type: " + word.getWordTypes() + "\n";
		res += "Meaning: " + word.getWordExplain();

		return res;
	}

	public boolean checkGuess(String guess) {
		return word.getWordTarget().equals(guess);
	}

	public String getCorrectWord() {
		return word.getWordTarget();
	}

	public void getRandomWord() {
		Random rand = new Random();
		int index = rand.nextInt(maxWord);
		word = dataFile.get(index);
	}
}
