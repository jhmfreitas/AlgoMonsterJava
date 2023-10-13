package com.algo.monster.binarysearch;

import java.util.Scanner;

//Given an integer, find its square root without using the built-in square root function.
// Only return the integer part (truncate the decimals).
// Time Complexity: O(log(n))
// Space Complexity: O(1)
class SquareRootEstimation {
    public static int squareRoot(int n) {
        if (n == 0) return 0;
        int left = 1, right = n ;
        int boundaryIndex= -1;
        while (left <= right){
            int middle = left + (right - left)/2;
            if(middle == n/middle) {
                return  middle;
            } else if ( middle > n/middle) {
                boundaryIndex = middle;
                right = middle - 1;
            } else{
                left = middle + 1;
            }
        }
        return boundaryIndex-1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        scanner.close();
        int res = squareRoot(n);
        System.out.println(res);
    }
}
