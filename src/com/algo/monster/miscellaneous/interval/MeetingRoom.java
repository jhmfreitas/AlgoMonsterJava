package com.algo.monster.miscellaneous.interval;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given an array of meeting time intervals consisting of start and end times
 * [[s1,e1],[s2,e2],...](si< ei), determine if a person could attend all meetings.
 *
 * Time Complexity: O(n*log(n))
 *
 * Let n denote the size of the intervals array.
 *
 * Space Complexity: O(1)
 */
class MeetingRoom {
    public static boolean meetingRooms(List<List<Integer>> intervals) {
        intervals.sort(Comparator.comparingInt(e -> e.get(0)));

        for (int i = 0; i < intervals.size() - 2; i++) {
            if (intervals.get(i).get(1) > intervals.get(i+1).get(0)) {
                return false;
            }
        }
        return true;
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
        boolean res = meetingRooms(intervals);
        System.out.println(res);
    }
}
