public class Word {
    private String wordTarget;
    private String wordExplain;

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

    private static boolean validCharacter(char c) {
        if (c == '-') {
            return true;
        }
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
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
        return wordTarget + "  :       " + wordExplain;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Word another) {
            return another.wordExplain.equals(this.wordExplain) && another.wordTarget.equals(this.wordTarget);
        }
        return false;
    }

    public static void main(String[] args) {
        Word w = new Word("Table", "Bàn");
        System.out.println(w);
    }
}
