package game;

import java.nio.file.Files;

import word.Word;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MultipleChoice extends GameManagement {
    private static int maxQuestion = 33482;

    private ArrayList<Question> dataFile = new ArrayList<Question>();
    private State state;
    private Question question;

    public MultipleChoice() {
        point = 0;
        state = State.PLAYING;
    }

    private void getDataFromFile() {
        System.out.println("Reading data from file...");

        // try {
        // return
        // Files.readAllLines(Paths.get("dictionary/data/MultipleChoiceData.txt"));
        // } catch (IOException e) {
        // System.out.println("Error: " + e);
        // return null;
        // }
        try {
            Scanner sc = new Scanner(new File("dictionary/data/MultipleChoiceData.txt"));
            while (sc.hasNext()) {
                String tmp[] = sc.nextLine().split("\t");
                dataFile.add(new Question(tmp[0], tmp[1]));
            }
            sc.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    private void generateQuestion() {
        Random rand = new Random();
        int index = rand.nextInt(maxQuestion);
        question = dataFile.get(index);
    }

    public void start() {
        System.out.println("You are playing Multiple Choice Game");
        getDataFromFile();
        
        while (state == State.PLAYING)
        {
            generateQuestion();
        }
    }
}
