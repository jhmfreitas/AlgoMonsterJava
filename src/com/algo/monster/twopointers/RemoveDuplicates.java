package com.algo.monster.twopointers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * Given a sorted list of numbers, remove duplicates and return the new length. You must do this in-place and without using extra memory.
 *
 * Input: [0, 0, 1, 1, 1, 2, 2].
 *
 * Output: 3.
 *
 * Your function should modify the list in place so the first 3 elements becomes 0, 1, 2. Return 3 because the new length is 3.
 *
 * Time Complexity: O(n)
 *
 * We simply traverse the array once moving from left to right.
 *
 * Space Complexity: O(1)
 *
 *
 */
class RemoveDuplicates {
    public static int removeDuplicates(List<Integer> arr) {
        int count = 0;
        for (int j=1; j < arr.size(); j++ ) {
            if (!Objects.equals(arr.get(count), arr.get(j))){
                count++;
                arr.set(count, arr.get(j));
            }
        }
        return count + 1;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> arr = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        scanner.close();
        int res = removeDuplicates(arr);
        System.out.println(arr.stream().limit(res).map(String::valueOf).collect(Collectors.joining(" ")));
    }
}
