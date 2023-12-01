package com.algo.monster.twopointers;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Find the total number of subarrays that sums up to target.
 */
class PrefixSum2 {
    // Since the new problem does not ask for index but total number instead,
    // we can change our hashmap to "sum k: number of prefix sums that sums up to k".
    public static int subarraySumTotal(List<Integer> arr, int target) {
        Map<Integer, Integer> prefixSums = new HashMap<>();
        prefixSums.put(0,1);
        int sum = 0;
        int count = 0;
        for (int i = 0; i < arr.size(); i++) {
            sum += arr.get(i);
            int complement = sum - target;
            if(prefixSums.containsKey(complement)){
                count+= prefixSums.get(complement);
            }
            if (prefixSums.containsKey(sum)) {
                prefixSums.replace(sum, prefixSums.get(sum) + 1);
            } else {
                prefixSums.put(sum, 1);
            }
        }
        return count;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> arr = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        int target = Integer.parseInt(scanner.nextLine());
        scanner.close();
        int res = subarraySumTotal(arr, target);
        System.out.println(res);
    }
}