package com.algo.monster.advanceddatastructures.unionfind;

import java.util.*;
import java.util.stream.Collectors;

//The key observation is that in the very end, all of the cities are disconnected from one another,
// so that the number of clusters is n in the very end
// Alternatively, there is a data structure that can maintain several disjoint-set data structures simultaneously,
// and it's called Link Cut Tree.
class ReverseUnionFind {
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

    public static List<Integer> umbristan(int n, List<List<Integer>> breaks) {
        List<Integer> res = new ArrayList<>();
        Collections.reverse(breaks);
        // loop through the reverse list and merge the edges if they are not of the same list
        for (List<Integer> edge : breaks) {
            if (!Objects.equals(dsu.find(edge.get(0)), dsu.find(edge.get(1)))) {
                // merging 2 connected components means total number of connected components decreases by 1
                dsu.union(edge.get(0), edge.get(1));
                n--;
            }
            res.add(n);
        }

        Collections.reverse(res);
        return res;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int breaksLength = Integer.parseInt(scanner.nextLine());
        List<List<Integer>> breaks = new ArrayList<>();
        for (int i = 0; i < breaksLength; i++) {
            breaks.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        scanner.close();
        List<Integer> res = umbristan(n, breaks);
        System.out.println(res.stream().map(String::valueOf).collect(Collectors.joining(" ")));
    }
}
