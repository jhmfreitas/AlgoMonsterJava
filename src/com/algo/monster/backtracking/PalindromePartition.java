package com.algo.monster.backtracking;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Given a string s, partition s such that every substring of the partition is a palindrome.
 *
 * Return all possible palindrome partitioning of s.
 *
 * Time Complexity
 * Estimating time complexity of backtracking on a pruned tree is tricky because pruned branches should be excluded from the overall time complexity.
 * One way to estimate is to look at the operations we have done on the input. For each letter in the input string, we can either include it as a previous string or start a new string with it.
 * With those two choices, the total number of operations is 2^n. We also need O(n) to check if the string is a palindrome. In total, the complexity is O(2^n * n).
 *
 * Space Complexity
 * The space complexity depends on the height of the tree , and the height of the state-space tree is worst-case O(n).
 */
class PalindromePartition {
    public static boolean isPalindrome(String s) {
        int i = 0;
        int j = s.length() - 1;
        char[] charArray = s.toCharArray();

        while (i<j) {
            if(charArray[i] != charArray[j]) {
                return false;
            }
            i++;
            j--;
        }
        return true ;
    }

    public static List<List<String>> partition(String s) {
        ArrayList<List<String>> result = new ArrayList<>();
        palindromeChecker(s, 0, new ArrayList<>(), result);
        return result;
    }

    public static void palindromeChecker(String characters, int startIndex, ArrayList<String> currentPath, ArrayList<List<String>> result) {
        if(characters.length() == startIndex) {
            result.add(new ArrayList<>(currentPath));
            return;
        }

        for (int i =startIndex; i < characters.length();i++) {
            String suffix = characters.substring(startIndex,i+1);
            if(isPalindrome(suffix)){
                currentPath.add(suffix);
                palindromeChecker(characters, i+1, currentPath, result);
                currentPath.remove(currentPath.size() - 1);
            }
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        scanner.close();
        List<List<String>> res = partition(s);
        for (List<String> row : res) {
            System.out.println(String.join(" ", row));
        }
    }
}