package com.algo.monster.advanceddatastructures.trie;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * For this question, we first give you a series of n words. For every word, we first add it to our
 * dictionary, and then we type out the word using the minimum number of strokes to autocomplete the word.
 * What is the minimum total number of strokes needed to type out all the words?
 *
 * Time Complexity: O(c) where c is the total number of characters in the input Space Complexity: O(c)
 */
class Autocomplete {

    public static class Trie {
        HashMap<Character, Trie> children = new HashMap<>();
        Integer freq = 0;

        public void insert(String word) {
            Trie node = this;
            for (char c : word.toCharArray()) {
                node.children.putIfAbsent(c, new Trie());
                node = node.children.get(c);
                node.freq++; // Increase frequency for this prefix
            }
        }

        public int query(String prefix) {
            Trie node = this;
            for (char c : prefix.toCharArray()) {
                if (!node.children.containsKey(c)) {
                    return 0;
                }
                node = node.children.get(c);
            }
            return node.freq;
        }
    }
    public static int autocomplete(List<String> words) {
        Trie trie = new Trie();
        int strokes = 0;
        for (String word : words) {
            trie.insert(word);
            int strokesForWord = 0;
            for (int i = 1; i <= word.length(); i++) {
                strokesForWord++;
                if (trie.query(word.substring(0, i)) == 1) {
                    break;
                }
            }
            strokes += strokesForWord;
        }

        return strokes;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> words = splitWords(scanner.nextLine());
        scanner.close();
        int res = autocomplete(words);
        System.out.println(res);
    }
}
