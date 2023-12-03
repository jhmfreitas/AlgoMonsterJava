package com.algo.monster.priorityqueue;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Find the kth largest element in an unsorted array. Note that it is the kth largest element in the sorted order, not necessarily the kth distinct element.
 *
 * Example 1:
 * Input: [3,2,1,5,6,4] and k = 2
 * Output: 5
 * Example 2:
 * Input: [3,2,3,1,2,4,5,5,6] and k = 4
 * Output: 4
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ array's length.
 *
 * Sorting the array before indexing gives us a O(nlog(n)) solution. We can do better with a max heap.
 * Heapify is O(n) and popping the first k element is O(klog(n)) so combined is O(n + klog(n)).
 *
 */
class KLargestElementInArray {
    public static int findKthLargest(List<Integer> nums, int k) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(nums.size(), Comparator.reverseOrder());
        priorityQueue.addAll(nums);
        int count = k;
        Integer e = 0;
        while (count != 0) {
            e = priorityQueue.poll();
            count--;
        }
        return e;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> nums = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        int k = Integer.parseInt(scanner.nextLine());
        scanner.close();
        int res = findKthLargest(nums, k);
        System.out.println(res);
    }
}
