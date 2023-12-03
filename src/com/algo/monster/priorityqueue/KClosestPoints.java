package com.algo.monster.priorityqueue;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given a list of points on a 2D plane. Find k closest points to origin (0, 0).
 *
 * Input: [(1, 1), (2, 2), (3, 3)], 1
 *
 * Output: [(1, 1)]
 *
 * Time Complexity: O(n*log(n))
 *
 * We add every element into the heap. Heap insertion is O(log(n)) which we do n times.
 *
 * Space Complexity: O(n)
 * 
 */
class KClosestPoints {
    public static List<List<Integer>> kClosestPoints(List<List<Integer>> points, int k) {
        Comparator<List<Integer>> distanceComparator = new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> p1, List<Integer> p2) {
                return distanceToOrigin(p1) - distanceToOrigin(p2);
            }
            private int distanceToOrigin(List<Integer> p) {
                return (int) (Math.pow(p.get(0), 2) + Math.pow(p.get(1), 2));
            }
        };

        PriorityQueue<List<Integer>> priorityQueue = new PriorityQueue<>(points.size(), distanceComparator);
        priorityQueue.addAll(points);
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            list.add(priorityQueue.poll());
        }
        return list;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int pointsLength = Integer.parseInt(scanner.nextLine());
        List<List<Integer>> points = new ArrayList<>();
        for (int i = 0; i < pointsLength; i++) {
            points.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        int k = Integer.parseInt(scanner.nextLine());
        scanner.close();
        List<List<Integer>> res = kClosestPoints(points, k);
        for (List<Integer> row : res) {
            System.out.println(row.stream().map(String::valueOf).collect(Collectors.joining(" ")));
        }
    }
}
