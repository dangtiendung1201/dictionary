package word;

import java.util.ArrayList;

public class Word {
    private String wordTarget;
    private String wordExplain;

    private String IPA;
    private String wordTypes;
    private String relatedWords;
    private String examples;

    public Word(String word_target, String word_explain) {
        for (int i = 0; i < word_target.length(); i++) {
            if (!validCharacter(word_target.charAt(i))) {
                throw new IllegalArgumentException("This word has an invalid character!");
            }
            word_target = word_target.toLowerCase();
        }
        wordTarget = word_target;
        wordExplain = word_explain;
    }

    public Word(String wordTarget, String wordExplain, String IPA,
                String wordTypes, String examples, String relatedWords) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
        this.IPA = IPA;
        this.wordTypes = wordTypes;
        this.relatedWords = relatedWords;
        this.examples = examples;
    }

    private static boolean validCharacter(char c) {
        if (c == '-') {
            return true;
        }
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static void main(String[] args) {
        Word w = new Word("Table", "Bàn");
        System.out.println(w);
    }

    public String getWordTarget() {
        return wordTarget;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }

    public String toString() {
        return wordTarget + '\t' + IPA + '\t' + wordTypes
                + '\t' + wordExplain + '\t' + examples + '\t'
                + relatedWords;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Word) {
            Word another = (Word) obj;
            return another.wordExplain.equals(this.wordExplain) && another.wordTarget.equals(this.wordTarget);
        }
        return false;
    }

    public String getExamples() {
        return examples;
    }

    public void setExamples(String examples) {
        this.examples = examples;
    }

    public String getRelatedWords() {
        return relatedWords;
    }

    public void setRelatedWords(String relatedWords) {
        this.relatedWords = relatedWords;
    }

    public String getWordTypes() {
        return wordTypes;
    }

    public void setWordTypes(String wordTypes) {
        this.wordTypes = wordTypes;
    }

    public String getIPA() {
        return IPA;
    }

    public void setIPA(String IPA) {
        this.IPA = IPA;
    }
}
