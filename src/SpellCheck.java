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

    private class TrieNode {
        // Each node has space for 26 children, one for each letter of the alphabet, plus one for other characters
        TrieNode[] children = new TrieNode[256];
        // If a node is a word ending, set a boolean to communicate it
        boolean isWordEnding = false;
    }

    private class WordTrie {
        TrieNode root = new TrieNode();

        public char[] formatWord(String word) {
            return word.toCharArray();
        }

        // Add a word to the tree
        public void addWord(String word) {
            TrieNode current = root;

            for (char letter : formatWord(word)) {
                int index = letter;

                // If the node doesn't exist, create it
                if (current.children[index] == null) {
                    current.children[index] = new TrieNode();
                }
                // Move to the next node
                current = current.children[index];
            }
            // Set the last node to be the end of a word
            current.isWordEnding = true;
        }

        // Search for a word in the tree
        // Does basically the same checks as addWord by following its path to validate a word
        public boolean searchWord(String word) {
            TrieNode node = root;

            for (char letter : formatWord(word)) {
                int index = letter;
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

    private class TSTNode {
        char value;
        boolean isWordEnding;
        TSTNode less, equal, greater;

        // Basic constructor
        public TSTNode(char value) {
            this.value = value;
        }
    }

    private class TST {
        TSTNode root;

        // Adds a word to the TST
        public void addWord(String word) {
            // If the tree is empty, create a new root node
            if (root == null) {
                root = new TSTNode(word.charAt(0));
            }
            TSTNode currentNode = root;
            int letterIndex = 0;

            // Traverse the tree until we've added all the letters of the word
            while (letterIndex < word.length()) {
                char letter = word.charAt(letterIndex);

                // If the current node's value is greater than the current letter, traverse to the less node
                if (letter < currentNode.value) {
                    if (currentNode.less == null) {
                        currentNode.less = new TSTNode(letter);
                    }
                    currentNode = currentNode.less;
                }
                // If the current node's value is less than the current letter, traverse to the greater node
                else if (letter > currentNode.value) {
                    if (currentNode.greater == null) {
                        currentNode.greater = new TSTNode(letter);
                    }
                    currentNode = currentNode.greater;
                }
                // If the current node's value is equal to the current letter, traverse to the equal node
                else {
                    // Increment the letter index, as we've found a match
                    letterIndex++;
                    if (letterIndex < word.length()) {
                        if (currentNode.equal == null) {
                            currentNode.equal = new TSTNode(word.charAt(letterIndex));
                        }
                        currentNode = currentNode.equal;
                    }
                }
            }
            // Once we've added all the letters, mark the node as a word ending
            currentNode.isWordEnding = true;
        }

        public boolean searchWord(String word) {
            TSTNode currentNode = root;
            int letterIndex = 0;

            // Traverse the tree until we've found the word or reached a null node
            while (currentNode != null && letterIndex < word.length()) {
                char letter = word.charAt(letterIndex);

                // If the current node's value is greater than the current letter, traverse to the less node
                if (letter < currentNode.value) {
                    currentNode = currentNode.less;
                // If the current node's value is less than the current letter, traverse to the greater node
                } else if (letter > currentNode.value) {
                    currentNode = currentNode.greater;
                // If the current node's value is equal to the current letter, traverse to the equal node
                } else {
                    // Increment the letter index, as we've found a match
                    letterIndex++;
                    if (letterIndex < word.length()) {
                        currentNode = currentNode.equal;
                    }
                }
            }
            // Return whether the current node is a word ending and not null
            return currentNode != null && currentNode.isWordEnding;
        }
    }

    /**
     * checkWords finds all words in text that are not present in dictionary
     *
     * @param text The list of all words in the text.
     * @param dictionary The list of all accepted words.
     * @return String[] of all mispelled words in the order they appear in text. No duplicates.
     */
    public String[] checkWords(String[] text, String[] dictionary) {
        // Tree to store all the letter combinations and whether they lead to valid words
        TST tree = new TST();
        for (String word : dictionary) {
            tree.addWord(word);
        }

        // Arraylist to store all the misspelled words
        ArrayList<String> misspelledWords = new ArrayList<>();

        // Check each word against the tree
        for (String word : text) {
            // If the word isn't in the tree, add it to the list of misspelled words
            if (!tree.searchWord(word)) {
                misspelledWords.add(word);

                // Add the word to the tree, so we don't have to check it again
                tree.addWord(word);
            }
        }

        return misspelledWords.toArray(new String[0]);
    }
}
