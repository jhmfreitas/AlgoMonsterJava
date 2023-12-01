package com.algo.monster.twopointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Given a linked list with potentially a loop, determine whether the linked list from the first node contains a cycle in it.
 * For bonus points, do this with constant space.
 *
 * Time Complexity: O(n)
 *
 * Technically O(n/2) but again we factor out the constant and we are left with linear time.
 * Worst case we have, O(2n) as the small pointer moves around the entire array once. Either way we have O(n) time complexity.
 *
 * Space Complexity: O(1)
 *
 */
class LinkedListCycle {
    public static class Node<T> {
        public T val;
        public Node<T> next;

        public Node(T val) {
            this(val, null);
        }

        public Node(T val, Node<T> next) {
            this.val = val;
            this.next = next;
        }
    }

    public static Node<Integer> nextNode(Node<Integer> node){
        return node.next != null ? node.next : node;
    }

    public static boolean hasCycle(Node<Integer> nodes) {
        Node<Integer> slowPointer = nextNode(nodes);
        Node<Integer> fastPointer = nextNode(nextNode(nodes));
        while (fastPointer != slowPointer && fastPointer.next != null) {
            slowPointer = nextNode(slowPointer);
            fastPointer = nextNode(nextNode(fastPointer));
        }
        return fastPointer.next != null;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> rawInput = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        ArrayList<Node<Integer>> nodesList = new ArrayList<>();
        for (int i = 0; i < rawInput.size(); i++) {
            nodesList.add(new Node<Integer>(i));
        }
        for (int i = 0; i < rawInput.size(); i++) {
            if (rawInput.get(i) != -1) {
                nodesList.get(i).next = nodesList.get(rawInput.get(i));
            }
        }
        Node<Integer> nodes = nodesList.get(0);
        scanner.close();
        boolean res = hasCycle(nodes);
        System.out.println(res);
    }
}
