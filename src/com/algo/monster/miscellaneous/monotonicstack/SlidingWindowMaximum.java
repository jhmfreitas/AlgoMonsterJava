package com.algo.monster.miscellaneous.monotonicstack;

import java.util.*;
import java.util.stream.Collectors;

/**
 * We have an array and a sliding window defined by a start index and an end index.
 * The sliding window moves from left of the array to right. There are always k elements in the window.
 * The window moves one position at a time. Find the maximum integer within the window each time it moves.
 */
class SlidingWindowMaximum {
    // My solution
    public static List<Integer> slidingWindowMaximum2(List<Integer> nums, int k) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i != (nums.size() - k); i++) {
            ArrayList<Integer> q = new ArrayList<>();
            for (int j = i; j < i+k; j++) {
                Integer entry = nums.get(j);
                while (!q.isEmpty() && q.get(q.size() - 1) <= entry) {
                    q.remove(q.size() - 1);
                }
                q.add(entry);
            }
            result.add(q.get(0));
        }
        return result;
    }

    /**
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     * @param nums
     * @param k
     * @return
     */
    public static List<Integer> slidingWindowMaximum(List<Integer> nums, int k) {
        ArrayDeque<Integer> q = new ArrayDeque<>(); // stores indexes
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < nums.size(); i++) {
            while (!q.isEmpty() && nums.get(q.getLast()) <= nums.get(i)) {
                q.removeLast();
            }
            q.addLast(i);

            // remove first element if it's outside the window
            if (q.getFirst() == i - k) {
                q.removeFirst();
            }

            // if window has k elements add to results (first k-1 windows have < k elements because we start from empty window and add 1 element each iteration)
            if (i >= k - 1) {
                result.add(nums.get(q.getFirst()));
            }
        }
        return result;
    }


    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> nums = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        int k = Integer.parseInt(scanner.nextLine());
        scanner.close();
        List<Integer> res = slidingWindowMaximum(nums, k);
        System.out.println(res.stream().map(String::valueOf).collect(Collectors.joining(" ")));
    }
}
