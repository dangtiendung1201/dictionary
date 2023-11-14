package trie;

import java.util.ArrayList;
import java.util.Collections;

import word.Word;

import static java.lang.Math.min;

public class Trie {
    static final int ALPHABET_SIZE = 26 + 1;
    private final Node root; // the root of the trie.Trie.
    private int size;

    public Trie() {
        root = new Node();
        size = 0;
    }

    /**
     * Map character int both lower and upper case to integer.
     *
     * @param c the mapped character, assume that c is '-', 'a...z' or 'A...Z'.
     * @return integer value of c, in range [0..26].
     */
    private static int mapCharToInt(char c) {
        if (c == '-') {
            return 0;
        }
        if (c >= 'a' && c <= 'z') {
            return c - 'a' + 1;
        }
        return c - 'A' + 1;
    }

    public static void main(String[] args) {
        Trie T = new Trie();
        T.addWord(new Word("Home", "Ngôi nhà"));
        T.addWord(new Word("Home", "Gia đình"));
        T.addWord(new Word("House", "Căn nhà"));
        System.out.println(T.allWords());
        T.removeWord(new Word("Home", "Ngôi nhà"));
        System.out.println(T.allWords());

    }

    public int size() {
        return size;
    }

    /**
     * Add a word to trie.Trie.
     * @param current current node.
     * @param depth   the depth of current node.
     * @param word    the added word.
     */
    private void addWord(Node current, int depth, Word word) {
        if (depth == word.getWordTarget().length()) {
            current.formedWord.add(word);
            current.updateCandidateWords();
            size++;
            return;
        }
        int id = mapCharToInt(word.getWordTarget().charAt(depth));
        if (current.next[id] == null) {
            current.next[id] = new Node();
        }
        addWord(current.next[id], depth + 1, word);
        current.updateCandidateWords();
    }

    /**
     * Add a word to trie.Trie.
     * @param word added word.
     */
    public void addWord(Word word) {
        addWord(root, 0, word);
    }

    /**
     * Remove a word from trie.Trie.
     * @param current current node.
     * @param depth   depth of current node.
     * @param word    removed word.
     */
    private void removeWord(Node current, int depth, Word word) {
        if (depth == word.getWordTarget().length()) {
            current.formedWord.remove(word);
            current.updateCandidateWords();
            size--;
            return;
        }
        int id = mapCharToInt(word.getWordTarget().charAt(depth));
        if (current.next[id] == null) {
            return;
        }
        removeWord(current.next[id], depth + 1, word);
        current.updateCandidateWords();
        if (current.next[id].meaningless()) current.next[id] = null;
    }

    /**
     * Remove all words that have the wordTarget equals to wordTarget.
     * @param current    current node.
     * @param depth      the depth of the current node.
     * @param wordTarget wordTarget needs to remove.
     */
    private void removeWord(Node current, int depth, String wordTarget) {
        if (depth == wordTarget.length()) {
            size -= current.formedWord.size();
            current.formedWord.removeIf(w -> w.getWordTarget().equals(wordTarget));
            size += current.formedWord.size();
            current.updateCandidateWords();
            return;
        }
        int id = mapCharToInt(wordTarget.charAt(depth));
        if (current.next[id] == null) {
            return;
        }
        removeWord(current.next[id], depth + 1, wordTarget);
        current.updateCandidateWords();
        if (current.next[id].meaningless()) current.next[id] = null;
    }

    public void removeWord(Word word) {
        removeWord(root, 0, word);
    }

    public void removeWord(String wordTarget) {
        removeWord(root, 0, wordTarget);
    }

    /**
     * Get the node that form this prefix.
     * @param current current node.
     * @param depth   the depth of current node.
     * @param prefix  the prefix you want to form.
     * @return The node from this prefix
     */
    private Node getNodeFormPrefix(Node current, int depth, String prefix) {
        if (depth == prefix.length()) {
            return current;
        }
        char c = prefix.charAt(depth);
        int id = mapCharToInt(c);
        if (current.next[id] == null) {
            throw new IllegalArgumentException("This prefix isn't contained in any word!");
        }
        return getNodeFormPrefix(current.next[id], depth + 1, prefix);
    }

    /**
     * Look up for words by its English form(word_target).
     *
     * @param word the English form of the word you want to look up.
     * @return ArrayList of Words that have WORD_TARGET equals to word.
     */
    public ArrayList<Word> lookUpWord(String word) {
        Node tail = getNodeFormPrefix(root, 0, word);
        ArrayList<Word> res = (ArrayList<Word>) tail.formedWord.clone();
        if (res.isEmpty()) {
            throw new IllegalArgumentException("This word doesn't exist in the dictionary!");
        }
        return res;
    }

    /**
     * Search for some words that contain this prefix.
     *
     * @param prefix the prefix you want to search.
     * @return ArrayList of some candidate words.
     */
    public ArrayList<Word> searchWord(String prefix) {
        Node tail = getNodeFormPrefix(root, 0, prefix);
        ArrayList<Word> res = (ArrayList<Word>) tail.candidateWords.clone();
        if (res.isEmpty()) {
            throw new IllegalArgumentException("This prefix doesn't exist in the dictionary");
        }
        return res;
    }

    /**
     * All distinct word target in trie.Trie.
     *
     * @param current current Node.
     * @return ArrayList of word target.
     */
    private ArrayList<String> allTargetWords(Node current) {
        ArrayList<String> res = new ArrayList<>();
        if (!current.formedWord.isEmpty()) {
            res.add(current.formedWord.get(0).getWordTarget());
        }
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (current.next[i] != null) {
                res.addAll(allTargetWords(current.next[i]));
            }
        }
        return res;
    }

    public ArrayList<String> allTargetWords() {
        return allTargetWords(root);
    }

    private ArrayList<Word> allWords(Node current) {
        ArrayList<Word> res = new ArrayList<>(current.formedWord);
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (current.next[i] != null) {
                res.addAll(allWords(current.next[i]));
            }
        }
        return res;
    }

    public ArrayList<Word> allWords() {
        return allWords(root);
    }

    /**
     * The minimum number of operations (insert, edit, remove) on a character, using dynamic programming.
     * of s to become t, used for suggesting word.
     *
     * @param s string s.
     * @param t string t.
     * @return minimum number of operations.
     */
    private int minimumEditDistance(String s, String t) {
        int n = s.length(), m = t.length();
        s = " " + s;
        t = " " + t;
        int[][] dp = new int[n + 1][m + 1];
        dp[0][1] = 1;
        dp[1][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = min(dp[i - 1][j] + 1, dp[i][j - 1] + 1);
                if (s.charAt(i) == t.charAt(j)) {
                    dp[i][j] = min(dp[i][j], dp[i - 1][j - 1]);
                } else {
                    dp[i][j] = min(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp[n][m];
    }

    /**
     * If the user enters the wrong word, suggested words are listed.
     * @return suggested words.
     */
    public ArrayList<String> searchSuggestions(String enteredWord) {
        final int SUGGEST_SIZE = 10;
        ArrayList<String> ret = new ArrayList<>();
        ArrayList<String> allTargetWord = allTargetWords();
        for (int i = 0; i < SUGGEST_SIZE && !allTargetWord.isEmpty(); i++) {
            int min = minimumEditDistance(enteredWord, allTargetWord.get(0));
            int id = 0;
            for (int j = 1; j < allTargetWord.size(); j++) {
                int op = minimumEditDistance(enteredWord, allTargetWord.get(j));
                if (op < min) {
                    op = min;
                    id = j;
                }
            }
            ret.add(allTargetWord.get(id));
            allTargetWord.remove(id);
        }
        return ret;
    }

    private static class Node {
        // by this node, used for searching word.
        static final int MAX_CANDIDATE_SIZE = 10; // Maximum number of the offered word.
        private final Node[] next; // pointer to child of this Node.
        private final ArrayList<Word> formedWord; // All words that this node form because maybe
        // one word can have many meanings.
        private final ArrayList<Word> candidateWords; // Some words that contain the prefix formed by this node.

        Node() {
            next = new Node[ALPHABET_SIZE];
            formedWord = new ArrayList<>();
            candidateWords = new ArrayList<>();
        }

        /**
         * Update candidate word of this node
         * candidateWords contains some distinct word added in the subtree formed by this node
         */
        void updateCandidateWords() {
            candidateWords.clear();
            for (Node child : next) {
                if (child == null) {
                    continue;
                }
                candidateWords.addAll(child.candidateWords);
            }
            Collections.shuffle(candidateWords);

            if (!formedWord.isEmpty()) {
                candidateWords.add(0, formedWord.get(0));
            }

            if (candidateWords.size() > MAX_CANDIDATE_SIZE) {
                candidateWords.subList(MAX_CANDIDATE_SIZE, candidateWords.size()).clear();
            }
        }

        // if a node doesn't have any child forming a word, it's meaningless.
        public boolean meaningless() {
            return candidateWords.isEmpty();
        }
    }
}