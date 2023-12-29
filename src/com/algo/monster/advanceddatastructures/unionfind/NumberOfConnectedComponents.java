package com.algo.monster.advanceddatastructures.unionfind;

import java.util.*;
import java.util.stream.Collectors;

class NumberOfConnectedComponents {
    public static UnionFind<Integer> dsu = new UnionFind<>();

    public static class UnionFind<T> {
        public Map<T, T> sets = new HashMap<T, T>();

        public T find(T x) {
            T y = sets.getOrDefault(x, x);

            if (y != x) {
                y  = find(y);
                // tree compression optimization
                // moves the nodes closer to the root
                sets.put(x, y);
            }

            return y;
        }

        public void union(T x, T y) {
            sets.put(find(x), find(y));
        }
    }

    public static List<Integer> numberOfConnectedComponents(int n, List<List<Integer>> connections) {
        List<Integer> res = new ArrayList<>();
        int connectedComponents = n;
        for (List<Integer> edge : connections) {
            if (!Objects.equals(dsu.find(edge.get(0)), dsu.find(edge.get(1)))) {
                dsu.union(edge.get(0), edge.get(1));
                connectedComponents--;
            }
            res.add(connectedComponents);
        }
        return res;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int connectionsLength = Integer.parseInt(scanner.nextLine());
        List<List<Integer>> connections = new ArrayList<>();
        for (int i = 0; i < connectionsLength; i++) {
            connections.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        scanner.close();
        List<Integer> res = numberOfConnectedComponents(n, connections);
        System.out.println(res.stream().map(String::valueOf).collect(Collectors.joining(" ")));
    }
}
