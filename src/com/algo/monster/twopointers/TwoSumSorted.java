package com.algo.monster.twopointers;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * Given an array of integers sorted in ascending order, find two numbers that add up to a given target. Return the indices
 * of the two numbers in ascending order. You can assume elements in the array are unique and there is only one solution. Do this in O(n) time and with constant auxiliary space.
 *
 * Input:
 *
 * arr: a sorted integer array
 * target: the target sum we want to reach
 * Sample Input: [2 3 4 5 8 11 18], 8
 *
 * Sample Output: 1 3
 *
 * Time Complexity
 * The array is already sorted, and the two points never meet so each element is visited at most once so the time complexity is O(n).
 *
 * Space Complexity: O(1)
 */
class TwoSumSorted {
    public static List<Integer> twoSumSorted(List<Integer> arr, int target) {
        int i = 0;
        int j = arr.size() - 1;
        int sum = 0;

        while (sum != target) {
            sum = arr.get(i) + arr.get(j);
            if(sum == target){
                return List.of(i, j);
            } else if(sum < target) {
                i++;
            } else {
                j--;
            }
        }

        return null;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> arr = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        int target = Integer.parseInt(scanner.nextLine());
        scanner.close();
        List<Integer> res = twoSumSorted(arr, target);
        System.out.println(res.stream().map(String::valueOf).collect(Collectors.joining(" ")));
    }
}
