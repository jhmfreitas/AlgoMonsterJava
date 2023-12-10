package com.algo.monster.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Given a binary matrix, find out the largest size square sub-matrix with all 1's and return its area.
 *
 * Time Complexity: O(r*c)
 * Space Complexity: O(r*c)
 */
class MaximalSquare {
    public static int maximalSquare(List<List<Integer>> matrix) {
        if(matrix == null || matrix.isEmpty()) {
            return 0;
        }

        int best = 0;
        int[][] dp = new int[matrix.size()][matrix.get(0).size()];
        for (int i = 0; i < matrix.size();  i++) {
            dp[i][0] = matrix.get(i).get(0);
            best = Math.max(dp[i][0], best);
        }
        for (int i = 0; i < matrix.get(0).size();  i++) {
            dp[0][i] = matrix.get(0).get(i);
            best = Math.max(dp[0][i], best);
        }

        for (int r = 1; r < matrix.size(); r++) {
            for (int c = 1; c < matrix.get(0).size(); c++) {
                if (matrix.get(r).get(c) == 1) {
                    int min = Math.min(dp[r - 1][c], dp[r][c - 1]);
                    min = Math.min(min, dp[r - 1][c - 1]);
                    dp[r][c] = min + 1;

                    best = Math.max(best, min + 1);
                } else {
                    dp[r][c] = 0;
                }
            }
        }

        return best * best;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int matrixLength = Integer.parseInt(scanner.nextLine());
        List<List<Integer>> matrix = new ArrayList<>();
        for (int i = 0; i < matrixLength; i++) {
            matrix.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        scanner.close();
        int res = maximalSquare(matrix);
        System.out.println(res);
    }
}
