public class Word {
     private final String WORD_TARGET;
     private final String WORD_EXPLAIN;

     private static boolean validCharacter(char c) {
         if (c == '-') {
             return true;
         }
         return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
     }
     Word(String word_target, String word_explain) {
        for(int i = 0; i < word_target.length(); i ++) {
            if (!validCharacter(word_target.charAt(i))) {
                throw new IllegalArgumentException("This word has an invalid character!");
            }
        }
        WORD_TARGET = word_target;
        WORD_EXPLAIN = word_explain;
    }

    public String getWORD_TARGET() {
        return WORD_TARGET;
    }

    public String getWORD_EXPLAIN() {
        return WORD_EXPLAIN;
    }

    public String toString() {
         return WORD_TARGET + " | " + WORD_EXPLAIN;
    }
}
