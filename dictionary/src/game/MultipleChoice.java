package game;

import question.Question;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class MultipleChoice extends GameManagement {
    private static int maxQuestion = 33482;

    private static ArrayList<Question> dataFile = new ArrayList<Question>();
    static {
        System.out.println("Reading data from Multiple Choice...");

        try {
            Scanner sc = new Scanner(
                    new File(System.getProperty("user.dir") + "/dictionary/data/MultipleChoiceData.txt"));

            while (sc.hasNext()) {
                String tmp[] = sc.nextLine().split("\t");
                dataFile.add(new Question(tmp[0], tmp[1]));
            }

            sc.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
    private State state;
    private Question question;
    private String[] answer = new String[4];
    private char correctAnswer;

    public MultipleChoice() {
        point = 0;
        health = 3;
        state = State.PLAYING;
    }

    private void generateQuestion() {
        Random rand = new Random();
        int index = rand.nextInt(maxQuestion);
        question = dataFile.get(index);
    }

    private void generateAnswer() {
        answer[0] = question.getWord();

        for (int i = 1; i < 4; i++) {
            Random rand = new Random();
            int index = rand.nextInt(maxQuestion);
            answer[i] = dataFile.get(index).getWord();
        }

        for (int i = 0; i < 4; i++) {
            Random rand = new Random();
            int index = rand.nextInt(4);

            String tmp = answer[i];
            answer[i] = answer[index];
            answer[index] = tmp;
        }
    }

    private void getCorrectAnswer() {
        for (int i = 0; i < 4; i++) {
            if (answer[i].equals(question.getWord())) {
                correctAnswer = (char) (i + 'A');
                break;
            }
        }
    }

    private void printQuestion() {
        String questionScript = question.getExample();
        questionScript = questionScript.replaceAll("(?i)" + question.getWord(), "______");
        System.out.println("Question: " + questionScript);
    }

    private void printAnswer() {
        System.out.println("A. " + answer[0]);
        System.out.println("B. " + answer[1]);
        System.out.println("C. " + answer[2]);
        System.out.println("D. " + answer[3]);
    }

    private void checkAnswer(char guess) {
        if (guess == correctAnswer) {
            System.out.println("Correct answer!");
            point++;
        } else {
            System.out.println("Incorrect answer!");
            System.out.println("Correct answer is " + correctAnswer);
            health--;
        }
    }

    private void printInformation() {
        System.out.println("Your point: " + point);
        System.out.println("Your health: " + health);
    }

    public void start() {
        System.out.println("You are playing Multiple Choice Game");

        while (state == State.PLAYING) {
            generateQuestion();
            generateAnswer();
            getCorrectAnswer();

            printQuestion();
            printAnswer();

            System.out.print("Your answer: ");
            Scanner sc = new Scanner(System.in);
            char guess = Character.toUpperCase(sc.next().charAt(0));

            checkAnswer(guess);

            printInformation();

            if (health == 0) {
                state = State.LOSE;
                break;
            }

            if (point == 10) {
                state = State.WIN;
                break;
            }
        }

        if (state == State.LOSE) {
            System.out.println("You lose!");
        } else {
            System.out.println("You win!");
        }
    }
}
