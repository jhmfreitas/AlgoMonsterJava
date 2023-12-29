package com.algo.monster.advanceddatastructures.unionfind;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 * Time Complexity: O(log(n)) for an operation where n is the number of nodes in our graph.
 * The space complexity here is O(n).
 *
 */
class DsuIntroduction {

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

    public static class SameSet {

        public static UnionFind<Integer> unionFind = new UnionFind<>();
        public void merge(int x, int y) {
            unionFind.union(x, y);
        }

        public boolean isSame(int x, int y) {
            return Objects.equals(unionFind.find(x), unionFind.find(y));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SameSet sol = new SameSet();
        int queryLength = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < queryLength; i++) {
            String[] segs = scanner.nextLine().split(" ");
            String op = segs[0];
            int x = Integer.parseInt(segs[1]);
            int y = Integer.parseInt(segs[2]);
            if (op.equals("union")) {
                sol.merge(x, y);
            } else if (op.equals("is_same")) {
                boolean res = sol.isSame(x, y);
                System.out.println(res);
            }
        }
        scanner.close();
    }
}
