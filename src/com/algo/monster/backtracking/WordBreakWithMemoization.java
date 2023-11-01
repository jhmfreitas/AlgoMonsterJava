package com.algo.monster.backtracking;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Given a string and a list of words, determine if the string can be constructed from concatenating words from the list of words.
 * A word can be used multiple times.
 *
 * Time complexity
 * The size of the memo array is O(n) where n is the length of the target word.
 * For each state in the memo array, we have to try every dictionary word to see if it's a prefix of the target word at the current position.
 * Assuming the size of the dictionary is m, string matching takes O(n), so the overall time complexity is O(n^2 * m);
 *
 * Space complexity
 * The height of the state-space tree is O(n). The size of the memo array is O(n). Therefore the overall space complexity is O(n).
 *
 */
class WordBreakWithMemoization {
    public static boolean wordBreak(String s, List<String> words) {
        return dfs(0, s, words, new Boolean[s.length()]);
    }

    private static boolean dfs(int startIndex, String s, List<String> words, Boolean[] memo) {
        if(startIndex == s.length()){
            return true;
        }

        if(memo[startIndex]!= null) {
            return memo[startIndex];
        }

        boolean result = false;
        for (String word : words) {
            if(s.substring(startIndex).startsWith(word)){
                result = result || dfs(startIndex + word.length(), s, words, memo);
            }
        }

        memo[startIndex] = result;
        return result;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        List<String> words = splitWords(scanner.nextLine());
        scanner.close();
        boolean res = wordBreak(s, words);
        System.out.println(res);
    }
}
