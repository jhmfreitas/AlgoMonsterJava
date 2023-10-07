package com.algo.monster.binarysearch;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class FirstElementNotSmallerThanTarget {

    // In binary search problems the template is always the same, what varies is the feasible function
    // The feasible function here is arr.get(middle) >= target
    // These problems seem to be represented by a monotonic function, i.e. once an element is 2
    // the right elements to that one have to be 2 or greater
    // Time Complexity: O(log(n))
    // Space Complexity: O(1)
    public static int firstNotSmaller(List<Integer> arr, int target) {
        int start = 0, end = arr.size() - 1;
        int boundaryIndex = -1;
        while (start <= end){
            int middle = start + (end - start)/2;
            if(arr.get(middle) >= target){
                boundaryIndex = middle;
                end = middle - 1;
            }else{
                start = middle + 1;
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
        int res = firstNotSmaller(arr, target);
        System.out.println(res);
    }
}
