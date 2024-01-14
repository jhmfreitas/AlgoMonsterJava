package com.algo.monster.miscellaneous.monotonicstack;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given a circular array (the next element of the last element is the first element of the array),
 * print the Next Greater Number for every element. The Next Greater Number of a number x is the first greater number
 * to its traversing-order next in the array, which means you could search circularly to find its next greater number.
 * If it doesn't exist, output -1 for this number.
 *
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 */
class NextGreaterElement2 {
    public static List<Integer> nextGreaterElements(List<Integer> nums) {
        List<Integer> res = new ArrayList<>(Collections.nCopies(nums.size(), 0));
        Deque<Integer> q = new ArrayDeque<>();
        // First pass to from end to beginning
        for (int i = nums.size() - 1; i >= 0; i--) {
            while (!q.isEmpty() && nums.get(q.getLast()) <= nums.get(i)) {
                q.removeLast();
            }

            if (q.isEmpty()) {
                res.set(i, -1);
            } else {
                res.set(i, nums.get(q.getLast()));
            }

            q.addLast(i);
        }

        // Find nextLargest than last element
        while (!q.isEmpty() && nums.get(q.getLast()) <= nums.get(res.size() - 1)) {
            q.removeLast();
        }

        // We do a second pass only looking to the -1 elems and assign the nextLargest value to them
        int nextLargest = nums.get(q.getLast());
        for (int i = res.size() - 1; i >= 0; i--) {
            if(res.get(i) == -1) {
                if(nums.get(i) < nextLargest) {
                    res.set(i, nextLargest);
                } else {
                    nextLargest = nums.get(i);
                }
            }
        }
        return res;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> nums = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        scanner.close();
        List<Integer> res = nextGreaterElements(nums);
        System.out.println(res.stream().map(String::valueOf).collect(Collectors.joining(" ")));
    }
}