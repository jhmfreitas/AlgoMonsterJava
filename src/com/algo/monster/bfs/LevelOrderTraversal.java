package com.algo.monster.bfs;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Given a binary tree, return its level order traversal.
 * The input is the root node of the tree. The output should be a list of lists of integers, with the ith list containing the values of nodes on level i, from left to right.
 *
 * Time Complexity: O(n)
 * We traverse every edge and node once but since the number of edges is n - 1, this simply becomes O(n).
 *
 * Space Complexity: O(n)
 * There are at most O(n) nodes in the queue.
 */
class LevelOrderTraversal {
    public static class Node<T> {
        public T val;
        public Node<T> left;
        public Node<T> right;

        public Node(T val) {
            this(val, null, null);
        }

        public Node(T val, Node<T> left, Node<T> right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static List<List<Integer>> levelOrderTraversal(Node<Integer> root) {
        List<List<Integer>> result = new ArrayList<>();
        if(root != null) {
            ArrayDeque<Node<Integer>> queue = new ArrayDeque<>();
            queue.add(root);
            bfs(queue, result);
        }
        return result;
    }

    private static void bfs(ArrayDeque<Node<Integer>> queue, List<List<Integer>> result) {
        while (!queue.isEmpty()) {
            List<Integer> newLevel = new ArrayList<>();
            int n = queue.size();
            for (int i = 0; i < n; i++){
                Node<Integer> node = queue.pop();
                newLevel.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            result.add(newLevel);
        }
    }


    // this function builds a tree from input; you don't have to modify it
    // learn more about how trees are encoded in https://algo.monster/problems/serializing_tree
    public static <T> Node<T> buildTree(Iterator<String> iter, Function<String, T> f) {
        String val = iter.next();
        if (val.equals("x")) return null;
        Node<T> left = buildTree(iter, f);
        Node<T> right = buildTree(iter, f);
        return new Node<T>(f.apply(val), left, right);
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Node<Integer> root = buildTree(splitWords(scanner.nextLine()).iterator(), Integer::parseInt);
        scanner.close();
        List<List<Integer>> res = levelOrderTraversal(root);
        for (List<Integer> row : res) {
            System.out.println(row.stream().map(String::valueOf).collect(Collectors.joining(" ")));
        }
    }
}
