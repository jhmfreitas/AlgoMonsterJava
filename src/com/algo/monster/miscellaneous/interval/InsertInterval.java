package com.algo.monster.miscellaneous.interval;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
 *
 * You may assume that the intervals were initially sorted according to their start times.
 */
class InsertInterval {

    // Time complexity: O(nlog(n)) because of the sorting
    public static List<List<Integer>> insertInterval(List<List<Integer>> intervals, List<Integer> newInterval) {
        intervals.add(newInterval);
        intervals.sort(Comparator.comparingInt(l -> l.get(0)));

        List<List<Integer>> mergedList = new ArrayList<>();
        for (List<Integer> interval : intervals) {
            if (mergedList.isEmpty() || !overlaps(mergedList.get(mergedList.size() - 1),interval)) {
                mergedList.add(interval);
            } else {
                List<Integer> lastInterval = mergedList.get(mergedList.size() - 1);
                lastInterval.set(1, Integer.max(interval.get(1), lastInterval.get(1)));
            }
        }
        return mergedList;
    }

    // Time complexity: O(n))
    public static List<List<Integer>> insertInterval2(List<List<Integer>> intervals, List<Integer> newInterval) {
        List<List<Integer>> r = new ArrayList<>();

        int i = 0;
        int n = intervals.size();
        while (i < n && intervals.get(i).get(1) < newInterval.get(0)) {
            r.add(intervals.get(i));
            i++;
        }

        while (i < n && intervals.get(i).get(0) <= newInterval.get(1)) {
            newInterval.set(0, Math.min(newInterval.get(0), intervals.get(i).get(0)));
            newInterval.set(1, Math.max(newInterval.get(1), intervals.get(i).get(1)));
            i++;
        }
        r.add(newInterval);

        while (i < n) {
            r.add(intervals.get(i));
            i++;
        }

        return r;
    }

    private static boolean overlaps(List<Integer> interval1, List<Integer> interval2) {
        return !(interval1.get(0) > interval2.get(1) || interval1.get(1) < interval2.get(0));
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
        List<Integer> newInterval = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        scanner.close();
        List<List<Integer>> res = insertInterval(intervals, newInterval);
        for (List<Integer> row : res) {
            System.out.println(row.stream().map(String::valueOf).collect(Collectors.joining(" ")));
        }
    }
}
