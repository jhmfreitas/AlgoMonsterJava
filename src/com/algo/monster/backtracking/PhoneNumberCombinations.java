package com.algo.monster.backtracking;

import java.util.*;

/**
 * Given a phone number that contains digits 2-9, find all possible letter combinations the phone number could translate to.
 *
 * Time complexity
 * The time complexity of a backtracking algorithm is the number of nodes in the state-space tree multiplied by the operation at each node.
 * In the worse case where we only have 7s and 9s in the input phone number, each node has 4 children. And the height of the tree is the number of digits of the phone number. Therefore the tree has maximum of 4^n nodes where n is the number of digits of the phone number. We also need O(n) to build a new string when we reach the leaf node so the total complexity is O(4^n * n).
 *
 * Space complexity
 * Similar to the time complexity, because we produce O(4^n) strings and each string has a length of O(n), the space complexity for this part will be O(4^n * n).
 * In addition, our recursion depth is O(n). Adding the two together, we still get a space complexity of O(4^n * n).
 */
class PhoneNumberCombinations {

    public static Map<Character, char[]> numberLetterCombinations = new HashMap<Character, char[]>();
    
    static {
        numberLetterCombinations.put('1', "".toCharArray());
        numberLetterCombinations.put('2', "abc".toCharArray());
        numberLetterCombinations.put('3', "def".toCharArray());
        numberLetterCombinations.put('4', "ghi".toCharArray());
        numberLetterCombinations.put('5', "jkl".toCharArray());
        numberLetterCombinations.put('6', "mno".toCharArray());
        numberLetterCombinations.put('7', "pqrs".toCharArray());
        numberLetterCombinations.put('8', "tuv".toCharArray());
        numberLetterCombinations.put('9', "wxyz".toCharArray());
    }

    public static List<String> letterCombinationsOfPhoneNumber(String digits) {
        ArrayList<String> result = new ArrayList<>();
        letterCombinationsOfPhoneNumber(digits.toCharArray(), 0, new ArrayList<>(), result);
        return result;
    }

    public static void letterCombinationsOfPhoneNumber(char[] digits, int startIndex, ArrayList<String> currentPath, ArrayList<String> result) {
        if(digits.length == startIndex) {
            result.add(String.join("", currentPath));
            return;
        }

        char digit = digits[startIndex];
        for (char letter : numberLetterCombinations.get(digit)) {
            currentPath.add(String.valueOf(letter));
            letterCombinationsOfPhoneNumber(digits, startIndex+1, currentPath, result);
            currentPath.remove(currentPath.size() - 1);
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String digits = scanner.nextLine();
        scanner.close();
        List<String> res = letterCombinationsOfPhoneNumber(digits);
        System.out.println(String.join(" ", res));
    }
}
