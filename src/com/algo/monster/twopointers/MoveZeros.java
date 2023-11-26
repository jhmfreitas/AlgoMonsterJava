package com.algo.monster.twopointers;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * Given an array of integers, move all the 0s to the back of the array while maintaining the relative order of the
 * non-zero elements. Do this in-place using constant auxiliary space.
 *
 * Input:
 *
 * [1, 0, 2, 0, 0, 7]
 * Output:
 *
 * [1, 2, 7, 0, 0, 0]
 *
 * Time Complexity: O(n)
 *
 * Space Complexity: O(1)
 */
class MoveZeros {
    public static void moveZeros(List<Integer> nums) {
        for (int i = 0, j = 0; j <nums.size(); j++) {
            Integer iNumber = nums.get(i);
            if (nums.get(j) != 0) {
                nums.set(i, nums.get(j));
                nums.set(j, iNumber);
                i++;
            }
        }
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> nums = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        scanner.close();
        moveZeros(nums);
        System.out.println(nums.stream().map(String::valueOf).collect(Collectors.joining(" ")));
    }
}
