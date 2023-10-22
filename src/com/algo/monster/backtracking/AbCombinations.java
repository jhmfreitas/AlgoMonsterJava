package com.algo.monster.backtracking;

import java.util.*;

/**
 *
 * Given a non-negative integer n, find all n-letter words composed by 'a' and 'b', return them in a list of strings in lexicographical order.
 *
 * Time complexity
 * For each node there are a maximum of 2 children. The height of the tree is n. The number of nodes is 2^n-1 or O(2^n), (see the "perfect binary tree" section of Everything about trees for a quick review). It takes O(n) to join the n characters at each leaf node. So the overall time complexity is O(2^n*n).
 *
 * Space complexity
 * We store 2^n strings in total, each with a length of n so this gives us O(2^n*n) space. In addition, our recursion depth is O(n). Adding the two together, we still get a space complexity of O(2^n*n).
 */
class AbCombinations {
    public static List<String> letterCombination(int n) {
        ArrayList<String> result = new ArrayList<>();
        backtrackingTemplate(n, 0, new ArrayList<>(), result);
        return result;
    }

    public static void backtrackingTemplate(int n, int currentHeight,  ArrayList<String> currentPath, ArrayList<String> result) {
        if(currentHeight == n) {
            result.add(String.join("", currentPath));
            return;
        }

        for (String character : new ArrayList<>(Arrays.asList("a", "b"))) {
            currentPath.add(character);
            backtrackingTemplate(n, currentHeight+1, currentPath, result);
            currentPath.remove(currentPath.size() - 1);
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        scanner.close();
        List<String> res = letterCombination(n);
        Collections.sort(res);
        for (String line : res) {
            System.out.println(line);
        }
    }
}
