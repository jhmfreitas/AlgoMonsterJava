package com.algo.monster.twopointers;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * What if we dont need the largest sum among all subarrays of fixed size k, but instead, we want to find the length of
 * the longest subarray with sum smaller than or equal to a target?
 *
 * Given input nums = [1, 6, 3, 1, 2, 4, 5] and target = 10, then the longest subarray that does not exceed 10 is
 * [3, 1, 2, 4], so the output is 4 (length of [3, 1, 2, 4]).
 *
 * Time complexity: O(N)
 * Space Complexity: O(1)
 */
class FlexibleSizeSlidingWindowLongest {
    public static int subarraySumLongest(List<Integer> nums, int target) {
        int largestWindowSize = 0;
        int windowSum = 0;
        for (int i = 0, j=0; j < nums.size(); j++) {
            windowSum = windowSum + nums.get(j);
            while (windowSum > target) {
                windowSum = windowSum - nums.get(i);
                i++;
            }
            largestWindowSize = Math.max(largestWindowSize, j-i+1);
        }
        return largestWindowSize;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> nums = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        int target = Integer.parseInt(scanner.nextLine());
        scanner.close();
        int res = subarraySumLongest(nums, target);
        System.out.println(res);
    }
}
