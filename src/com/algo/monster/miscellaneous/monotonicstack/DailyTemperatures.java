package com.algo.monster.miscellaneous.monotonicstack;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given a list of daily temperatures T, return a list such that, for each day in the input, tells you how many days
 * you would have to wait until a warmer temperature. If there is no future day for which this is possible, put 0 instead.
 *
 * Time Complexity: O(n)
 * Space Complexity: O(n)
 */
class DailyTemperatures {
    public static List<Integer> dailyTemperatures(List<Integer> t) {
        List<Integer> res = new ArrayList<>(Collections.nCopies(t.size(), 0));
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = t.size() - 1; i >= 0; i--) {
            while (!q.isEmpty() && t.get(q.getLast()) <= t.get(i)) {
                q.removeLast();
            }

            if (q.isEmpty()) {
                res.set(i, 0);
            } else {
                res.set(i, q.getLast() - i);
            }

            q.addLast(i);
        }
        return res;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> t = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        scanner.close();
        List<Integer> res = dailyTemperatures(t);
        System.out.println(res.stream().map(String::valueOf).collect(Collectors.joining(" ")));
    }
}
