package com.algo.monster.advanceddatastructures.lru;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Design and implement a data structure for Least Recently Used (LRU) cache. It should support get and put operations.
 *
 * get(key): Get the value (which will always be positive) of the key if the key exists in the cache, otherwise return -1.
 * put(key, value): Set or insert the value if the key is not already present. When the cache reached its capacity,
 * it should invalidate the least recently used item before inserting a new item.
 * The cache is initialized with a positive capacity.
 *
 * Can you do both operations in O(1) time complexity?
 *
 */
class LRU {
    public static class LRUCache {
        //Double Linked List
        private static class DLL {
            public int key;
            public int val;
            public DLL next;
            public DLL prev;

            public DLL(int key, int val) {
                this.key = key;
                this.val = val;
            }
        }

        public int capacity;
        public HashMap<Integer, DLL> cache;
        public DLL head;
        public DLL tail;

        public int size;

        public LRUCache(int capacity) {
            this.cache = new HashMap<>(capacity);
            this.head = new DLL(0, 0);
            this.tail = new DLL(0, 0);
            this.head.next = tail;
            this.tail.prev = head;
            this.size = 0;
            this.capacity = capacity;
        }

        public int get(int key) {
            if (this.cache.containsKey(key)) {
                DLL loc = this.cache.get(key);
                loc.prev.next = loc.next;
                loc.next.prev = loc.prev;
                this.head.next.prev = loc;
                loc.next = this.head.next;
                this.head.next = loc;
                loc.prev = this.head;
                return loc.val;
            }
            return -1;
        }

        public void put(int key, int value) {
            if (this.cache.containsKey(key)) {
                get(key);
                this.cache.get(key).val = value;
                return;
            }
            this.size++;
            if (this.size > this.capacity) {
                DLL lru = this.tail.prev;
                cache.remove(lru.key);
                this.tail.prev.val = this.tail.val;
                this.tail.prev.next = null;
                this.tail = this.tail.prev;
                this.size--;
            }
            DLL newHead = new DLL(key, value);
            this.head.next.prev = newHead;
            newHead.next = this.head.next;
            this.head.next = newHead;
            newHead.prev = this.head;
            cache.put(key, newHead);
        }
    }

    public static List<Integer> exec(List<List<String>> operations) {
        LRUCache lru = null;
        List<Integer> res = new ArrayList<>();
        for (List<String> operation : operations) {
            switch (operation.get(0)) {
                case "LRUCache":
                    lru = new LRUCache(Integer.parseInt(operation.get(1)));
                    break;
                case "get":
                    res.add(lru.get(Integer.parseInt(operation.get(1))));
                    break;
                case "put":
                    lru.put(Integer.parseInt(operation.get(1)), Integer.parseInt(operation.get(2)));
                    break;
            }
        }
        return res;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int operationsLength = Integer.parseInt(scanner.nextLine());
        List<List<String>> operations = new ArrayList<>();
        for (int i = 0; i < operationsLength; i++) {
            operations.add(splitWords(scanner.nextLine()));
        }
        scanner.close();
        List<Integer> res = exec(operations);
        System.out.println(res.stream().map(String::valueOf).collect(Collectors.joining(" ")));
    }
}
