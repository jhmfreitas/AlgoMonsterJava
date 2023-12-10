package com.algo.monster.dynamicprogramming;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Collections;

/**
 * You are given a set of numbers nums consisting of distinct numbers. Find the size of the largest subset that
 * satisfies the following condition: for each two number pairings in the set, one number is divisible by the other.
 *
 * Time Complexity: O(n^2)
 *
 * Space Complexity: O(n)
 *
 */
class LargestDivisibleSubset {
    public static int findLargestSubset(List<Integer> nums) {
        Collections.sort(nums);
        int N = nums.size();
        int[] dp = new int[N + 1];

        dp[0] = 0; // base case: no elements has an LIS of length 0
        int len = 0;
        for (int i = 1; i <= N; ++i) {
            int ni = nums.get(i - 1);
            dp[i] = dp[0] + 1; // first we try starting a new sequence

            for (int j = 1; j < i; ++j) { // then try extending an existing LIS from indices less than i
                int nj = nums.get(j - 1);
                if (ni % nj == 0) {
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
        int res = findLargestSubset(nums);
        System.out.println(res);
    }
}
