import java.util.ArrayList;

/**
 * Spell Check
 * A puzzle written by Zach Blick
 * for Adventures in Algorithms
 * At Menlo School in Atherton, CA
 *
 * Completed by: Landon Moceri
 * */

public class SpellCheck {


    /**
     * checkWords finds all words in text that are not present in dictionary
     *
     * @param text The list of all words in the text.
     * @param dictionary The list of all accepted words.
     * @return String[] of all mispelled words in the order they appear in text. No duplicates.
     */

    private class Node {
        // Each node has space for 26 children, one for each letter of the alphabet
        Node[] children = new Node[26];
        // If a node is a word ending, set a boolean to communicate it
        boolean isWordEnding = false;
    }

    private class WordTree {
        Node root = new Node();

        public char[] formatWord(String word) {
            // Format the word to be all lowercase
            word = word.toLowerCase();
            return word.toCharArray();
        }

        // Add a word to the tree
        public void addWord(String word) {
            Node current = root;

            for (char letter : formatWord(word)) {
                // Turn the letter into an index from 0-25
                int index = letter - 'a';
                if (index < 0 || index > 25) {
                    // If letter is not a lowercase letter, skip it
                    continue;
                }
                // If the node doesn't exist, create it
                if (current.children[index] == null) {
                    current.children[index] = new Node();
                }
                // Move to the next node
                current = current.children[index];
            }
            // Set the last node to be the end of a word
            current.isWordEnding = true;
        }

        // Search for a word in the tree
        // Does basically the same checks as addWord by following its path to validate a word
        public boolean search(String word) {
            Node node = root;

            for (char letter : formatWord(word)) {
                int index = letter - 'a';
                if (index < 0 || index > 25) {
                    continue;
                }
                // If the node doesn't exist, no combination of these letters makes a word
                if (node.children[index] == null) {
                    return false;
                }
                // Move on to the next node
                node = node.children[index];
            }

            // Once we've gotten to the end of the word, we can check if it's a valid ending and return the result
            return node.isWordEnding;
        }
    }

    public String[] checkWords(String[] text, String[] dictionary) {

        // Tree to store all the letter combinations and whether they lead to valid words
        WordTree tree = new WordTree();
        for (String word : dictionary) {
            tree.addWord(word);
        }

        // Arraylist to store all the misspelled words
        ArrayList<String> misspelledWords = new ArrayList<>();

        // Check each word against the tree
        for (String word : text) {
            // If the word isn't in the tree, add it to the list of misspelled words
            if (!tree.search(word)) {
                misspelledWords.add(word);
            }
        }

        // Convert the ArrayList to an array
        return misspelledWords.toArray(new String[misspelledWords.size()]);
    }
}
