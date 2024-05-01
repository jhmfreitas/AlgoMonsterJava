package com.algo.monster.dynamicprogramming;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CoinChange {

    public static int coinChange(List<Integer> coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);

        dp[0] = 0;
        for (int a = 1; a <= amount; a++) {
            for (Integer coin : coins) {
                if (a - coin >= 0) {
                    dp[a] = Math.min(dp[a], 1 + dp[a -coin]);
                }
            }
        }
        return dp[amount] != amount + 1 ? dp[amount] : -1;
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
