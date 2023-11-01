package com.algo.monster.backtracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * Given a string of unique letters, find all of its distinct permutations.
 *
 * Time Complexity
 * We have n letters to choose in the first level, n - 1 choices in the second level and so on therefore
 * the number of strings we generate is n * (n - 1) * (n - 2) * ... * 1, or O(n!)
 * (see math basics if you need a refresher on factorial). Since each string has length n, generating all the strings requires O(n * n!) time.
 *
 * Space Complexity
 * The total space complexity is given by the amount of space required by the strings we're constructing.
 * Like the time complexity, the space complexity is also O(n * n!).
 */
class GenerateAllPermutations {
    public static List<String> permutations(String letters) {
        ArrayList<String> result = new ArrayList<>();
        letterPermutations(letters, new ArrayList<>(),letters.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toCollection(ArrayList::new)) , result);
        return result;
    }

    public static void letterPermutations(String letters, ArrayList<String> currentCombination , ArrayList<Character> lettersToUse, ArrayList<String> result) {
        if(currentCombination.size() == letters.length()) {
            result.add(String.join("", currentCombination));
            return;
        }

        for (Character letter : new ArrayList<>(lettersToUse)) {
            currentCombination.add(String.valueOf(letter));
            lettersToUse.remove(letter);
            letterPermutations(letters, currentCombination, lettersToUse,result);
            currentCombination.remove(currentCombination.size() - 1);
            lettersToUse.add(letter);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String letters = scanner.nextLine();
        scanner.close();
        List<String> res = permutations(letters);
        Collections.sort(res);
        for (String line : res) {
            System.out.println(line);
        }
    }
}
