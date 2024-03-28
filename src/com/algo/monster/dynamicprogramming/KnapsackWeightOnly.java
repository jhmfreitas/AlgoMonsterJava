package com.algo.monster.dynamicprogramming;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * Given a list of weights of n items, find all sums that can be formed using their weights.
 *
 * Input
 * weights: A list of n positive integers, representing weights of the items
 *
 * Output
 * A list, in any order, of the unique sums that can be obtained by using combinations of the provided weights
 *
 * Time Complexity: O(n * totalSum)
 */
class KnapsackWeightOnly {

    // Brute Force
    // Time Complexity: O(2^n)
    public static void generateSums(List<Integer> weights, Set<Integer> sums, int sum, int n) {
        if (n == 0) {
            sums.add(sum);
            return;
        }

        // Here we try to calculate the sums when adding the item and when not adding the item

        // Does not add item
        generateSums(weights, sums, sum, n - 1);
        // Adds item
        generateSums(weights, sums, sum + weights.get(n - 1), n - 1);
    }

    public static List<Integer> knapsackWeightOnly(List<Integer> weights) {
        Set<Integer> sums = new HashSet<>();
        int n = weights.size();
        generateSums(weights, sums, 0, n);
        List<Integer> ans = new ArrayList<>();
        ans.addAll(sums);
        return ans;
    }

    // Top down Dynamic Programming
    public static void generateSums(List<Integer> weights, Set<Integer> sums, int sum, int n, boolean[][] memo) {
        if (memo[n][sum]) {
            return;
        }
        if (n == 0) {
            sums.add(sum);
            return;
        }
        generateSums(weights, sums, sum, n - 1, memo);
        generateSums(weights, sums, sum + weights.get(n - 1), n - 1, memo);
        memo[n][sum] = true;
    }

    public static List<Integer> knapsackWeightOnly(List<Integer> weights) {
        Set<Integer> sums = new HashSet<>();
        int n = weights.size();
        // find total sum of all items
        int totalSum = 0;
        for (int weight : weights) {
            totalSum += weight;
        }
        // initialize memo table to store if result has been calculated
        boolean[][] memo = new boolean[n + 1][totalSum + 1];
        for (int i = 0; i < n + 1; i++) {
            Arrays.fill(memo[i], false);
        }
        generateSums(weights, sums, 0, n, memo);
        List<Integer> ans = new ArrayList<>();
        ans.addAll(sums);
        return ans;
    }

    // Bottom-Up Dynamic Programming
    // Space Complexity: O(n * totalSum)
    public static List<Integer> knapsackWeightOnly(List<Integer> weights) {
        int totalSum = 0;
        for (int weight : weights)
            totalSum += weight;

        // +1 to include 0
        int n = weights.size();
        boolean[][] dp = new boolean[n + 1][totalSum + 1];
        dp[0][0] = true;

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= totalSum; w++) {
                if (w - weights.get(i - 1) < 0) {
                    dp[i][w] = dp[i - 1][w];
                } else {
                    dp[i][w] = dp[i - 1][w] || dp[i - 1][w - weights.get(i - 1)];
                }
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i <= totalSum; i++) {
            if (dp[n][i]){
                res.add(i);
            }
        }
        return res;
    }

    // Bottom-Up Dynamic Programming + Space Optimization
    //
    // It is true that the results from the current row only rely on the previous row at most
    // Therefore, we can store only two arrays, one for previous and another for current
    public static List<Integer> knapsackWeightOnly(List<Integer> weights) {
        int totalSum = 0;
        for (int weight : weights)
            totalSum += weight;

        // +1 to include 0
        int n = weights.size();
        boolean[][] dp = new boolean[2][totalSum + 1];
        dp[0][0] = true;

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= totalSum; w++) {
                if (w - weights.get(i - 1) < 0) {
                    dp[1][w] = dp[0][w];
                } else {
                    dp[1][w] = dp[0][w] || dp[0][w - weights.get(i - 1)];
                }
            }

            // Update previous row to current row
            for (int k = 0; k <= totalSum; k++) {
                dp[0][k] = dp[1][k];
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i <= totalSum; i++) {
            if (dp[1][i]){
                res.add(i);
            }
        }
        return res;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> weights = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        scanner.close();
        List<Integer> res = knapsackWeightOnly(weights);
        Collections.sort(res);
        for (int i = 0; i < res.size(); i++) {
            System.out.print(res.get(i));
            if (i != res.size() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}