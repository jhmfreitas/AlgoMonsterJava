package com.algo.monster.priorityqueue;

import java.util.*;

/**
 * Given a string s, check if the letters can be rearranged so that two characters that are adjacent to each other are
 * not the same.
 *
 * If possible, output any possible result. If not possible, return the empty string.
 *
 * Example 1:
 * Input:s = "aab"
 * Output: aba
 * Example 2:
 * Input:s = "aaab"
 * Output: ``
 *
 * Time Complexity: O(n + k*log(k)) where n is the size of the array and k is the number of distinct characters.
 *
 * Space Complexity: O(k)
 *
 */
class ReorganizeString {
    public static String reorganizeString(String s) {
        int n = s.length();
        HashMap<Character, Integer> charCount = new HashMap<>();
        for (char c : s.toCharArray()) {
            charCount.merge(c, 1, Integer::sum);
        }

        PriorityQueue<Map.Entry<Character, Integer>> priorityQueue = new PriorityQueue<>((a, b) -> Integer.compare(b.getValue(), a.getValue()));
        priorityQueue.addAll(charCount.entrySet());

        if(priorityQueue.isEmpty() || priorityQueue.peek().getValue() > (n+1)/2) {
            return "";
        }

        char[] reorganizedChars = new char[n];
        int pointer = 0;
        while (priorityQueue.size() != 0) {
            Map.Entry<Character, Integer> element = priorityQueue.poll();
            for (int i = 0; i < element.getValue() ; i++) {
                reorganizedChars[pointer] = element.getKey();
                pointer+=2;
                if (pointer >= n) {
                    pointer = 1;
                }
            }
        }
        return new String(reorganizedChars);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        scanner.close();
        String res = reorganizeString(s);
        if (res.isEmpty()) {
            System.out.println("Impossible");
            return;
        }
        HashMap<Character, Integer> sCount = new HashMap<>(), resCount = new HashMap<>();
        for (char e : s.toCharArray()) {
            sCount.merge(e, 1, Integer::sum);
        }
        for (char e : res.toCharArray()) {
            resCount.merge(e, 1, Integer::sum);
        }
        if (!sCount.equals(resCount)) {
            System.out.println("Not rearrangement");
            return;
        }
        for (int i = 0; i < res.length() - 1; i++) {
            if (res.charAt(i) == res.charAt(i + 1)) {
                System.out.printf("Same character at index %d and %d\n", i, i + 1);
                return;
            }
        }
        System.out.println("Valid");
    }
}
