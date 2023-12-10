package com.algo.monster.dynamicprogramming;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *Input
 * nums: the integer sequence
 * Output
 * the length of longest increasing subsequence
 *
 * The runtime for this solution is O(n^2) since there are O(n) states and each state takes O(n) to compute.
 * The space complexity is O(n) due to the use of the dp array of length O(n).
 *
 */
class LongestIncreasingSubsequence {

    public static int longestSubLen(List<Integer> nums) {
        int N = nums.size();
        int[] dp = new int[N + 1];

        dp[0] = 0; // base case: no elements has an LIS of length 0
        int len = 0;
        for (int i = 1; i <= N; ++i) {
            int ni = nums.get(i - 1);
            dp[i] = dp[0] + 1; // first we try starting a new sequence

            for (int j = 1; j < i; ++j) { // then try extending an existing LIS from indices less than i
                int nj = nums.get(j - 1);
                if (nj < ni) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            len = Math.max(len, dp[i]);
        }
        return len;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> nums = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        scanner.close();
        int res = longestSubLen(nums);
        System.out.println(res);
    }
}
