package com.algo.monster.miscellaneous.interval;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given a collection of intervals, merge all overlapping intervals.
 *
 * Time Complexity: O(n*log(n))
 *
 * This is because we have to sort the array.
 *
 * Space Complexity: O(1)
 */
class MergeIntervals {
    public static List<List<Integer>> mergeIntervals(List<List<Integer>> intervals) {
        intervals.sort(Comparator.comparingInt(o -> o.get(0)));

        List<List<Integer>> res = new ArrayList<>();
        for (List<Integer> interval : intervals) {
            if (res.isEmpty() || !overlaps(res.get(res.size() - 1), interval)) {
                res.add(interval);
            } else {
                List<Integer> lastInterval = res.get(res.size() - 1);
                lastInterval.set(1, Integer.max(lastInterval.get(1), interval.get(1)));
            }
        }
        return res;
    }

    private static boolean overlaps(List<Integer> interval1, List<Integer> interval2) {
        // or !(interval1.get(1) < interval2.get(0) || interval1.get(0) > interval2.get(1))
        return Integer.max(interval1.get(0), interval2.get(0)) <= Integer.min(interval1.get(1), interval2.get(1));
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int intervalsLength = Integer.parseInt(scanner.nextLine());
        List<List<Integer>> intervals = new ArrayList<>();
        for (int i = 0; i < intervalsLength; i++) {
            intervals.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        scanner.close();
        List<List<Integer>> res = mergeIntervals(intervals);
        for (List<Integer> row : res) {
            System.out.println(row.stream().map(String::valueOf).collect(Collectors.joining(" ")));
        }
    }
}
