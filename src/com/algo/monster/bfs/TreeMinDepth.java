package com.algo.monster.bfs;

import java.util.*;
import java.util.function.Function;

/**
 * Given a binary tree, find the depth of the shallowest leaf node.
 *
 * Time Complexity: O(n)
 *
 * We traverse every edge and node once but since the number of edges is n - 1, then this simply becomes O(n).
 *
 * Space Complexity: O(n)
 *
 * There are at most O(n) nodes in the queue.
 */
class TreeMinDepth {
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

    public static int binaryTreeMinDepth(Node<Integer> root) {
        int shallowNodeDepth = 0;
        if(root == null)
            return shallowNodeDepth;

        ArrayDeque<Node<Integer>> queue = new ArrayDeque<>();
        queue.add(root);
        int level = 0;
        while (!queue.isEmpty() && shallowNodeDepth == 0) {
            int n = queue.size();
            for (int i = 0 ; i < n; i++) {
                Node<Integer> node = queue.pop();

                if(node.left == null && node.right == null) {
                    shallowNodeDepth = level;
                    break;
                }

                if (node.left != null) {
                    queue.add(node.left);
                }

                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            level++;
        }
        return shallowNodeDepth;
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
        int res = binaryTreeMinDepth(root);
        System.out.println(res);
    }
}
