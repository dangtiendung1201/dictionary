package dictionary.game;


public class Hangman extends Game {
    enum State {
        WIN, LOSE, PLAYING
    }

    private Word word;
    private State state;

    public Hangman() {
        super();
        state = State.PLAYING;
    }

    private void getInput()
    {
        System.out.println("Guess a letter: ");
        Scanner scanner = new Scanner(System.in);
        String guess = scanner.nextLine().substring(0, 1);
        System.out.println("You guessed: " + guess);

        if (checkGuess(guess)) {
            System.out.println("Correct!");
        } else {
            System.out.println("Incorrect!");
            setHealth(getHealth() - 1);
        }
    }

    private void play() {
        while (state == State.PLAYING) {
            System.out.println("Guess a letter: ");
            // a scanner to get input from user
            Scanner scanner = new Scanner(System.in);
            String guess = scanner.nextLine();

        }
    }

    // Get random word from dict
    public void start() {
        // ...
        play();
    }

    public void printInformation() {
        System.out.println("Score: " + getPoint());
        System.out.println("Health: " + getHealth());
    }
}

