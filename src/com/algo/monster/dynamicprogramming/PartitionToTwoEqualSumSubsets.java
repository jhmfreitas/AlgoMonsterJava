package com.algo.monster.dynamicprogramming;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/*
Consider a non-empty array containing only positive integers, find if the array can be partitioned into two subsets
such that the sum of elements in both subsets is equal.

Constraints:

Each of the array element will not exceed 100.
The array size will not exceed 200.

Input
nums: the array

Output
if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal

 */
public class PartitionToTwoEqualSumSubsets {

    // Bottom Up Dynamic Programming
    // Time complexity: O(n * totalSum/2)
    // Space complexity: O(n * totalSum/2)
    public static boolean canPartition(List<Integer> nums) {
        int totalSum = 0;
        for (int n : nums) {
            totalSum += n;
        }

        if(totalSum % 2 != 0)
            return false;

        boolean[][] dp = new boolean[nums.size() + 1][totalSum/2 + 1];
        for (int i = 0; i <= nums.size(); i++) {
            Arrays.fill(dp[i], false);
        }

        dp[0][0] = true;
        for (int i = 1; i <= nums.size(); i++) {
            for (int j = 0; j <= totalSum/2; j++) {
                if (j - nums.get(i - 1) < 0) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums.get(i - 1)];
                }
            }
        }

        return dp[nums.size()][totalSum/2];
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> nums = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        scanner.close();
        boolean res = canPartition(nums);
        System.out.println(res);
    }
}
