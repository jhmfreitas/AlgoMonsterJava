package com.algo.monster.priorityqueue;

import java.util.*;

/**
 * Write a program to find the n-th ugly number.
 *
 * Ugly numbers are positive numbers whose prime factors only include 2, 3, 5.
 *
 * Example 1:
 * Input:n = 10
 * Output: 12
 * Explanation: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 is the sequence of the first 10 ugly numbers.
 *
 * Space Complexity: O(n) as there are at most 3n elements in the heap.
 *
 * Time Complexity: O(n * log(n))
 *
 * We build up the ugly numbers by popping the smallest and insert its multiples. Since the inserted values are most
 * likely larger than the existing ugly numbers, the amortized insertion time is O(1).
 * Each deletion time is O(log(n)), so the total deletion time is O(nlog(n)).
 * Overall, the amortized time complexity is O(nlog(n)).
 *
 */
class UglyNumber {
    public static final int[] allowedPrime = {2, 3, 5};
    public static int nthUglyNumber(int n) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(1);
        HashSet<Integer> usedNums = new HashSet<>();
        usedNums.add(1);

        for (int i = 0;  i < n-1; i++) {
            Integer nextNum = priorityQueue.poll();
            for (int j = 0; j < allowedPrime.length; j++) {
                int number = nextNum * allowedPrime[j];
                if (!usedNums.contains(number)) {
                    priorityQueue.add(number);
                    usedNums.add(number);
                }
            }
        }

        return priorityQueue.poll();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        scanner.close();
        int res = nthUglyNumber(n);
        System.out.println(res);
    }
}
