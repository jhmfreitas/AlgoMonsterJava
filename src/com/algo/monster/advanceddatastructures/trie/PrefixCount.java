package com.algo.monster.advanceddatastructures.trie;

import java.util.*;
import java.util.stream.Collectors;

class PrefixCount {

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
    public static List<Integer> prefixCount(List<String> words, List<String> prefixes) {
        List<Integer> queryResults = new ArrayList<>();
        Trie trie = new Trie();

        for (String word : words) {
            trie.insert(word);
        }

        for (String prefix : prefixes) {
            queryResults.add(trie.query(prefix));
        }

        return queryResults;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> words = splitWords(scanner.nextLine());
        List<String> prefixes = splitWords(scanner.nextLine());
        scanner.close();
        List<Integer> res = prefixCount(words, prefixes);
        System.out.println(res.stream().map(String::valueOf).collect(Collectors.joining(" ")));
    }
}
