package com.algo.monster.priorityqueue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Given a stream of numbers, find the median number at any given time (accurate to 1 decimal place).
 *
 * Example:
 *
 * add_number(1)
 * add_number(2)
 * add_number(3)
 * get_median() == 2.0
 * add_number(4)
 * get_median() == 2.5
 *
 * Time Complexity: O(qlog(q))
 *
 * Here we let q denote the number of queries so worst case its log(q) insertion for a given number.
 *
 * Space Complexity: O(n)
 *
 */
class MedianOfDataStream {
    public static class MedianOfStream {
        PriorityQueue<Double> minHeap = new PriorityQueue<>();
        PriorityQueue<Double> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

        public void addNum(int num) {
            if (minHeap.isEmpty() || num < minHeap.peek()) {
                maxHeap.add((double) num);
            } else {
                minHeap.add((double) num);
            }
            balance();
        }

        private void balance() {
            if(maxHeap.size() < minHeap.size()) {
                maxHeap.add(minHeap.poll());
            }

            if(maxHeap.size() > minHeap.size() + 1) {
                minHeap.add(maxHeap.poll());
            }
        }

        public double getMedian() {
            return minHeap.size() == maxHeap.size() ? (minHeap.peek() + maxHeap.peek())/2.0 : maxHeap.peek();
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numInputs = Integer.parseInt(scanner.nextLine());
        MedianOfStream medianOfStream = new MedianOfStream();
        for (int i = 0; i < numInputs; ++i) {
            String input = scanner.nextLine();
            if (input.equals("get")) {
                System.out.println(medianOfStream.getMedian());
            } else {
                medianOfStream.addNum(Integer.parseInt(input));
            }
        }
    }
}
