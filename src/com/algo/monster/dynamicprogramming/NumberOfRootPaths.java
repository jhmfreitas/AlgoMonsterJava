package com.algo.monster.dynamicprogramming;

import java.util.Scanner;

/**
 * A robot starts its journey at the top-left corner of a grid that measures m x n (m rows by n columns).
 *
 * At each step, the robot has only two possible directions: it can either move to the right or move downward. Its destination is the bottom-right corner of the grid.
 *
 * Determine the total number of unique paths the robot can take to reach its destination.
 *
 * Time Complexity: O(n * m).
 *
 * Space Complexity: O(n * m).
 */
class NumberOfRootPaths {
    public static int uniquePaths(int m, int n) {
        if(m == 0 && n == 0) {
            return 0;
        }

        int[][] dp = new int[m][n];
        for (int i= 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 1;
                }else{
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }

        return dp[m-1][n-1];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int m = Integer.parseInt(scanner.nextLine());
        int n = Integer.parseInt(scanner.nextLine());
        scanner.close();
        int res = uniquePaths(m, n);
        System.out.println(res);
    }
}
