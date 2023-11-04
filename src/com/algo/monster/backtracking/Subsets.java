package com.algo.monster.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Given a set of distinct integers, nums, return all possible subsets (the power set).
 *
 * Note: The solution set must not contain duplicate subsets.
 *
 * For every element in nums, you have two choices: either to include it or exclude it in the current subset. As there are n numbers, there are a total of 2^n subsets. Constructing each subset takes O(n) (in the worst case, you have to add all n numbers). Therefore, the total time and space complexity is O(n * 2^n).
 *
 * Time Complexity: O(n * 2^n)
 *
 * Space Complexity: O(n * 2^n)
 *
 * The stack memory determined by the state-space tree's height is O(n) because we need to process n elements.
 */
class Subsets {
    public static List<List<Integer>> subsets(List<Integer> nums) {
        List<List<Integer>> combinations = new ArrayList<>();
        dfs(nums, new ArrayList<>(), combinations, 0);
        return combinations;
    }

    private static void dfs(List<Integer> nums, ArrayList<Integer> currentPath, List<List<Integer>> combinations, int start) {
        combinations.add(new ArrayList<>(currentPath));

        for (int i = start; i < nums.size(); i++) {
            currentPath.add(nums.get(i));
            dfs(nums, currentPath, combinations, i + 1);
            currentPath.remove(currentPath.size() - 1);
        }
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> nums = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        scanner.close();
        List<List<Integer>> res = subsets(nums);
        List<String> output = new ArrayList<>();
        for (List<Integer> x : res) {
            Collections.sort(x);
            List<String> sNumbers = x.stream().map(
                n -> n.toString()).collect(Collectors.toList()
            );
            output.add(String.join(" ", sNumbers));
        }
        Collections.sort(output);
        for (String row : output) {
            System.out.println(row);
        }
    }
}
