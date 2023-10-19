package com.algo.monster.dfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Given the root node of a valid BST and a value to insert into the tree, return a new root node representing the valid BST with the addition of the new item. If the new item already exists in the binary search tree, do not insert anything.
 *
 * You must expand on the original BST by adding a leaf node. Do not change the structure of the original BST.
 *
 * Time Complexity: O(h), h is height of the tree
 */
class BSTInsertion {
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

    public static Node<Integer> insertBst(Node<Integer> bst, int val) {
        if(bst == null) {
            return new Node<>(val);
        }

        if(bst.val > val) {
            bst.left = insertBst(bst.left, val);
        } else if(bst.val < val){
            bst.right = insertBst(bst.right, val);
        }

        return bst;
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

    public static <T> void formatTree(Node<T> node, List<String> out) {
        if (node == null) {
            out.add("x");
            return;
        }
        out.add(node.val.toString());
        formatTree(node.left, out);
        formatTree(node.right, out);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Node<Integer> bst = buildTree(splitWords(scanner.nextLine()).iterator(), Integer::parseInt);
        int val = Integer.parseInt(scanner.nextLine());
        scanner.close();
        Node<Integer> res = insertBst(bst, val);
        ArrayList<String> resArr = new ArrayList<>();
        formatTree(res, resArr);
        System.out.println(String.join(" ", resArr));
    }
}
