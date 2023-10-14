package com.algo.monster.binarysearch;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * You've begun your new job to organize newspapers. Each morning, you are to separate the newspapers into smaller piles and assign each pile to a co-worker. This way, your co-workers can read through the newspapers and examine its contents simultaneously.
 *
 * Each newspaper is marked with a read time to finish all its contents. A worker can read one newspaper at a time, and when they finish a newspaper, they can start reading the next newspaper. Your goal is to minimize the amount of time needed for your co-workers to finish all newspapers.
 * Additionally, the newspapers came in a particular order, and you must not disarrange the original ordering when distributing the newspapers. In other words, you cannot pick and choose newspapers randomly from the whole pile to assign to a co-worker, but rather you must take a subsection of consecutive newspapers from the whole pile.
 *
 * What is the minimum amount of time it would take to have your coworkers go through all the newspapers? That is, if you optimize the distribution of newspapers, what is the longest read time among all piles?
 *
 * Time Complexity: O(n log(m)), m = sum(newspapers_read_times)
 * Space Complexity: O(1)
 */
class Newspapers {
    public static int newspapersSplit(List<Integer> newspapersReadTimes, int numCoworkers) {
        int low = Collections.max(newspapersReadTimes);
        int high = newspapersReadTimes.stream().reduce(0, (a, b) -> a+b);
        int minAmountOfTime = -1;
        // Time Complexity: O(N)
        while(low <= high){
            // The mid calculation is different from the others because we are looking for time in sequential order: 25(high) + 15(low) = 40/2 = 20
            int mid = (high + low)/2;
            // Time Complexity: O(log(m)), m = sum(newspapers_read_times)
            if (feasible(newspapersReadTimes, numCoworkers, mid)) {
                minAmountOfTime = mid;
                high = mid - 1;
            }
            else {
                low = mid + 1;
            }
        }
        return minAmountOfTime;
    }

    private static boolean feasible(List<Integer> newspapersReadTimes, int numCoworkers, int mid) {
        int accumulatedTimeForWorker = 0;
        int workersNeeded = 0;
        for (Integer time: newspapersReadTimes){
            if(accumulatedTimeForWorker + time > mid) {
                workersNeeded++;
                accumulatedTimeForWorker = 0;
            }
            accumulatedTimeForWorker+=time;
        }

        if(accumulatedTimeForWorker != 0) {
            workersNeeded++;
        }

        return workersNeeded <= numCoworkers;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> newspapersReadTimes = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        int numCoworkers = Integer.parseInt(scanner.nextLine());
        scanner.close();
        int res = newspapersSplit(newspapersReadTimes, numCoworkers);
        System.out.println(res);
    }
}