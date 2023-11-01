package com.algo.monster.backtracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Given an integer n, generate all strings with n matching parentheses. "matching" parentheses mean :
 * there is equal number of opening and closing parentheses.
 * each opening parenthesis has matching closing parentheses.
 * For example, () is a valid string but )( is not a valid string because ) has no matching parenthesis before it and ( has no matching parenthesis after it.
 *
 * Time Complexity: O(4^n * n)
 * There are 2^(2n) = 4^n combinations of possible parentheses. In addition, the string parentheses have length 2n or O(n). Multiplying these values together, we get O(4^n * n). However, since we prune the invalid branches early on, this is a very generous bound.
 *
 * Space Complexity: O(4^n * n)
 * The memory is calculated from the strings we need to store. There are 2^(2n) = 4^n combinations of possible parentheses. In addition, the string parentheses have length 2n or O(n). Multiplying these values together, we get O(4^n * n). In addition, our recursion depth or stack space is O(n).
 * Adding the two together, we still get a space complexity of O(4^n * n).
 */
class ValidParenthesisCombinationsWithAdditionalStates {
    public static List<String> generateParentheses(int n) {
        ArrayList<String> result = new ArrayList<>();
        parenthesisDfs(n, 0, 0, "", result);
        return result;
    }

    public static void parenthesisDfs(int n, int open, int close, String currentCombination, List<String> result) {
        if(currentCombination.length() == 2*n){
            result.add(currentCombination);
            return;
        }

        if(open < n) {
            parenthesisDfs(n, open + 1, close, currentCombination + '(', result);
        }

        if(close < open) {
            parenthesisDfs(n, open, close + 1, currentCombination + ')', result);
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        scanner.close();
        List<String> res = generateParentheses(n);
        Collections.sort(res);
        for (String line : res) {
            System.out.println(line);
        }
    }
}
