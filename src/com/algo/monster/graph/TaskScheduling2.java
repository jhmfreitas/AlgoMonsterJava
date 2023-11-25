package com.algo.monster.graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This problem is similar to Task Scheduling. The primary difference is now we assign times to tasks and we ask for the
 * minimum amount of time to complete all tasks. There will be an extra times array such that times[i] indicates the time
 * required to complete task[i]. You have also invited all your friends to help complete your tasks so you can work on any
 * amount of tasks at a given time. Remember that task a must be completed before completing task b (but the starting time
 * don't need to be in order).
 *
 * There is guaranteed to be a solution.
 *
 * Time Complexity: O(n+m)
 *
 * The time complexity is equal to n the number of nodes in the graph plus m the number of edges
 * in the graph. This is because we have to go through every connection and node once when we sort the graph.
 *
 * Space Complexity: O(n)
 *
 * The queue holds at most n nodes in the worst case.
 *
 */
class TaskScheduling2 {
    public static Map<String, Integer> findInDegree(Map<String, List<String>> graph) {
        HashMap<String, Integer> inDegrees = new HashMap<>();
        graph.keySet().forEach(t -> inDegrees.put(t, 0));
        graph.forEach((key, value) -> {
            for (String n : value) {
                inDegrees.put(n, inDegrees.get(n) + 1);
            }
        });
        return inDegrees;
    }

    public static int taskScheduling2(List<String> tasks, List<Integer> times, List<List<String>> requirements) {
        HashMap<String, List<String>> graph = new HashMap<>();
        HashMap<String, Integer> taskTimes = new HashMap<>();
        for (int i = 0 ; i < tasks.size(); i++) {
            graph.put(tasks.get(i), new ArrayList<>());
            taskTimes.put(tasks.get(i), times.get(i));
        }
        for (List<String> req : requirements) {
            graph.get(req.get(0)).add(req.get(1));
        }
        return topoSort(graph, taskTimes);
    }


    private static int topoSort(Map<String, List<String>> graph, HashMap<String, Integer> taskTimes) {
        int res = 0;
        Map<String, Integer> inDegrees = findInDegree(graph);
        ArrayDeque<String> queue = new ArrayDeque<>();
        List<String> scheduling = new ArrayList<>();

        inDegrees.entrySet().stream().filter(e -> e.getValue() == 0).forEach(e -> queue.add(e.getKey()));
        while (!queue.isEmpty()) {
            int n = queue.size();
            int maxTime = 0;
            for (int i = 0; i < n; i++) {
                String node = queue.poll();
                scheduling.add(node);
                for (String neighbor : graph.get(node)) {
                    inDegrees.put(neighbor, inDegrees.get(neighbor) - 1);
                    if (inDegrees.get(neighbor) == 0) {
                        queue.add(neighbor);
                    }
                }
                // Save the time of the dependency that takes longer to perform
                if(taskTimes.get(node) > maxTime) {
                    maxTime = taskTimes.get(node);
                }
            }
            res += maxTime;
        }
        return (graph.size() == scheduling.size()) ? res : 0;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> tasks = splitWords(scanner.nextLine());
        List<Integer> times = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        int requirementsLength = Integer.parseInt(scanner.nextLine());
        List<List<String>> requirements = new ArrayList<>();
        for (int i = 0; i < requirementsLength; i++) {
            requirements.add(splitWords(scanner.nextLine()));
        }
        scanner.close();
        int res = taskScheduling2(tasks, times, requirements);
        System.out.println(res);
    }
}
