package com.algo.monster.priorityqueue;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given k sorted lists of numbers, merge them into one sorted list.
 *
 * Input: [[1, 3, 5], [2, 4, 6], [7, 10]]
 *
 * Output: [1, 2, 3, 4, 5, 6, 7, 10]
 *
 * Time Complexity: O(n log(k))
 *
 * There are always k elements in the min-heap. For n elements where each insertion/deletion takes O(log(k)) time,
 * the total time complexity is bounded by O(n log(k)).
 *
 * Space Complexity: O(k)
 *
 * Aside from the outputs, we use a min-heap of size k, so the space complexity is O(k)
 *
 * Note that we could also push all the elements into one min heap and pop them one by one.
 * The heap size would be O(n) which means heap pop would be O(log(n)). This approach is slower than the O(log(k))
 * solution above, especially when n >> k.
 *
 */
class MergeKSortedLists {
    private static class Element {
        private int val;
        private List<Integer> currentList;
        private int headIndex;

        public Element(int val, List<Integer> currentList, int headIndex) {
            this.val = val;
            this.currentList = currentList;
            this.headIndex = headIndex;
        }
    }

    public static List<Integer> mergeKSortedLists(List<List<Integer>> lists) {
        List<Integer> result = new ArrayList<>();
        PriorityQueue<Element> priorityQueue = new PriorityQueue<>(lists.size(), Comparator.comparingInt(value -> value.val));
        lists.forEach(l -> priorityQueue.add(new Element(l.get(0), l, 0)));
        while (!priorityQueue.isEmpty()) {
            Element e = priorityQueue.poll();
            result.add(e.val);
            if(e.headIndex != e.currentList.size() - 1) {
                priorityQueue.add(new Element(e.currentList.get(e.headIndex+1), e.currentList, e.headIndex+1));
            }
        }
        return result;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int listsLength = Integer.parseInt(scanner.nextLine());
        List<List<Integer>> lists = new ArrayList<>();
        for (int i = 0; i < listsLength; i++) {
            lists.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        scanner.close();
        List<Integer> res = mergeKSortedLists(lists);
        System.out.println(res.stream().map(String::valueOf).collect(Collectors.joining(" ")));
    }
}
