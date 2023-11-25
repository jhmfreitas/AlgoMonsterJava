package com.algo.monster.graph;

import java.util.*;

/**
 * For this problem, given a list of tasks and a list of requirements, compute a sequence of tasks that can be performed,
 * such that we complete every task once while satisfying all the requirements.
 *
 * Each requirement will be in the form of a list [a, b], where task a needs to be completed first before task b can be
 * completed,
 *
 * There is guaranteed to be a solution.
 *
 * Space Complexity: O(n)
 *
 * Time Complexity: O(n+m)
 *
 * The time complexity is equal to n the number of nodes in the graph plus m the number of edges in the graph.
 * This is because we have to go through every connection and node once when we sort the graph.
 *
 */
class TaskScheduling {

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

    public static List<String> taskScheduling(List<String> tasks, List<List<String>> requirements) {
        HashMap<String, List<String>> graph = new HashMap<>();
        for (String task : tasks) {
            graph.put(task, new ArrayList<>());
        }
        for (List<String> req : requirements) {
            graph.get(req.get(0)).add(req.get(1));
        }
        return topoSort(graph);
    }

    private static List<String> topoSort(Map<String, List<String>> graph) {
        Map<String, Integer> inDegrees = findInDegree(graph);
        ArrayDeque<String> queue = new ArrayDeque<>();
        List<String> scheduling = new ArrayList<>();

        inDegrees.entrySet().stream().filter(e -> e.getValue() == 0).forEach(e -> queue.add(e.getKey()));
        while (!queue.isEmpty()) {
            String node = queue.poll();
            scheduling.add(node);
            for (String neighbor : graph.get(node)) {
                inDegrees.put(neighbor, inDegrees.get(neighbor) - 1);
                if (inDegrees.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }
        return (graph.size() == scheduling.size()) ? scheduling : new ArrayList<>();
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> tasks = splitWords(scanner.nextLine());
        int requirementsLength = Integer.parseInt(scanner.nextLine());
        List<List<String>> requirements = new ArrayList<>();
        for (int i = 0; i < requirementsLength; i++) {
            requirements.add(splitWords(scanner.nextLine()));
        }
        scanner.close();
        List<String> res = taskScheduling(tasks, requirements);
        if (res.size() != tasks.size()) {
            System.out.println("output size " + res.size() + " does not match input size " + tasks.size());
            return;
        }
        HashMap<String, Integer> indices = new HashMap<>();
        for (int i = 0; i < res.size(); i++) {
            indices.put(res.get(i), i);
        }
        for (List<String> req : requirements) {
            for (String task : req) {
                if (!indices.containsKey(task)) {
                    System.out.println("'" + task + "' is not in output");
                    return;
                }
            }
            String a = req.get(0);
            String b = req.get(1);
            if (indices.get(a) >= indices.get(b)) {
                System.out.println("'" + a + "' is not before '" + b + "'");
                return;
            }
        }
        System.out.println("ok");
    }
}
