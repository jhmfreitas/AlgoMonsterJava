package com.algo.monster.binarysearch;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// A sorted array of unique integers was rotated at an unknown pivot. For example, [10, 20, 30, 40, 50] becomes [30, 40, 50, 10, 20].
// Find the index of the minimum element in this array.
// Time Complexity: O(log(n))
// Space Complexity: O(1)
class FindMinimumInRotatedSortedArray {
    public static int findMinRotated(List<Integer> arr) {
        int left = 0, right = arr.size() - 1 ;
        int boundaryIndex = -1;
        while (left <= right) {
            int middle = left + (right-left)/2;
            if(arr.get(middle) <= arr.get(arr.size()-1)){
                right = middle - 1;
                boundaryIndex = middle;
            }else {
                left = middle + 1;
            }
        }
        return boundaryIndex;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> arr = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        scanner.close();
        int res = findMinRotated(arr);
        System.out.println(res);
    }
}
