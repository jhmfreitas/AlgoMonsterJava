package com.algo.monster.graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Given a 2-dimensional matrix of values with 0 and 1, count the number of islands of 1.
 * An island consists of all value 1 cells and is surrounded by either an edge or all 0 cells.
 * A cell can only be adjacent to each other horizontally or vertically, not diagonally.
 *
 * Time Complexity: O(r*c)
 *
 * Similar to before, the time complexity is equal to the size of the matrix itself in the worst case.
 * The size of the matrix is denoted by the number of rows times the number of columns so therefore we have r*c nodes in our graph.
 * The number of edges for a given cell is 4 (except boundary cells whose edges are < 4). And constants are dropped in the time complexity notation.
 *
 * Space Complexity: O(r+c)
 *
 * The amount of space we use is determined by the number of nodes in the queue at once.
 * Since we start on the outer layer of the grid and we move towards the inside nodes layer by layer, the maximum number
 * of nodes inside the queue is at most twice the number of nodes in the outermost layer, which is given by 2r+2c-4 and in O(r+c).
 *
 */
class FindNumberOfIslands {
    public static class Coordinate {
        int r;
        int c;

        public Coordinate(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
    public static int countNumberOfIslands(List<List<Integer>> grid) {
        int count = 0;
        // bfs starting from each unvisited land cell
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                if (grid.get(i).get(j) == 0) continue;
                bfs(grid, i, j);
                count++;
            }
        }
        return count;
    }
    private static void bfs(List<List<Integer>> grid, int r, int c) {
        int numOfRows = grid.size();
        int numOfCols = grid.get(0).size();
        ArrayDeque<Coordinate> queue = new ArrayDeque<>();
        queue.add(new Coordinate(r,c));
        grid.get(r).set(c, 0);
        while (!queue.isEmpty()) {
            Coordinate node = queue.pop();
            for (Coordinate coordinate : getNeighbors(node.r, node.c, numOfRows, numOfCols)) {
                if(!grid.get(coordinate.r).get(coordinate.c).equals(0)){
                    grid.get(coordinate.r).set(coordinate.c, 0);
                    queue.add(coordinate);
                }
            }
        }
    }

    private static List<Coordinate> getNeighbors(int r, int c, int numOfRows, int numOfCols){
        int[] deltaRow = {-1, 0, 1, 0, };
        int[] deltaCol = {0, 1, 0, -1 };

        List<Coordinate> neighbors = new ArrayList<>();
        for (int i = 0; i < deltaCol.length; i++) {
            int rowVal = r + deltaRow[i];
            int colVal = c + deltaCol[i];
            if(rowVal >= 0 && rowVal < numOfRows && colVal >= 0 && colVal < numOfCols) {
                neighbors.add(new Coordinate(rowVal, colVal));
            }
        }
        return neighbors;
    }


    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int gridLength = Integer.parseInt(scanner.nextLine());
        List<List<Integer>> grid = new ArrayList<>();
        for (int i = 0; i < gridLength; i++) {
            grid.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        scanner.close();
        int res = countNumberOfIslands(grid);
        System.out.println(res);
    }
}
