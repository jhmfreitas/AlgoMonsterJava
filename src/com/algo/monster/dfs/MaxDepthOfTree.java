package com.algo.monster.dfs;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Max depth of a binary tree is the longest root-to-leaf path. Given a binary tree, find its max depth.
 * Here, we define the length of the path to be the number of edges on that path, not the number of nodes.
 *
 * Time Complexity: O(n)
 * There are n nodes and n - 1 edges in a tree so if we traverse each once then the total traversal is O(2n - 1) which is O(n).
 *
 * Space Complexity: O(h)
 * The call stack uses at most O(h) memory where h is the height of the tree, which is worst case O(n) when the tree is skewed (each node has one or no children).
 *
 */
class MaxDepthOfTree {
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

    public static int treeMaxDepth(Node<Integer> root) {
        return root != null?  dfs(root) - 1 : 0;
    }

    private static int dfs(Node<Integer> root) {
        if(root == null) {
            return 0;
        }

        int leftDepth = dfs(root.left);
        int rightDepth = dfs(root.right);
        return Integer.max(leftDepth, rightDepth) + 1;
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
        int res = treeMaxDepth(root);
        System.out.println(res);
    }
}
