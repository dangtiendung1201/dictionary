package game;

public class Question {
    private String word;
    private String example;

    public Question(String word, String example) {
        this.word = word;
        this.example = example;
    }

    public String getWord() {
        return word;
    }

    public String getExample() {
        return example;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setExample(String example) {
        this.example = example;
    }
}
