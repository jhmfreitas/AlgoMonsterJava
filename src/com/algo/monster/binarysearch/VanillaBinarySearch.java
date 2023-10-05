package com.algo.monster.binarysearch;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/*
  Time Complexity : O(log(N))
  Binary search can be used whenever we make a binary decision to shrink the search range
 */
class VanillaBinarySearch {
    public static int binarySearch(List<Integer> arr, int target) {
        for (int start = 0,end = arr.size()-1, middle = (end - start)/2; start <= end; middle = start + (end - start)/2){
            Integer value = arr.get(middle);
            if(value == target){
                return middle;
            }else if(value < target){
                // discard left half plus middle element
                start = middle + 1;
            }else{
                // discard right half plus middle element
                end = middle - 1;
            }
        }
        return -1;
    }

    public static int binarySearchWithWhile(List<Integer> arr, int target) {
        int start = 0;
        int end = arr.size() - 1;

        // Equality is essential for cases with one element arrays
        while (start <= end){
            int middle = start  + (end - start)/2;
            int value = arr.get(middle);
            if(value == target)
                return middle;

            if(value < target){
                start = middle + 1;
            } else {
                end = middle - 1;
            }
        }
        return -1;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> arr = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        int target = Integer.parseInt(scanner.nextLine());
        scanner.close();
        int res = binarySearch(arr, target);
        System.out.println(res);
    }
}
