package com.algo.monster.twopointers;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * Given an array of integers and an integer target, find a subarray that sums to target and return the start and end indices of the subarray.
 *
 * Input: arr: 1 -20 -3 30 5 4 target: 7
 *
 * Output: 1 4
 *
 * Explanation: -20 - 3 + 30 = 7. The indices for subarray [-20,-3,30] is 1 and 4 (right exclusive).
 *
 * Time Complexity: O(n)
 *
 * Space Complexity: O(n)
 */
class PrefixSum {
    public static List<Integer> subarraySum(List<Integer> arr, int target) {
        Map<Integer, Integer> prefixSums = new HashMap<>();
        prefixSums.put(0,0);
        int sum = 0;
        for (int i = 0; i < arr.size(); i++) {
            sum += arr.get(i);
            int complement = sum - target;
            if(prefixSums.containsKey(complement)){
                return List.of(prefixSums.get(complement), i+1);
            }
            prefixSums.put(sum, i+1);
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
        List<Integer> res = subarraySum(arr, target);
        System.out.println(res.stream().map(String::valueOf).collect(Collectors.joining(" ")));
    }
}
