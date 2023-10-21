package com.algo.monster.backtracking;

import java.util.*;
import java.util.function.Function;

/**
 *
 * Given a ternary tree (each node of the tree has at most three children), find all root-to-leaf paths.
 *
 */
class DfsWithStates {
    public static class Node<T> {
        public T val;
        public List<Node<T>> children;

        public Node(T val) {
            this(val, new ArrayList<>());
        }

        public Node(T val, List<Node<T>> children) {
            this.val = val;
            this.children = children;
        }
    }

    public static List<String> ternaryTreePaths(Node<Integer> root) {
        ArrayList<String> paths = new ArrayList<>();
        if(root != null) {
            dfsPathExplorer(root, new ArrayList<>(), paths);
        }
        return paths;
    }

    public static void dfsPathExplorer(Node<Integer> root, ArrayList<String> currentPath, ArrayList<String> paths) {
        if(root.children.isEmpty()) {
            currentPath.add(String.valueOf(root.val));
            paths.add(String.join("->", currentPath));
            currentPath.remove( currentPath.size() - 1);
            return;
        }

        for (Node<Integer> node : root.children) {
            if(node != null) {
                currentPath.add(String.valueOf(root.val));
                dfsPathExplorer(node, currentPath, paths);
                currentPath.remove( currentPath.size() - 1);
            }
        }
    }

    // this function builds a tree from input; you don't have to modify it
    // learn more about how trees are encoded in https://algo.monster/problems/serializing_tree
    public static <T> Node<T> buildTree(Iterator<String> iter, Function<String, T> f) {
        String val = iter.next();
        int num = Integer.parseInt(iter.next());
        ArrayList<Node<T>> children = new ArrayList<>();
        for (int i = 0; i < num; i++)
            children.add(buildTree(iter, f));
        return new Node<T>(f.apply(val), children);
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Node<Integer> root = buildTree(splitWords(scanner.nextLine()).iterator(), Integer::parseInt);
        scanner.close();
        List<String> res = ternaryTreePaths(root);
        for (String line : res) {
            System.out.println(line);
        }
    }
}
