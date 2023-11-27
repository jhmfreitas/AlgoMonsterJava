package com.algo.monster.twopointers;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Determine whether a string is a palindrome, ignoring non-alphanumeric characters and case. Examples:
 *
 * Input: Do geese see God? Output: True
 *
 * Input: Was it a car or a cat I saw? Output: True
 *
 * Input: A brown fox jumping over Output: False
 *
 * The time complexity is O(N) since we only look at each character at most once, and space complexity is O(1)
 * since we didn't allocate a dynamic amount of memory.
 */
class ValidPalindrome {
    public static Pattern pattern = Pattern.compile("[a-zA-Z0-9]");

    public static boolean isAlphanumeric(String s) {
        return pattern.matcher(s).matches();
    }

    public static boolean isPalindrome(String s) {
        boolean res = true;
        int i = 0;
        int j = s.length() - 1;
        char[] charArray = s.toCharArray();

        while (i < j) {
            String s1 = String.valueOf(charArray[i]);
            if (!isAlphanumeric(s1)) {
                i++;
                continue;
            }

            String s2 = String.valueOf(charArray[j]);
            if (!isAlphanumeric(s2)) {
                j--;
                continue;
            }

            if(s1.equalsIgnoreCase(s2)) {
                i++;
                j--;
            } else {
                res = false;
                break;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        scanner.close();
        boolean res = isPalindrome(s);
        System.out.println(res);
    }
}
