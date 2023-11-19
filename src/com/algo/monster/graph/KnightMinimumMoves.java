package com.algo.monster.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * On an infinitely large chessboard, a knight is located on [0, 0].
 * A knight can move in eight directions.
 * Given a destination coordinate [x, y], determine the minimum number of moves from [0, 0] to [x, y].
 *
 * Time Complexity: O(|x| * |y|)
 *
 * The computational time is equal the number of visited edges in the worst case.
 * There are 8 edges to each cell, and by the time we reach (x,y), we would have potentially visited most of the cells
 * inside a square of size 4x * 4y.
 * When we drop the constants, the time complexity is bounded by O(|x| * |y|).
 *
 * Space Complexity: O(|x| * |y|)
 *
 * A very generous bound on the maximum number of cells in the queue is O(8^steps).
 * However, since we do not know what steps is, and we avoid duplicate visits to the same coordinate,
 * we can bound the space by the number of cells we visit (time complexity), which is inO(|x| * |y|)
 *
 */
class KnightMinimumMoves {
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

    private static List<Coordinate> getNeighbors(Coordinate coord) {
        ArrayList<Coordinate> res = new ArrayList<>();
        int[] deltaRow = {-2, -2, -1, 1, 2, 2, 1, -1};
        int[] deltaCol = {-1, 1, 2, 2, 1, -1, -2, -2};
        for (int i = 0; i < deltaRow.length; i++) {
            int r = coord.r + deltaRow[i];
            int c = coord.c + deltaCol[i];
            res.add(new Coordinate(r, c));
        }
        return res;
    }

    private static int bfs(Coordinate start, int x, int y) {
        HashSet<Coordinate> visited = new HashSet<>();
        visited.add(start);
        int steps = 0;
        ArrayDeque<Coordinate> queue = new ArrayDeque<>();
        queue.add(start);
        Coordinate target = new Coordinate(x, y);
        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                Coordinate c = queue.pop();
                if(c.equals(target)) {
                    return steps;
                }

                for (Coordinate neighbor : getNeighbors(c)) {
                    if(!visited.contains(neighbor)) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                    }
                }
            }
            steps++;
        }
        return steps;
    }



    public static int getKnightShortestPath(int x, int y) {
        return bfs(new Coordinate(0, 0), x, y);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x = Integer.parseInt(scanner.nextLine());
        int y = Integer.parseInt(scanner.nextLine());
        scanner.close();
        int res = getKnightShortestPath(x, y);
        System.out.println(res);
    }
}
