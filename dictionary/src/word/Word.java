package word;

import word.exception.InvalidWordException;

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
            throw new InvalidWordException("This word has an invalid character!");
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

    public Word getDisplayingWord() {
        if (examples.contains("\\n")) {
            // this word has been converted to displaying then converted to line again
            return new Word(wordTarget, wordExplain, IPA, wordTypes,
                    examples.replace("2\\n", "\n\n").replace("1\\n", "\n"), relatedWords);
        }

        String[] allExample = examples.split(" \\| ");
        String examples = "";
        boolean isPreviousExampleEnglish = false;
        for (String example : allExample) {
            // Check if example is English or Vietnamese
            boolean isEnglish = true;
            if (example.isEmpty())
                continue;
            /*
             * for (int i = 0; i + wordTarget.length() - 1 < example.length(); i ++) {
             * if (i > 0 && invalidCharacterBesideWordTarget(example.charAt(i - 1))) {
             * continue;
             * }
             * if (i + wordTarget.length() < example.length()
             * && invalidCharacterBesideWordTarget(example.charAt(i + wordTarget.length())))
             * {
             * continue;
             * }
             * if (example.startsWith(wordTarget, i) ||
             * example.startsWith(wordTarget.toUpperCase(), i)) {
             * isEnglish = true;
             * break;
             * }
             * }
             */
            for (int i = 0; i < example.length(); i++) {
                char c = example.charAt(i);
                isEnglish &= (c < 'À');
            }

            if (examples.isEmpty()) {
                examples += example;
            } else if (isEnglish) {
                examples += "\n\n" + example;
            } else {
                // Vietnamese
                if (isPreviousExampleEnglish) {
                    examples += "\n";
                }
                else examples += "; ";
                examples += example;
            }
            isPreviousExampleEnglish = isEnglish;
        }
        return new Word(wordTarget, wordExplain, IPA,
                wordTypes, examples, relatedWords);
    }

    /**
     * Convert displaying word to one line to export;
     *
     * @return word in one line
     */
    public Word toLine() {
        String wordTarget = this.wordTarget.replace("\n\n", "2\\n")
                .replace("\n", "1\\n").replace("\t", "");
        String examples = this.examples.replace("\n\n", "2\\n")
                .replace("\n", "1\\n").replace("\t", "");
        String wordExplain = this.wordExplain.replace("\n\n", "2\\n")
                .replace("\n", "1\\n").replace("\t", "");
        String IPA = this.IPA.replace("\n\n", "2\\n")
                .replace("\n", "1\\n").replace("\t", "");
        String wordTypes = this.wordTypes.replace("\n\n", "2\\n")
                .replace("\n", "1\\n").replace("\t", "");
        String relatedWords = this.relatedWords.replace("\n\n", "2\\n")
                .replace("\n", "1\\n").replace("\t", "");
        return new Word(wordTarget, wordExplain, IPA, wordTypes, examples, relatedWords);
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
