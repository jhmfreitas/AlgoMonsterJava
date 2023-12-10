package com.algo.monster.dynamicprogramming;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The problem is to find the minimum path sum from top to bottom if given a triangle.
 * Each step you may move to adjacent numbers on the row below.
 *
 * Space Complexity: O(n^2)
 * Time Complexity: O(n^2)
 */
class Triangle {
    public static int minimumTotal(List<List<Integer>> triangle) {
        if(triangle.isEmpty()) {
            return 0;
        }

        int[][] dp = new int[triangle.size()][triangle.get(triangle.size() - 1).size()];

        for (int level = 0; level < triangle.size(); level++) {
            for (int element = 0; element < triangle.get(level).size(); element++) {
                if (level == 0) {
                    dp[level][element] = triangle.get(level).get(element);
                } else if (element == 0) {
                    dp[level][element] = triangle.get(level).get(element) + dp[level - 1][element];
                } else if (element == triangle.get(level).size() - 1) {
                    dp[level][element] = triangle.get(level).get(element) + dp[level - 1][element - 1];
                } else {
                    dp[level][element] = triangle.get(level).get(element) + Math.min(dp[level - 1][element - 1], dp[level - 1][element]);
                }
            }
        }
        return findMinElement(dp[triangle.size() - 1]);
    }

    public static int findMinElement(int[] arr) {
        // Check if the array is not empty
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array is empty or null");
        }

        // Initialize min with the first element of the array
        int min = arr[0];

        // Iterate through the array to find the minimum element
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }

        return min;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int triangleLength = Integer.parseInt(scanner.nextLine());
        List<List<Integer>> triangle = new ArrayList<>();
        for (int i = 0; i < triangleLength; i++) {
            triangle.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        scanner.close();
        int res = minimumTotal(triangle);
        System.out.println(res);
    }
}
