package com.algo.monster.graph;

import java.util.*;

/**
 * Word Ladder is "a puzzle begins with two words, and to solve the puzzle one must find a chain of other words to link
 * the two, in which two adjacent words (that is, words in successive steps) differ by one letter."
 * For example: COLD → CORD → CARD → WARD → WARM
 * Given a start word, an end word, and a list of dictionary words, determine the minimum number of steps to go from the
 * start word to the end word using only words from the dictionary.
 *
 * Time Complexity: O(n+m)
 * The nodes in the graph are determined by the number of words in the dictionary.
 * Edges are determined by words that are 1 letter apart. Another note is that after edges are computed some performance
 * increases might be possible by hashing the string to a numerical value.
 *
 * Space Complexity: O(w * n)
 * There are at most n strings in the queue and each word is size w.
 */
class WordLadder {
    public static int wordLadder(String begin, String end, List<String> wordList) {
        // Searches in a hashset are O(1) so we use a set here
        Set<String> wordSet = new HashSet<>(wordList);
        if (!wordList.contains(end)) {
            return 0;
        }

        int ladderLength = 0;
        ArrayDeque<String> queue = new ArrayDeque<>();
        queue.offer(begin);
        while (!queue.isEmpty()) {
            int numberOfWordInThisLevel = queue.size();
            for (int i = 0; i < numberOfWordInThisLevel; i++) {
                String node = queue.poll();
                if(node != null) {
                    char[] nodeCharArray = node.toCharArray();
                    for (int j = 0; j < nodeCharArray.length; j++) {
                        char originalChar = nodeCharArray[j];
                        for (char c = 'a'; c <= 'z'; c++) {
                            if(c == originalChar) {
                                continue;
                            }

                            nodeCharArray[j] = c;
                            String newWord = new String(nodeCharArray);
                            if(newWord.equals(end)) {
                                return ladderLength + 1;
                            }

                            if (wordSet.contains(newWord)) {
                                wordSet.remove(newWord);
                                queue.offer(newWord);
                            }
                            nodeCharArray[j] = originalChar;
                        }
                    }
                }
            }
            ladderLength++;
        }
        return -1;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String begin = scanner.nextLine();
        String end = scanner.nextLine();
        List<String> wordList = splitWords(scanner.nextLine());
        scanner.close();
        int res = wordLadder(begin, end, wordList);
        System.out.println(res);
    }
}
