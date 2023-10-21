package com.algo.monster.dfs;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 *
 * Given a binary search tree (BST), find the lowest common ancestor (LCA) node of two given nodes in the BST.
 *
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”
 *
 * Input
 * bst: a binary tree representing the existing BST.
 * p: the value of node p as described in the question
 * q: the value of node q as described in the question
 * Output
 * The value of the LCA between nodes p and q
 *
 * Time Complexity
 * Since we don't need to traverse the entire tree for our search and on each level we only visit a single node, the final time complexity is O(h) where h is the height of the tree. In the worst case, the tree is not balanced and h is n.
 *
 * Space Complexity
 * If we count stack memory as space usage, then our space complexity is O(h) where h is the height of the tree because in the worst case we have a line graph where nodes p and q are at the end, which leads to O(n) stack memory.
 */
class LowestCommonAncestor {
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

    public static int lcaOnBst(Node<Integer> bst, int p, int q) {
        if(p < bst.val && q < bst.val) {
            return lcaOnBst(bst.left, p, q );
        } else if (bst.val < q && bst.val < p) {
            return lcaOnBst(bst.right, p, q );
        } else {
            return bst.val;
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
        Node<Integer> bst = buildTree(splitWords(scanner.nextLine()).iterator(), Integer::parseInt);
        int p = Integer.parseInt(scanner.nextLine());
        int q = Integer.parseInt(scanner.nextLine());
        scanner.close();
        int res = lcaOnBst(bst, p, q);
        System.out.println(res);
    }
}
