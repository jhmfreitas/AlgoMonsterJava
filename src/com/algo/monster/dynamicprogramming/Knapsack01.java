package com.algo.monster.dynamicprogramming;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * We want to discuss a classic dynamic programming problem, which is 0-1 knapsack.
 * Given a series of objects with a weight and a value and a knapsack that can carry a set amount of weight,
 * what is the maximum object value we can put in our knapsack without exceeding the weight constraint?
 *
 * Input
 * weights: an array of integers that denote the weights of objects
 * values: an array of integers that denote the values of objects
 * max_weight: the maximum weight capacity of the knapsack
 *
 * Output
 * the maximum value in the knapsack
 *
 * Time Complexity: O(n * max_weight)
 * Space Complexity: O(n * max_weight)
 */
public class Knapsack01 {
    public static int knapsack(List<Integer> weights, List<Integer> values, int maxWeight) {
        int[][] dp = new int[values.size() + 1][maxWeight + 1];
        for (int i = 0; i <= values.size(); i++) {
            Arrays.fill(dp[i], 0);
        }

        for (int i = 1; i <= values.size(); i++) {
            for (int j = 0; j <= maxWeight; j++) {
                int complement = j - weights.get(i - 1);
                if (complement >= 0) {
                    dp[i][j] = Math.max(dp[i - 1][complement] + values.get(i - 1), dp[i - 1][j]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        // Usually we do a cycle through the last row to see which is the result
        // At first I did a cycle and I updated a variable with the max value but realized
        // it wasn't necessary because the values are propagated even for the columns we don't
        // have weight to correspond to
        return dp[values.size()][maxWeight];
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> weights = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> values = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        int maxWeight = Integer.parseInt(scanner.nextLine());
        scanner.close();
        int res = knapsack(weights, values, maxWeight);
        System.out.println(res);
    }
}
