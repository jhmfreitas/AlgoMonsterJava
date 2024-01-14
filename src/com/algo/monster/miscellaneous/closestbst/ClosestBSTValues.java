package com.algo.monster.miscellaneous.closestbst;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Given a BST, output the k closest values in this BST to x. Sort the output by value.
 *
 * The output set is guaranteed to be unique.
 *
 * Do not convert the BST to a list.
 *
 * Time Complexity: O(N)
 * Space Complexity: O(N)
 *
 */
class ClosestBSTValues {
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

    public static void dfs(Node<Integer> bst, Deque<Integer> queue, int x, int k) {
        if (bst != null) {
            dfs(bst.left, queue, x, k);

            if (queue.size() < k) {
                queue.offer(bst.val);
            } else {
                if(Math.abs(bst.val - x) < Math.abs(queue.peek() - x)) {
                    queue.poll();
                    queue.offer(bst.val);
                } else {
                    return;
                }
            }

            dfs(bst.right, queue, x, k);
        }
    }

    public static List<Integer> closestValuesNoRecursion(Node<Integer> bst, int x, int k) {
        ArrayDeque<Integer> returnList = new ArrayDeque<>();
        ArrayList<Node<Integer>> stack = new ArrayList<>();
        Node<Integer> currentNode = bst;
        while(!stack.isEmpty() || currentNode != null) {
            while (currentNode != null) {
                stack.add(currentNode);
                currentNode = currentNode.left;
            }
            currentNode = stack.remove(stack.size() - 1);
            if (returnList.size() < k) {
                returnList.offer(currentNode.val);
            } else {
                if (Math.abs(returnList.peek() - x) > Math.abs(currentNode.val - x)) {
                    returnList.poll();
                    returnList.offer(currentNode.val);
                } else {
                    break;
                }
            }
            currentNode = currentNode.right;
        }
        return new ArrayList<>(returnList);
    }

    public static List<Integer> closestValues(Node<Integer> bst, int x, int k) {
        Deque<Integer> queue = new ArrayDeque<>();
        dfs(bst, queue, x, k);
        return new ArrayList<>(queue);
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
        int x = Integer.parseInt(scanner.nextLine());
        int k = Integer.parseInt(scanner.nextLine());
        scanner.close();
        List<Integer> res = closestValues(bst, x, k);
        System.out.println(res.stream().map(String::valueOf).collect(Collectors.joining(" ")));
    }
}
