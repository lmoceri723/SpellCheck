import jdk.jfr.Unsigned;

import java.util.ArrayList;
import java.util.Hashtable;

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
    public String[] checkWords(String[] text, String[] dictionary) {
        Hashtable <String, Integer> dict = new Hashtable <String, Integer>();
        ArrayList <String> badWords = new ArrayList <String>();


        for (String word : dictionary) {
            dict.put(word, 1);
        }

        for (String word : text) {
            if (!dict.containsKey(word)) {
                badWords.add(word);
                dict.put(word, 1);
            }
        }

        return badWords.toArray(new String[badWords.size()]);
    }
}
