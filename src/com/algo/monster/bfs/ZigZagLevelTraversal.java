package com.algo.monster.bfs;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Given a binary tree, return its level order traversal but in alternate left to right order.
 *
 * Time Complexity: O(n)
 * We traverse every edge and node once but since the number of edges is n - 1, then this simply becomes O(n).
 *
 * Space Complexity: O(n)
 * There are at most O(n) nodes in the queue.
 */
class ZigZagLevelTraversal {
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

    public static List<List<Integer>> zigZagTraversal(Node<Integer> root) {
        List<List<Integer>> result = new ArrayList<>();
        if(root == null)
            return result;

        ArrayDeque<Node<Integer>> queue = new ArrayDeque<>();
        queue.add(root);
        boolean leftToRight = true;
        while (!queue.isEmpty()) {
            int n = queue.size();
            ArrayDeque<Integer> newLevel = new ArrayDeque<>();
            for (int i = 0 ; i < n; i++) {
                Node<Integer> node = queue.pop();
                if(leftToRight){
                    newLevel.add(node.val);
                }else{
                    newLevel.addFirst(node.val);
                }

                if (node.left != null) {
                    queue.add(node.left);
                }

                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            result.add(Arrays.asList(newLevel.toArray(new Integer[0])));
            leftToRight = !leftToRight;
        }
        return result;
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
        List<List<Integer>> res = zigZagTraversal(root);
        for (List<Integer> row : res) {
            System.out.println(row.stream().map(String::valueOf).collect(Collectors.joining(" ")));
        }
    }
}
