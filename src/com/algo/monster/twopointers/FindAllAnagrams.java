package com.algo.monster.twopointers;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given a string original and a string check, find the starting index of all substrings of original that is an anagram of check. The output must be sorted in ascending order.
 *
 * Parameters
 * original: A string
 * check: A string
 * Result
 * A list of integers representing the starting indices of all anagrams of check.
 *
 * Time Complexity: O(n) where n is size of original
 *
 * Space Complexity: O(c) where c is size of check
 *
 */
class FindAllAnagrams {
    public static List<Integer> findAllAnagrams(String original, String check) {
        if(check.length() > original.length())
            return List.of();

        List<Integer> indexes = new ArrayList<>();
        Map<Character, Integer> wantedState = new HashMap<>();
        for (char c : check.toCharArray()) {
            wantedState.put(c, wantedState.getOrDefault(c, 0) + 1);
        }

        int k = check.length();
        Map<Character, Integer> contains = new HashMap<>();
        for (char c : check.toCharArray()) {
            contains.put(c, 0);
        }

        for(int i  = 0 ; i < k; i++) {
            contains.put(original.charAt(i), contains.getOrDefault(original.charAt(i), 0) + 1);
        }

        if (contains.equals(wantedState)){
            indexes.add(0);
        }

        for (int right = k; right < original.length(); right++) {
            int left = right - k;
            char leftChar = original.charAt(left);
            Integer leftCharacterValue = contains.get(leftChar);
            if(leftCharacterValue != null){
                contains.put(leftChar,leftCharacterValue-1);
            }
            char rightChar = original.charAt(right);
            Integer rightCharacterValue = contains.get(rightChar);
            if(rightCharacterValue != null){
                contains.put(rightChar, rightCharacterValue+1);
            }

            if (contains.equals(wantedState)){
                indexes.add(left+1);
            }
        }
        return indexes;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String original = scanner.nextLine();
        String check = scanner.nextLine();
        scanner.close();
        List<Integer> res = findAllAnagrams(original, check);
        System.out.println(res.stream().map(String::valueOf).collect(Collectors.joining(" ")));
    }
}
