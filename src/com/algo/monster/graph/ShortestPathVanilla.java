package com.algo.monster.graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * Given an (unweighted) connected graph, return the length of the shortest path between two nodes A and B, in terms of the number of edges.
 *
 * Assume there always exists a path between nodes A and B.
 *
 * Time Complexity: O(n+m)
 *
 * Again we adopt the convention that n denote the number of nodes in the graph and m the number of edges. The time spent is equal to the number of nodes and edges in the worst case. Consider for example a linear graph 0->1->2->3 and so on where we want to get from end to end, we would traverse every node and edge exactly once.
 *
 * Space Complexity: O(n), each level have at most O(n) nodes in the queue.
 */
class ShortestPathVanilla {
    public static int shortestPath(List<List<Integer>> graph, int a, int b) {
        return bfs(graph, a, b);
    }
    public static int bfs(List<List<Integer>> graph, int root, int target) {
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.add(root);
        HashSet<Integer> visited = new HashSet<>();
        visited.add(root);
        int level = 0;
        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i = 0; i<n;i++) {
                int node = queue.pop();
                if(node == target) {
                    return level;
                }
                for (int neighbor : getNeighbors(graph, node)) {
                    if(visited.contains(neighbor)){
                        continue;
                    }
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
            level++;
        }
        return level;
    }

    private static List<Integer> getNeighbors(List<List<Integer>> graph, int node) {
        return graph.get(node);
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int graphLength = Integer.parseInt(scanner.nextLine());
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < graphLength; i++) {
            graph.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        int a = Integer.parseInt(scanner.nextLine());
        int b = Integer.parseInt(scanner.nextLine());
        scanner.close();
        int res = shortestPath(graph, a, b);
        System.out.println(res);
    }
}
