package game;

public class Question {
    private String word;
    private String question;

    public Question(String word, String question) {
        this.word = word;
        this.question = question;
    }

    public String getWord() {
        return word;
    }

    public String getQuestion() {
        return question;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
