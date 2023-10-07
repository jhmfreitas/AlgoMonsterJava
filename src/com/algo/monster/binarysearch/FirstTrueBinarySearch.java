package com.algo.monster.binarysearch;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class FirstTrueBinarySearch {

    // AlgoMonster implementation
    // Time complexity: O(log(n))
    // Space Complexity: O(1)
    public static int findBoundaryAlgo(List<Boolean> arr) {
        int left = 0;
        int right = arr.size() - 1;
        int boundaryIndex = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr.get(mid)) {
                boundaryIndex = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return boundaryIndex;
    }

    // My implementation
    // Time complexity: O(log(n))
    // Space Complexity: O(1)
    public static int findBoundary(List<Boolean> arr) {
        int start = 0, end = arr.size() - 1;
        while (start <= end) {
            int middle = start + (end - start)/2;
            if(arr.get(middle)){
                int previous = middle > 0 ? middle - 1 : middle;
                if(arr.get(previous) && previous != 0){
                    end = middle - 1;
                }else {
                    return middle;
                }
            }else {
                start = middle + 1;
            }
        }
        return -1;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Boolean> arr = splitWords(scanner.nextLine()).stream().map(v -> v.equals("true")).collect(Collectors.toList());
        scanner.close();
        int res = findBoundary(arr);
        System.out.println(res);
    }
}
