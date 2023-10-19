package com.algo.monster.dfs;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 * A binary search tree is a binary tree with the property that for any node, the value of this node is greater than any node in its left subtree and less than any node's value in its right subtree. In other words, an inorder traversal of a binary search tree yields a list of values that is monotonically increasing (strictly increasing).
 *
 * Given a binary tree, determine whether it is a binary search tree.
 *
 * Time Complexity: O(n) since we visit each node at most once and there are n nodes in the tree.
 *
 * Space Complexity: O(h) stack memory where h is the height of the tree, but in the worst case if we have a skewed tree, h is in O(n).
 *
 */
class ValidBinarySearchTree {
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

    private static boolean dfs(Node<Integer> root, int min, int max) {
        // empty nodes are always valid
        if (root == null) return true;
        if (!(min < root.val && root.val < max)) {
            return false;
        }
        return dfs(root.left, min, root.val) && dfs(root.right, root.val, max);
    }

    public static boolean validBst(Node<Integer> root) {
        // root is always valid
        return dfs(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
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
        boolean res = validBst(root);
        System.out.println(res);
    }
}
