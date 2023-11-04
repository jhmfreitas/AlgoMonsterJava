package com.algo.monster.backtracking;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given an array of distinct integers candidates and a target integer target, return a list of all unique combinations of candidates where the chosen numbers sum to target. You may return the combinations in any order.
 *
 * The same number may be chosen from candidates an unlimited number of times. Two combinations are unique if the frequency of at least one of the chosen numbers is different.
 *
 * It is guaranteed that the number of unique combinations that sum up to target is less than 150 combinations for the given input.
 *
 * Time Complexity: O(n^target/min(candidates)). In the worse case, the state-space tree has n branches and the depth of the tree is at most target divided by the smallest number in candidates.
 *
 * Every number can be used or not used therefore leading to exponential time complexity.
 *
 * Space Complexity: O(target/min(candidates)) as each candidate combination is at most length target/min(candidates)` corresponding to the maximum height of the state-space tree.
 */
class CombinationSum {
    public static List<List<Integer>> combinationSum(List<Integer> candidates, int target) {
        List<List<Integer>> combinations = new ArrayList<>();
        Collections.sort(candidates);
        dfs(candidates, target, combinations, 0, new ArrayList<>(), 0);
        return combinations;
    }

    private static void dfs(List<Integer> candidates, int result, List<List<Integer>> combinations, Integer currentSum, List<Integer> currentPath, int start) {
        if(currentSum == result) {
            combinations.add(new ArrayList<>(currentPath));
            return;
        }

        if(currentSum < result) {
            for (int i = start; i < candidates.size(); i++) {
                currentSum += candidates.get(i);
                if(currentSum > result) {
                    break;
                }

                currentPath.add(candidates.get(i));
                dfs(candidates, result, combinations, currentSum, currentPath, i);
                currentPath.remove(candidates.get(i));
                currentSum -= candidates.get(i);
            }
        }
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> candidates = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        int target = Integer.parseInt(scanner.nextLine());
        scanner.close();
        List<List<Integer>> res = combinationSum(candidates, target);
        for (List<Integer> lst : res) { lst.sort(Comparator.naturalOrder()); }
        res.sort((l1, l2) -> {
            for (int i = 0; i < Math.min(l1.size(), l2.size()); i++) {
                if (l1.get(i) != l2.get(i)) {return l1.get(i) - l2.get(i);}
            }
            return l1.size() - l2.size();
        });

        for (List<Integer> lst : res) {
            System.out.println(lst.stream().map(String::valueOf).collect(Collectors.joining(" ")));
        }
    }
}
