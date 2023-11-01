package com.algo.monster.backtracking;

import java.util.Scanner;

/**
 *We have a message consisting of digits 0-9 to decode. Letters are encoded to digits by their positions in the alphabet
 *
 * A -> 1
 * B -> 2
 * C -> 3
 * ...
 * Y -> 25
 * Z -> 26
 * Given a non-empty string of digits, how many ways are there to decode it?
 *
 * Time complexity
 * The time complexity of the memoization solution is the size of the memo array O(n) multiplied by the number of operations per state which is O(1).
 * So the overall time complexity is O(n).
 *
 * Space complexity
 * The height of the state-space tree is at most O(n). The size of the memo array is O(n).
 * Therefore the space complexity is O(n).
 *
 */
class NumberOfWaysToDecodeMemoization {
    public static int decodeWays(String digits) {
        return dfs(0, digits, new Integer[digits.length()]);
    }

    private static int dfs(int startIndex, String digits, Integer[] memo) {
        if(startIndex == digits.length()){
            return 1;
        }

        if(memo[startIndex] != null) {
            return memo[startIndex];
        }

        int ways = 0;
        // can't decode string with leading 0
        if (digits.charAt(startIndex) == '0') {
            return ways;
        }

        //decode 1 digit
        ways += dfs(startIndex + 1, digits, memo);

        //decode 2 digits
        // Starting from 27 there is no corresponding letter to the numbers
        if(startIndex + 2 <= digits.length() && Integer.parseInt(digits.substring(startIndex, startIndex + 2 )) <= 26){
            ways += dfs(startIndex + 2, digits, memo);
        }

        memo[startIndex] = ways;
        return ways;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String digits = scanner.nextLine();
        scanner.close();
        int res = decodeWays(digits);
        System.out.println(res);
    }
}