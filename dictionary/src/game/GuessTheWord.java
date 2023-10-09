package game;

import java.util.Scanner;
import word.Word;

public class GuessTheWord extends GameManagement {
	static private final int maxGuess = 5;

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

	public void start() {
		System.out.println("You are playing Guess The Word");

		word = new Word("mississippi", "hihi");
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
		}
	}
}
