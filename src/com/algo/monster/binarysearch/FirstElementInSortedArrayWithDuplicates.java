package com.algo.monster.binarysearch;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// Given a sorted array of integers and a target integer, find the first occurrence of the target and return its index.
// Return -1 if the target is not in the array
// Time Complexity: O(log(n))
// Space Complexity: O(1)
class FirstElementInSortedArrayWithDuplicates {
    public static int findFirstOccurrence(List<Integer> arr, int target) {
        int left = 0, right = arr.size() - 1 ;
        int boundaryIndex= -1;
        while (left <= right){
            int middle = left + (right - left)/2;
            if(arr.get(middle) == target){
                boundaryIndex = middle;
                right = middle - 1;
            } else if(arr.get(middle) < target){
                left = middle + 1;
            } else{
                right = middle - 1;
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
        int target = Integer.parseInt(scanner.nextLine());
        scanner.close();
        int res = findFirstOccurrence(arr, target);
        System.out.println(res);
    }
}
