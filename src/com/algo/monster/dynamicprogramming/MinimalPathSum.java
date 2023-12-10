package com.algo.monster.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Suppose we have a m by n matrix filled with non-negative integers, find a path from top left corner to bottom right
 * corner which minimizes the sum of all numbers along its path.
 *
 * Note: Movements can only be either down or right at any point in time.
 *
 * Time Complexity: O(m * n)
 *
 * There are O(m * n) states to consider, and each state requires O(1) to calculate, giving us a time complexity of O(m * n).
 *
 * Space Complexity: O(m * n)
 */
class MinimalPathSum {
    public static int minPathSum(List<List<Integer>> grid) {
        if(grid.isEmpty()) {
            return 0;
        }

        int[][] dp = new int[grid.size()][grid.get(0).size()];

        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = grid.get(i).get(j);
                }
                else if (i == 0) {
                    dp[i][j] = grid.get(i).get(j) + dp[i][j - 1];
                }
                else if (j == 0) {
                    dp[i][j] = grid.get(i).get(j) + dp[i - 1][j];
                }
                else {
                    dp[i][j] = grid.get(i).get(j) + Math.min(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[dp.length - 1][dp[0].length - 1];
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int gridLength = Integer.parseInt(scanner.nextLine());
        List<List<Integer>> grid = new ArrayList<>();
        for (int i = 0; i < gridLength; i++) {
            grid.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        scanner.close();
        int res = minPathSum(grid);
        System.out.println(res);
    }
}
