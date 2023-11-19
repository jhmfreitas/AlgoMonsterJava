package com.algo.monster.graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * You are given an m by n grid of integers representing a map of a dungeon. In this map:
 *
 * -1 represents a wall or an obstacle of some kind.
 * 0 represents a gate out of the dungeon.
 * INF represents empty space.
 * For this question, let INF be 2^31 - 1 == 2147483647, which is the max value of the integer type in many programming languages.
 *
 * Venturing into the dungeon is very dangerous, so you would like to know how close you are at each point in the dungeon to the closest exit. Given the map of the dungeon, return the same map, but for each empty space, that space is replaced by the number of steps it takes to reach the closest exit. If a space cannot reach the exit, that space remains INF.
 *
 * Note that each step, you can move horizontally or vertically, but you cannot move pass a wall or an obstacle.
 *
 * Time and space complexity of O(n * m).
 */
class WallsAndGates {

    public static final int MAX_INT = 2147483647;

    public static class Coordinate {
        int r;
        int c;

        public Coordinate(int r, int c) {
            this.r = r;
            this.c = c;
        }
        @Override
        public int hashCode() {
            return Objects.hash(r, c);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (this == obj) return true;
            if ((obj instanceof Coordinate) && ((Coordinate) obj).r == r && ((Coordinate) obj).c == c) {
                return true;
            }
            return false;
        }
        
    }

    public static List<List<Integer>> mapGateDistances(List<List<Integer>> dungeonMap) {
        Integer count = 0;
        // bfs starting from each land portion
        for (int i = 0; i < dungeonMap.size(); i++) {
            for (int j = 0; j < dungeonMap.get(0).size(); j++) {
                if (dungeonMap.get(i).get(j) != MAX_INT) continue;
                dungeonMap.get(i).set(j, bfs(dungeonMap, new Coordinate(i, j)));
            }
        }
        return dungeonMap;
    }

    private static int  bfs(List<List<Integer>> dungeonMap, Coordinate start) {
        int distance = 0;
        HashSet<Coordinate> visited = new HashSet<>();
        visited.add(start);
        ArrayDeque<Coordinate> queue = new ArrayDeque<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                Coordinate c = queue.pop();
                if(dungeonMap.get(c.r).get(c.c).equals(0)) {
                    return distance;
                }

                List<Coordinate> neighbors = getNeighbors(dungeonMap, c);
                for (Coordinate neighbor : neighbors) {
                    if(!visited.contains(neighbor)) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                    }
                }
            }
            distance++;
        }
        return MAX_INT;
    }

    private static List<Coordinate> getNeighbors(List<List<Integer>> dungeonMap, Coordinate root){
        int numOfRows = dungeonMap.size();
        int numOfCols = dungeonMap.get(0).size();

        int[] deltaRow = {-1, 0, 1, 0, };
        int[] deltaCol = {0, 1, 0, -1 };

        List<Coordinate> neighbors = new ArrayList<>();
        for (int i = 0; i < deltaCol.length; i++) {
            int rowVal = root.r + deltaRow[i];
            int colVal = root.c + deltaCol[i];
            if(rowVal >= 0 && rowVal < numOfRows && colVal >= 0 && colVal < numOfCols) {
                if(!dungeonMap.get(rowVal).get(colVal).equals(-1)) {
                    neighbors.add(new Coordinate(rowVal, colVal));
                }
            }
        }
        return neighbors;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int dungeonMapLength = Integer.parseInt(scanner.nextLine());
        List<List<Integer>> dungeonMap = new ArrayList<>();
        for (int i = 0; i < dungeonMapLength; i++) {
            dungeonMap.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        scanner.close();
        List<List<Integer>> res = mapGateDistances(dungeonMap);
        for (List<Integer> row : res) {
            System.out.println(row.stream().map(String::valueOf).collect(Collectors.joining(" ")));
        }
    }
}
