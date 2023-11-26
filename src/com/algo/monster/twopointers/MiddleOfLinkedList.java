package com.algo.monster.twopointers;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Find the middle node of a linked list.
 *
 * Input: 0 1 2 3 4
 *
 * Output: 2
 *
 * If the number of nodes is even, then return the second middle node.
 *
 * Input: 0 1 2 3 4 5
 *
 * Output: 3
 *
 * Time Complexity: O(n)
 *
 * Technically O(n/2) but again constants are cut out from the time complexity and so we are left with just O(n).
 *
 * Space Complexity: O(1)
 *
 *
 */
class MiddleOfLinkedList {
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

    public static int middleOfLinkedList(Node<Integer> head) {
        Node<Integer> slowPointer = head;
        Node<Integer> fastPointer = head;
        while (fastPointer != null && fastPointer.next != null) {
            fastPointer = fastPointer.next.next;
            slowPointer = slowPointer.next;
        }
        return slowPointer.val;
    }

    public static <T> Node<T> buildList(Iterator<String> iter, Function<String, T> f) {
        if (!iter.hasNext()) return null;
        String val = iter.next();
        Node<T> next = buildList(iter, f);
        return new Node<T>(f.apply(val), next);
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Node<Integer> head = buildList(splitWords(scanner.nextLine()).iterator(), Integer::parseInt);
        scanner.close();
        int res = middleOfLinkedList(head);
        System.out.println(res);
    }
}
