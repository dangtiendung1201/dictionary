package game;

import question.Question;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MultipleChoice extends GameManagement {
    private static int maxQuestion = 33482;

    private static ArrayList<Question> dataFile = new ArrayList<Question>();
    static {
        System.out.println("Reading data from Multiple Choice...");

        try {
            Scanner sc = new Scanner(
                    new File(System.getProperty("user.dir") + "/dictionary/resources/data/MultipleChoiceData.txt"));

            while (sc.hasNext()) {
                String tmp[] = sc.nextLine().split("\t");
                dataFile.add(new Question(tmp[0], tmp[1]));
            }

            sc.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
    private Question question;
    private String[] answer = new String[4];
    private char correctAnswer;

    public MultipleChoice() {
        point = 0;
        health = 3;
    }

    public void generateQuestion() {
        Random rand = new Random();
        int index = rand.nextInt(maxQuestion);
        question = dataFile.get(index);
    }

    public void generateAnswer() {
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

        for (int i = 0; i < 4; i++) {
            if (answer[i].equals(question.getWord())) {
                correctAnswer = (char) (i + 'A');
                break;
            }
        }
    }

    public String getQuestion() {
        String questionScript = question.getExample();
        questionScript = questionScript.replaceAll("(?i)" + question.getWord(), "______");

        return questionScript;
    }

    public String getSentence() {
        return question.getExample();
    }

    public String getAnswer(int index) {
        if (index == 0)
            return "A. " + answer[0];
        else if (index == 1)
            return "B. " + answer[1];
        else if (index == 2)
            return "C. " + answer[2];
        return "D. " + answer[3];
    }

    public String getAnswer(char index) {
        if (index == 'A')
            return answer[0];
        else if (index == 'B')
            return answer[1];
        else if (index == 'C')
            return answer[2];
        return answer[3];
    }

    public char getCorrectAnswer() {
        return correctAnswer;
    }
}
