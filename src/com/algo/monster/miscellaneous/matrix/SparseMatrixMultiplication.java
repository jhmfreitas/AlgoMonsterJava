package com.algo.monster.miscellaneous.matrix;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given two sparse integer matrices A and B. Return the result of AB.
 *
 * Review the rules of multiple two matrices here
 *
 * Below is a graphical demonstration of matrix multiplication:
 *
 * A "sparse" matrix is a matrix where most entries are zero.
 * You may assume that the number of columns in A is equal to that of the number of rows in B.
 *
 * Space Complexity: O(A_row * B_col) where A_row is the number of rows in A, and B_col is the number of columns in B.
 *
 * Time Complexity: O(A * B), size of A times size of B
 */
class SparseMatrixMultiplication {
    public static List<List<Integer>> multiplyMatrix(List<List<Integer>> a, List<List<Integer>> b) {
        List<List<Integer>> res = new ArrayList<>();
        int n = a.size();
        int m = b.size();
        int k = b.get(0).size();

        for (int i = 0; i < n; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int j = 0; j < k; j++) {
                list.add(0);
            }
            res.add(list);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Integer aEntry = a.get(i).get(j);
                // The matrixes are sparse so we can save a bit of time with this check
                if (aEntry != 0) {
                    for (int l = 0; l < k; l++) {
                        Integer bEntry = b.get(j).get(l);
                        res.get(i).set(l, res.get(i).get(l) + aEntry * bEntry);
                    }
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
        int aLength = Integer.parseInt(scanner.nextLine());
        List<List<Integer>> a = new ArrayList<>();
        for (int i = 0; i < aLength; i++) {
            a.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        int bLength = Integer.parseInt(scanner.nextLine());
        List<List<Integer>> b = new ArrayList<>();
        for (int i = 0; i < bLength; i++) {
            b.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        scanner.close();
        List<List<Integer>> res = multiplyMatrix(a, b);
        for (List<Integer> row : res) {
            System.out.println(row.stream().map(String::valueOf).collect(Collectors.joining(" ")));
        }
    }
}
