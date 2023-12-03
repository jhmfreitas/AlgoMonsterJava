package com.algo.monster.twopointers;

import java.util.*;
import java.util.stream.Collectors;

/**
 * You are given two sorted arrays of distinct integers, arr1, and arr2. Your goal is to start from the beginning of one
 * array, and arrive to the end of one array (it could be the same array or not).
 *
 * For each step, you can either move forward a step on an array, or move to a square in the other array where the number
 * is the same as the number in your current square ("teleport"). Your total score is defined as the sum of all unique numbers that you have been on.
 *
 * Find the maximum score that you can get given the above rules. Since the result might be very large and cause overflow,
 * return the maximum score modded by 10^9 + 7.
 *
 *  The time complexity is O(n+m), where n and m are the size of the arrays, respectively.
 *
 * Space Complexity: O(1)
 *
 */
class TeleporterArrays {
    public static int maximumScore(List<Integer> arr1, List<Integer> arr2) {
        int ptr1 = 0;
        int ptr2 = 0;
        long sum = 0;
        long sectionSum1 = 0, sectionSum2 = 0;
        int n1 = arr1.size(), n2 = arr2.size();

        while(ptr1 < n1 || ptr2 < n2) {
            if (ptr1 < n1 && ptr2 < n2 && arr1.get(ptr1).equals(arr2.get(ptr2))) {
                sum += Math.max(sectionSum1, sectionSum2) + arr1.get(ptr1);
                sum = (long) (sum % ( Math.pow(10,9) + 7));
                sectionSum1 = 0;
                sectionSum2 = 0;
                ptr1++;
                ptr2++;
                continue;
            }
            // If either "ptr1" reaches the end, or "ptr2" is smaller than "ptr1"
            // we move "ptr2" and keep track of the sum.
            if (ptr1 == n1 || (ptr2 != n2 && arr1.get(ptr1) > arr2.get(ptr2))) {
                sectionSum2 += arr2.get(ptr2);
                ptr2++;
            } else {
                // Otherwise, we move "ptr1" and keep track of that sum
                sectionSum1 += arr1.get(ptr1);
                ptr1++;
            }
        }

        sum += Math.max(sectionSum1, sectionSum2);
        return (int) (sum % ( Math.pow(10,9) + 7));
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> arr1 = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> arr2 = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        scanner.close();
        int res = maximumScore(arr1, arr2);
        System.out.println(res);
    }
}