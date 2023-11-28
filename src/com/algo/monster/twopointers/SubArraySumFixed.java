package com.algo.monster.twopointers;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class SubArraySumFixed {
    public static int subarraySumFixed(List<Integer> nums, int k) {
        int windowSum = nums.stream().limit(k).reduce(Integer::sum).orElse(-1);;
        int largestSum = windowSum;

        if (windowSum == -1)
            return -1;

        for (int right = k; right < nums.size(); right++) {
            int left = right - k;
            windowSum = windowSum - nums.get(left);
            windowSum = windowSum + nums.get(right);
            largestSum = Math.max(largestSum, windowSum);
        }
        return largestSum;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> nums = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        int k = Integer.parseInt(scanner.nextLine());
        scanner.close();
        int res = subarraySumFixed(nums, k);
        System.out.println(res);
    }
}
