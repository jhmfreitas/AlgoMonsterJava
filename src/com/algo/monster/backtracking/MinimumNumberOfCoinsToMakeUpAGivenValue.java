package com.algo.monster.backtracking;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * You are given coins of different denominations and an amount. Write a function to compute the fewest number of coins that you need to make up that amount.
 * If that amount of money cannot be made up by any combination of the coins, return -1.
 *
 * Time complexity
 * The size of the memo array is O(amount). For each amount, we try up to n coins.
 * So the overall time complexity is O(amount * n).
 *
 * Space complexity
 * The height of the state-space tree is O(amount / min(coins)).
 * However the memo array takes O(amount) space so the overall space complexity is O(amount).
 */
class MinimumNumberOfCoinsToMakeUpAGivenValue {
    public static int coinChange(List<Integer> coins, int amount) {
        int result = dfs(coins, amount, new Integer[amount + 1], 0);
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    private static int dfs(List<Integer> coins, int amount, Integer[] memo, int currentSum) {
        if(currentSum == amount){
            return 0;
        }

        if(currentSum > amount) {
            return Integer.MAX_VALUE;
        }

        if(memo[currentSum] != null){
            return memo[currentSum];
        }

        int coinNumber = Integer.MAX_VALUE;
        for (Integer coin : coins) {
            int result = dfs(coins, amount , memo, currentSum + coin);
            if(result == Integer.MAX_VALUE) {
                continue;
            }
            coinNumber = Math.min(coinNumber, result + 1);
        }

        return memo[currentSum] = coinNumber;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> coins = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        int amount = Integer.parseInt(scanner.nextLine());
        scanner.close();
        int res = coinChange(coins, amount);
        System.out.println(res);
    }
}