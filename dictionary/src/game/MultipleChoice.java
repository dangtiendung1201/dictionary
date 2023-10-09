package game;

import java.nio.file.Files;

import word.Word;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class MultipleChoice extends GameManagement {
    private List<String> dataFile;
    private State state;
    private Question question;

    public MultipleChoice() {
        point = 0;
        state = State.PLAYING;
    }

    private List<String> getDataFromFile() {
        System.out.println("Reading data from file...");
        
        try {
            return Files.readAllLines(Paths.get("dictionary/data/MultipleChoiceData.txt"));
        } catch (IOException e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    public void start() {
        getDataFromFile();
    }
}
