package com.algo.monster.graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * There are a total of n courses a student has to take, numbered from 0 to n-1. A course may have prerequisites.
 * The "depends on" relationship is expressed as a pair of numbers. For example, [0, 1] means you need to take course 1
 * before taking course 0. Given n and the list of prerequisites, decide if it is possible to take all the courses.
 *
 * Example 1:
 *
 * Input: n = 2, prerequisites = [[0, 1]]
 *
 * Output: true
 *
 * Explanation: we can take 1 first and then 0.
 *
 * Example 2:
 *
 * Input: n = 2, prerequisites = [[0, 1], [1, 0]]
 *
 * Output: false
 *
 * Explanation: the two courses depend on each other, there is no way to take them
 *
 * Time Complexity: O(n+m)
 *
 * The time complexity is equal to n the number of nodes in the graph plus m the number of edges in the graph.
 * This is because we have to go through every connection and node once when we sort the graph.
 *
 * Space Complexity: O(n)
 *
 * We use the states array to store the status of each node, which takes O(n) space.
 *
 */
class CourseSchedule {
    private enum State {
        TO_VISIT, VISITING, VISITED
    }

    private static Map<Integer, List<Integer>> buildGraph(List<List<Integer>> prerequisites) {
        Map<Integer, List<Integer>> graph = new HashMap<>(prerequisites.size());
        for (List<Integer> dependency : prerequisites) {
            graph.computeIfAbsent(dependency.get(0), k -> new ArrayList<>()).add(dependency.get(1));
        }
        return graph;
    }


    public static boolean isValidCourseSchedule(int n, List<List<Integer>> prerequisites) {
        Map<Integer, List<Integer>> graph = buildGraph(prerequisites);
        State[] states = new State[n];
        Arrays.fill(states, State.TO_VISIT);

        for (int i = 0; i < n; i++) {
            if (!dfs(i, states, graph))
                return false;
        }
        return true;
    }

    private static boolean dfs(int start, State[] states, Map<Integer, List<Integer>> graph) {
        states[start] = State.VISITING;
        List<Integer> neighbors = graph.get(start);
        if (neighbors != null) {
            for (Integer nextNode : neighbors) {
                if(states[nextNode] == State.VISITED)
                    continue;

                // Cycle was detected
                if (states[nextNode] == State.VISITING)
                    return false;

                // Visit neighbors
                if(!dfs(nextNode, states, graph))
                    return false;
            }
        }

        states[start] = State.VISITED;
        return true;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int prerequisitesLength = Integer.parseInt(scanner.nextLine());
        List<List<Integer>> prerequisites = new ArrayList<>();
        for (int i = 0; i < prerequisitesLength; i++) {
            prerequisites.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        scanner.close();
        boolean res = isValidCourseSchedule(n, prerequisites);
        System.out.println(res);
    }
}
