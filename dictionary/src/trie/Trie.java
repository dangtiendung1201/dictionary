package trie;

import trie.exception.AddWordException;
import trie.exception.RemoveWordException;
import trie.exception.SearchWordException;
import word.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

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

    /**
     * The minimum number of operations (insert, edit, remove) on a character, using dynamic programming.
     * of s to become t, used for suggesting word.
     *
     * @param s string s.
     * @param t string t.
     * @return minimum number of operations.
     */
    private static int minimumEditDistance(String s, String t) {
        int n = s.length(), m = t.length();
        s = " " + s;
        t = " " + t;
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        dp[0][0] = 0;
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (i > 0) {
                    dp[i][j] = min(dp[i][j], dp[i - 1][j] + 1);
                }
                if (j > 0) {
                    dp[i][j] = min(dp[i][j], dp[i][j - 1] + 1);
                }
                if (i > 0 && j > 0) {
                    if (s.charAt(i) == t.charAt(j)) {
                        dp[i][j] = min(dp[i][j], dp[i - 1][j - 1]);
                    } else {
                        dp[i][j] = min(dp[i][j], dp[i - 1][j - 1] + 1);
                    }
                }
            }
        }
        return dp[n][m];
    }

    public int size() {
        return size;
    }

    /**
     * Add a word to trie.Trie.
     *
     * @param current current node.
     * @param depth   the depth of current node.
     * @param word    the added word.
     */
    private void addWord(Node current, int depth, Word word) throws AddWordException {
        if (depth == word.getWordTarget().length()) {
            if (current.formedWord != null) {
                throw new AddWordException("This word has already been added!");
            }
            current.formedWord = word;
            size++;
            return;
        }
        int id = mapCharToInt(word.getWordTarget().charAt(depth));
        if (!current.next.containsKey(id)) {
            current.next.put(id, new Node());
        }
        addWord(current.next.get(id), depth + 1, word);
    }

    /**
     * Add a word to trie.Trie.
     *
     * @param word added word.
     */
    public void addWord(Word word) {
        addWord(root, 0, word);
    }

    /**
     * Remove a word from trie.Trie.
     *
     * @param current current node.
     * @param depth   depth of current node.
     * @param word    removed word.
     */
    private void removeWord(Node current, int depth, Word word) {
        if (depth == word.getWordTarget().length()) {
            if (current.formedWord == null) {
                throw new RemoveWordException("Từ không tồn tại!");
            }
            current.formedWord = null;
            size--;
            return;
        }
        int id = mapCharToInt(word.getWordTarget().charAt(depth));
        if (!current.next.containsKey(id)) {
            throw new RemoveWordException("Từ không tồn tại!");
        }
        removeWord(current.next.get(id), depth + 1, word);
    }

    /**
     * Remove all words that have the wordTarget equals to wordTarget.
     *
     * @param current    current node.
     * @param depth      the depth of the current node.
     * @param wordTarget wordTarget needs to remove.
     */
    private void removeWord(Node current, int depth, String wordTarget) {
        if (depth == wordTarget.length()) {
            if (current.formedWord == null) {
                throw new RemoveWordException("Từ không tồn tại!");
            }
            current.formedWord = null;
            size--;
            return;
        }
        int id = mapCharToInt(wordTarget.charAt(depth));
        if (current.next.get(id) == null) {
            throw new RemoveWordException("Từ không tồn tại!");
        }
        removeWord(current.next.get(id), depth + 1, wordTarget);
    }

    public void removeWord(Word word) {
        removeWord(root, 0, word);
    }

    public void removeWord(String wordTarget) {
        removeWord(root, 0, wordTarget);
    }

    /**
     * Get the node that form this prefix.
     *
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
        if (current.next.get(id) == null) {
            throw new SearchWordException("This prefix isn't contained in any word!");
        }
        return getNodeFormPrefix(current.next.get(id), depth + 1, prefix);
    }

    /**
     * Look up for words by its English form(word_target).
     *
     * @param word the English form of the word you want to look up.
     * @return ArrayList of Words that have WORD_TARGET equals to word.
     */
    public Word lookUpWord(String word) {
        Node tail = getNodeFormPrefix(root, 0, word);
        return tail.formedWord;
    }

    /**
     * Search for some words that contain this prefix.
     *
     * @param prefix the prefix you want to search.
     * @return ArrayList of some candidate words.
     */
    public ArrayList<Word> searchWord(String prefix) {
        Node tail = getNodeFormPrefix(root, 0, prefix);
        ArrayList<Word> res = allWords(tail);
        if (res.isEmpty()) {
            throw new SearchWordException("This prefix doesn't exist in the dictionary");
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
        if (current.formedWord != null) {
            res.add(current.formedWord.getWordTarget());
        }
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (current.next.get(i) != null) {
                res.addAll(allTargetWords(current.next.get(i)));
            }
        }
        return res;
    }

    public ArrayList<String> allTargetWords() {
        return allTargetWords(root);
    }

    private ArrayList<Word> allWords(Node current) {
        ArrayList<Word> res = new ArrayList<>();
        if(current.formedWord != null) {
            res.add(current.formedWord);
        }
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if (current.next.get(i) != null) {
                res.addAll(allWords(current.next.get(i)));
            }
        }
        return res;
    }

    public ArrayList<Word> allWords() {
        return allWords(root);
    }

    /**
     * If the user enters the wrong word, suggested words are listed.
     *
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
                    min = op;
                    id = j;
                }
            }
            ret.add(allTargetWord.get(id));
            allTargetWord.remove(id);
        }
        return ret;
    }

    public ArrayList<String> searchSuggestions(String enteredWord, int maxSuggestSize) {
        ArrayList<String> ret = new ArrayList<>();
        ArrayList<String> allTargetWord = allTargetWords();
        for (int i = 0; i < maxSuggestSize && !allTargetWord.isEmpty(); i++) {
            int min = minimumEditDistance(enteredWord, allTargetWord.get(0));
            int id = 0;
            for (int j = 1; j < allTargetWord.size(); j++) {
                int op = minimumEditDistance(enteredWord, allTargetWord.get(j));
                if (op < min) {
                    min = op;
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
        private final TreeMap<Integer, Node> next; // pointer to child of this Node.
        private Word formedWord; // All words that this node form because maybe
        // one word can have many meanings.

        public Node() {
            next = new TreeMap<>();
            formedWord = null;
        }

    }
}