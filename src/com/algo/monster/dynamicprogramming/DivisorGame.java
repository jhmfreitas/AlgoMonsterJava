package com.algo.monster.dynamicprogramming;

import java.util.Scanner;

/**
 *
 * James and Oliver are playing a fun number game. The game's strategy is based on recognizing "winning" and "losing" positions for each player.
 * A winning position is one where a player can force a win regardless of what the opponent does, while a losing position is one where the opponent
 * can force a win. Here's how they play:
 *
 * They begin with a number, N, written on the board.
 * During their turn, with James starting first, a player chooses a number x smaller than N that divides N evenly. They then subtract x from N.
 * If a player can't find such a number, they lose.
 * Given the starting number N, assuming both players play optimally, return true if James wins and false if Oliver wins.
 *
 *
 * Time Complexity: O(n^2)
 *
 * Space Complexity: O(n)
 *
 */
class DivisorGame {
    public static boolean divisorGame(int n) {
        boolean[] dp = new boolean[n+1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                if (n % i == 0 && !dp[i-j]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        scanner.close();
        boolean res = divisorGame(n);
        System.out.println(res);
    }
}
