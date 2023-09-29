import java.util.ArrayList;
import java.util.Collections;

public class Trie {
    static final int ALPHABET_SIZE = 26 + 1;
    private final Node root; // the root of the Trie.

    Trie() {
        root = new Node();
    }

    /** Map character int both lower and upper case to integer.
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
     * Add a word to Trie.
     *
     * @param current current node.
     * @param depth   the depth of current node.
     * @param word    the added word.
     */
    private void addWord(Node current, int depth, Word word) {
        if (depth == word.getWORD_TARGET().length()) {
            current.formedWord.add(word);
            current.updateCandidateWords();
            return;
        }
        int id = mapCharToInt(word.getWORD_TARGET().charAt(depth));
        if (current.next[id] == null) current.next[id] = new Node();
        addWord(current.next[id], depth + 1, word);
        current.updateCandidateWords();
    }

    /**
     * Add a word to Trie.
     *
     * @param word added word.
     */
    public void addWord(Word word) {
        addWord(root, 0, word);
    }

    /**
     * Get the node that form this prefix.
     *
     * @param current current node.
     * @param depth   the depth of current node.
     * @param prefix  the prefix you wante to form.
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

    private static class Node {
        // by this node, used for searching word.
        static final int MAX_CANDIDATE_SIZE = 10; // Maximum number of the offered word.
        private final Node[] next; // pointer to child of this Node.
        private final ArrayList<Word> formedWord; // All words that this node form because maybe
        // one word can have many meanings.
        private final ArrayList<Word> candidateWords; // Some words that contain the prefix formed

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

    }
}