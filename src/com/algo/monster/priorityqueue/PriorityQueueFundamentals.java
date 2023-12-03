package com.algo.monster.priorityqueue;

import java.util.*;
import java.util.stream.Collectors;

class PriorityQueueFundamentals {
    public static List<Integer> heapTop3(List<Integer> arr) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(arr);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(priorityQueue.poll());
        }
        return list;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> arr = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        scanner.close();
        List<Integer> res = heapTop3(arr);
        System.out.println(res.stream().map(String::valueOf).collect(Collectors.joining(" ")));
    }
}
