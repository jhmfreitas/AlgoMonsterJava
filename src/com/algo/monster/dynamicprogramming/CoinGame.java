package com.algo.monster.dynamicprogramming;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * You and a friend are playing a game with n coins arranged in a straight line, where each coin has a distinct value
 * given by coins[i]. Starting with you, both players take turns, picking one coin from either end of the line and adding
 * its value to their individual scores. Once a coin is picked up, it's removed from the line.
 *
 * Given that your friend plays optimally to achieve the highest score, determine your maximum possible score.
 *
 * Time Complexity: Since there are n turns, 2 possibilities each turn, and takes O(n) to calculate the sum from l to r, the final runtime is O(n * 2^n)
 * Space Complexity: O(n)
 */
class CoinGame {
    public static int maxScore(int[][] dp, int[] prefixSum, int l, int r) {
        // Return memoized result
        if (dp[l][r] != 0) {
            return dp[l][r];
        }

        // Calculate total coins value from l to r
        int sum = prefixSum[r] - prefixSum[l - 1];

        // If only one coin is present, return its value
        if (l == r) {
            dp[l][r] = sum;
        } else {
            // Consider left and right coin and choose the better option
            dp[l][r] = sum - Math.min(maxScore(dp, prefixSum, l + 1, r), maxScore(dp, prefixSum, l, r - 1));
        }

        return dp[l][r];
    }

    public static int coinGame(List<Integer> coins) {
        int n = coins.size();
        int[][] dp = new int[n + 1][n + 1];
        int[] prefixSum = new int[n + 1];
        prefixSum[0] = 0;
        // Compute prefix sum of coins
        for (int i = 1; i <= n; i++) {
            prefixSum[i] = prefixSum[i - 1] + coins.get(i - 1);
        }

        return maxScore(dp, prefixSum, 1, n);
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> coins = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        scanner.close();
        int res = coinGame(coins);
        System.out.println(res);
    }
}
