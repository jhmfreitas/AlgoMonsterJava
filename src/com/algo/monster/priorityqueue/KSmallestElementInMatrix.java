package com.algo.monster.priorityqueue;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given a n x n matrix where each of the rows and columns are sorted in ascending order,
 * find the kth smallest element in the matrix.
 *
 * Note that it is the kth smallest element in the sorted order, not the kth distinct element.
 *
 * Example:
 * Input:
 * matrix = [
 *   [ 1,  5,  9],
 *   [10, 11, 13],
 *   [12, 13, 15]
 * ],
 * k = 8,
 * Output: 13
 *
 * The time complexity is O(N + K log(N)) since it takes O(N) to process the first row and each of the k iterations take
 * O(log(N)) to process due to the use of the min heap.
 *
 * The space complexity is O(n) as the heap always stores one element for each row (until it's empty).
 *
 */
class KSmallestElementInMatrix {
    public static int kthSmallest(List<List<Integer>> matrix, int k) {
        int n = matrix.size();
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(
                (a, b) -> Integer.compare(matrix.get(a[0]).get(a[1]), matrix.get(b[0]).get(b[1]))
        );
        priorityQueue.add(new int[] {0,0});
        // Keeps track of the top of each row that is not processed
        int[] columnTop = new int[n];
        // Keeps track of the first number each row not processed
        int[] rowFirst = new int[n];
        while (k > 1) {
            k--;
            int[] e = priorityQueue.poll();
            int row = e[0], column = e[1];
            rowFirst[row] = column + 1;
            // Add the item on the right to the heap if everything above it is processed
            if(column + 1 < n && columnTop[column + 1] == row) {
                priorityQueue.add(new int[] {row, column + 1});
            }
            columnTop[column] = row + 1;
            // Add the item below it to the heap if everything before it is processed
            if(row + 1 < n && rowFirst[row + 1] == column) {
                priorityQueue.add(new int[] {row + 1, column});
            }

        }
        int[] kElement = priorityQueue.poll();
        return matrix.get(kElement[0]).get(kElement[1]);
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int matrixLength = Integer.parseInt(scanner.nextLine());
        List<List<Integer>> matrix = new ArrayList<>();
        for (int i = 0; i < matrixLength; i++) {
            matrix.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        int k = Integer.parseInt(scanner.nextLine());
        scanner.close();
        int res = kthSmallest(matrix, k);
        System.out.println(res);
    }
}
