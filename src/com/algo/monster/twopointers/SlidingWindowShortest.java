package com.algo.monster.twopointers;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Let's continue on finding the sum of subarrays. This time given a positive integer array nums, we want to find the
 * length of the shortest subarray such that the subarray sum is at least target.
 * Recall the same example with input nums = [1, 4, 1, 7, 3, 0, 2, 5] and target = 10, then the smallest window with the
 * sum >= 10 is [7, 3] with length 2. So the output is 2.
 *
 * We'll assume for this problem that it's guaranteed target will not exceed the sum of all elements in nums.
 *
 */
class SlidingWindowShortest {
    public static int subarraySumShortest(List<Integer> nums, int target) {
        int shortest = nums.size();
        int windowSum = 0;
        int right = 0;
        for (int left = right; right < nums.size(); right++) {
            windowSum += nums.get(right);
            while (windowSum >= target) {
                left--;
                windowSum -= nums.get(left);
                shortest = Math.min(shortest, right - left + 1);
            }
        }
        return shortest;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> nums = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        int target = Integer.parseInt(scanner.nextLine());
        scanner.close();
        int res = subarraySumShortest(nums, target);
        System.out.println(res);
    }
}
