package com.algo.monster.binarysearch;


import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * A mountain array is defined as an array that:
 *
 *  - has at least 3 elements
 *  - has an element with the largest value called "peak", with index k. The array elements strictly increase from the first element to A[k], and then strictly decreases from A[k + 1] to the last element of the array. Thus creating a "mountain" of numbers.
 * That is, given A[0]<...<A[k-1]<A[k]>A[k+1]>...>A[n-1], we need to find the index k. Note that the peak element is neither the first nor the last element of the array.
 *
 * Find the index of the peak element. Assume there is only one peak element.
 */
class PeakOfMountainArray {
    public static int peakOfMountainArray(List<Integer> arr) {
        int left = 0, right = arr.size() - 1 ;
        int boundaryIndex = -1;
        while (left <= right) {
            int middle = left + (right-left)/2;
            if(middle == arr.size() - 1 || arr.get(middle) > arr.get(middle + 1)){
                // Potential peak found, update boundaryIndex and search the left half
                boundaryIndex = middle;
                right = middle - 1;
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
        int res = peakOfMountainArray(arr);
        System.out.println(res);
    }
}