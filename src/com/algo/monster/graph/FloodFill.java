package com.algo.monster.graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * In computer graphics, an uncompressed raster image is presented as a matrix of numbers. Each entry of the matrix represents the color of a pixel.
 * A flood fill algorithm takes a coordinate r, c and a replacement color, and replaces all pixels connected to r, c that have the same color
 * (i.e., the pixels connected to the given coordinate with same color and all the other pixels connected to the those pixels of the same color)
 * with the replacement color. (e.g. MS-Paint's paint bucket tool).
 *
 * Let r and c represent the number of rows and columns respectively. We have O(r * c) vertices and also O(r * c) edges.
 *
 * This gives us a time and space complexity of O(r * c).
 */
class FloodFill {
    public static class Coordinate {
        int r;
        int c;

        public Coordinate(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
    public static List<List<Integer>> floodFill(int r, int c, int replacement, List<List<Integer>> image) {
        return bfs(image, r, c , replacement);
    }

    private static List<List<Integer>> bfs(List<List<Integer>> image, int r, int c, int replacement) {
        int numOfRows = image.size();
        int numOfCols = image.get(0).size();
        Integer pixelValue = image.get(r).get(c);
        image.get(r).set(c, replacement);
        ArrayDeque<Coordinate> queue = new ArrayDeque<>();
        queue.add(new Coordinate(r, c));
        boolean[][] visited = new boolean[numOfRows][numOfCols];
        visited[r][c] = true;
        while (!queue.isEmpty()) {
            Coordinate node = queue.pop();
            for (Coordinate coordinate : getNeighbors(image, node.r, node.c, numOfRows, numOfCols, pixelValue)) {
                if(!visited[coordinate.r][coordinate.c]){
                    image.get(coordinate.r).set(coordinate.c, replacement);
                    queue.add(coordinate);
                    visited[coordinate.r][coordinate.c] = true;
                }
            }
        }

        return image;
    }

    private static List<Coordinate> getNeighbors(List<List<Integer>> graph, int r, int c, int numOfRows, int numOfCols, int pixelColor){
        int[] deltaRow = {-1, 0, 1, 0, };
        int[] deltaCol = {0, 1, 0, -1 };

        List<Coordinate> neighbors = new ArrayList<>();
        for (int i = 0; i < deltaCol.length; i++) {
            int rowVal = r + deltaRow[i];
            int colVal = c + deltaCol[i];
            if(rowVal >= 0 && rowVal < numOfRows && colVal >= 0 && colVal < numOfCols) {
                if(graph.get(rowVal).get(colVal).equals(pixelColor)) {
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
        int r = Integer.parseInt(scanner.nextLine());
        int c = Integer.parseInt(scanner.nextLine());
        int replacement = Integer.parseInt(scanner.nextLine());
        int imageLength = Integer.parseInt(scanner.nextLine());
        List<List<Integer>> image = new ArrayList<>();
        for (int i = 0; i < imageLength; i++) {
            image.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        scanner.close();
        List<List<Integer>> res = floodFill(r, c, replacement, image);
        for (List<Integer> row : res) {
            System.out.println(row.stream().map(String::valueOf).collect(Collectors.joining(" ")));
        }
    }
}
