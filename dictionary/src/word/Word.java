package word;

public class Word {
    private String wordTarget = "N/A";
    private String wordExplain = "N/A";

    private String IPA = "N/A";
    private String wordTypes = "N/A";
    private String relatedWords = "N/A";
    private String examples = "N/A";

    public Word(String wordTarget, String wordExplain) {
        if (invalidWordTarget(wordTarget)) {
            throw new IllegalArgumentException("This word has an invalid character!");
        }
        wordTarget = wordTarget.toLowerCase();
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    public Word(String wordTarget, String IPA,
                String wordTypes, String wordExplain) {
        if (invalidWordTarget(wordTarget)) {
            throw new IllegalArgumentException("This word has an invalid character!");
        }
        wordTarget = wordTarget.toLowerCase();

        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
        this.IPA = IPA;
        this.wordTypes = wordTypes;
    }

    public Word(String wordTarget, String wordExplain, String IPA,
            String wordTypes, String examples, String relatedWords) {
        if (invalidWordTarget(wordTarget)) {
            throw new IllegalArgumentException("This word has an invalid character!");
        }
        wordTarget = wordTarget.toLowerCase();

        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
        this.IPA = IPA;
        this.wordTypes = wordTypes;
        this.relatedWords = relatedWords;
        this.examples = examples;
    }

    private static boolean invalidCharacter(char c) {
        if (c == '-') {
            return false;
        }
        return (c < 'a' || c > 'z') && (c < 'A' || c > 'Z');
    }

    private static boolean invalidWordTarget(String wordTarget) {
        for (int i = 0; i < wordTarget.length(); i++) {
            if (invalidCharacter(wordTarget.charAt(i))) {
                return true;
            }
        }
        return false;
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
