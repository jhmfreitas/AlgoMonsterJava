package com.algo.monster.backtracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class ValidParenthesisCombinationsNoAdditionalStates {
    public static List<String> generateParentheses(int n) {
        ArrayList<String> result = new ArrayList<>();
        parenthesisDfs(n, new ArrayList<>(), result);
        return result;
    }

    public static void parenthesisDfs(int n, ArrayList<Character> currentCombination, List<String> result) {
        if(currentCombination.size() == 2*n){
            result.add(currentCombination.stream().map(String::valueOf).collect(Collectors.joining()));
            return;
        }

        List<Character> possibleStates = new ArrayList<>();
        possibleStates.add('(');
        possibleStates.add(')');
        for (Character p : possibleStates) {
            ArrayList<Character> possibleCombination = new ArrayList<>(currentCombination);
            possibleCombination.add(p);
            if(isValidCombination(n, possibleCombination)) {
                currentCombination.add(p);
                parenthesisDfs(n, currentCombination, result);
                currentCombination.remove(currentCombination.size() -1);
            }
        }
    }

    private static boolean isValidCombination(int n, ArrayList<Character> possibleCombination) {
        long nOfOpenParenthesis = possibleCombination.stream().filter(ch -> ch =='(').count();
        long nOfCloseParenthesis = possibleCombination.stream().filter(ch -> ch == ')').count();
        return possibleCombination.size() <= 2 * n && nOfOpenParenthesis <= n && nOfCloseParenthesis <= nOfOpenParenthesis;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        scanner.close();
        List<String> res = generateParentheses(n);
        Collections.sort(res);
        for (String line : res) {
            System.out.println(line);
        }
    }
}
