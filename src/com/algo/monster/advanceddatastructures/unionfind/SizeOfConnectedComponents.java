package com.algo.monster.advanceddatastructures.unionfind;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

class SizeOfConnectedComponents {

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

    public static class SetCounter {
        public static UnionFind<Integer> unionFind = new UnionFind<>();

        public static Map<Integer, Integer> sizes = new HashMap<>();

        public void merge(int x, int y) {
            if (!Objects.equals(unionFind.find(x), unionFind.find(y))) {
                int newSize = count(x) + count(y);
                unionFind.union(x, y);
                sizes.put(unionFind.find(x), newSize);
            }
        }

        public int count(int x) {
            return sizes.getOrDefault(unionFind.find(x),1);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SetCounter sol = new SetCounter();
        int queryLength = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < queryLength; i++) {
            String[] segs = scanner.nextLine().split(" ");
            String op = segs[0];
            int x = Integer.parseInt(segs[1]);
            if (op.equals("union")) {
                int y = Integer.parseInt(segs[2]);
                sol.merge(x, y);
            } else if (op.equals("count")) {
                int res = sol.count(x);
                System.out.println(res);
            }
        }
        scanner.close();
    }
}
