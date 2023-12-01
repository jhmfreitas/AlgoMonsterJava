package com.algo.monster.twopointers;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Find the length of the longest substring of a given string without repeating characters.
 *
 * Input: abccabcabcc
 *
 * Output: 3
 *
 * Explanation: longest substrings are abc, cab, both of length 3
 *
 * Input: aaaabaaa
 *
 * Output: 2
 *
 * Explanation: ab is the longest substring, length 2
 *
 * Time Complexity: O(n)
 *
 * Space Complexity: O(n)
 *
 */
class LongestSubstringWithoutRepeatingCharacters {
    public static int longestSubstringWithoutRepeatingCharacters(String s) {
        Map<Character, Integer> charCount = new HashMap<>();
        int right = 0;
        int longest = 0;
        for (int left = right; right < s.length(); right++) {
            charCount.put(s.charAt(right), charCount.getOrDefault(s.charAt(right), 0) + 1);
            while (charCount.get(s.charAt(right))>1) {
                charCount.put(s.charAt(left), charCount.get(s.charAt(left)) - 1);
                left++;
            }
            longest = Math.max(longest, right - left + 1);
        }
        return longest;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        scanner.close();
        int res = longestSubstringWithoutRepeatingCharacters(s);
        System.out.println(res);
    }
}
