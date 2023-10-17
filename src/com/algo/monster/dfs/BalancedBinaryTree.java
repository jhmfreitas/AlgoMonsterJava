package com.algo.monster.dfs;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 * A binary tree is considered balanced if, for every node in the tree, the difference in the height (or depth) of its left and right subtrees is at most 1.
 *
 * In other words, the depth of the two subtrees for every node in the tree differ by no more than one.
 *
 * The height (or depth) of a tree is defined as the number of edges on the longest path from the root node to any leaf node.
 *
 * Note: An empty tree is considered balanced by definition.
 *
 * In that case, given a binary tree, determine if it's balanced.
 *
 * Time Complexity: O(n), where n is the number of nodes in this tree.
 *
 * Space Complexity: O(h) stack memory where h is the height of the tree.
 * In the worst case, we have a skewed binary tree (each node only have one or no child), which has the height O(n).
 */
class BalancedBinaryTree {
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

    public static boolean isBalanced(Node<Integer> tree) {
        return tree != null && dfs(tree) != -1;
    }
    public static int dfs(Node<Integer> root) {
        if(root == null ) {
            return 0;
        }

        int leftHeight = dfs(root.left);
        int rightHeight  = dfs(root.right);

        if(leftHeight == -1 || rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }

        return Math.max(leftHeight, rightHeight) + 1;
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
        Node<Integer> tree = buildTree(splitWords(scanner.nextLine()).iterator(), Integer::parseInt);
        scanner.close();
        boolean res = isBalanced(tree);
        System.out.println(res);
    }
}
