package com.algo.monster.twopointers;

import java.util.Scanner;
import java.util.HashMap;

/**
 * Given two strings, original and check, return the minimum substring of original such that each character in check,
 * including duplicates, are included in this substring. By "minimum", I mean the shortest substring. If two substrings
 * that satisfy the condition has the same length, the one that comes lexicographically first are smaller.
 *
 * Parameters
 * original: The original string.
 * check: The string to check if a window contains it.
 * Result
 * The smallest substring of original that satisfy the condition.
 *
 * Time Complexity: O(n)
 *
 * Space Complexity: O(n)
 *
 */
class MinimumWindowSubstring {
    public static String getMinimumWindow(String original, String check) {
        HashMap<Character, Integer> checkCount = new HashMap<>();
        HashMap<Character, Integer> windowCount = new HashMap<>();
        for (char ch : check.toCharArray()) {
            checkCount.merge(ch, 1, Integer::sum);
        }
        int satisfiedNumberOfDistinctCharacters = 0, requiredDistinctCharacters = checkCount.size();
        int m = original.length();
        int window = -1, length = m+1;
        int l = 0;
        for (int r = 0; r < m; r++) {
            windowCount.put(original.charAt(r), windowCount.getOrDefault(original.charAt(r), 0) + 1);
            if (windowCount.get(original.charAt(r)) == checkCount.get(original.charAt(r))) {
                satisfiedNumberOfDistinctCharacters++;
            }
            while (satisfiedNumberOfDistinctCharacters == requiredDistinctCharacters) {
                char leftChar = original.charAt(l);
                if(r-l+1 < length ||
                        (r-l+1 == length && original.substring(l, r+1).compareTo(original.substring(window, window+length)) < 0)) {
                    window = l;
                    length = r - l + 1;
                }
                windowCount.put(leftChar, windowCount.get(leftChar)-1);
                if(checkCount.containsKey(leftChar) && windowCount.get(original.charAt(l)) < checkCount.get(original.charAt(l))){
                    satisfiedNumberOfDistinctCharacters--;
                }
                l++;
            }
        }
        return window >= 0 ? original.substring(window, window+length):"";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String original = scanner.nextLine();
        String check = scanner.nextLine();
        scanner.close();
        String res = getMinimumWindow(original, check);
        System.out.println(res);
    }
}
