package com.algo.monster.miscellaneous.divideandconquer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**

 * Time Complexity: O(nlog(n))
 *
 * Space Complexity: O(1) if we merge in-place, O(n) if we make copies of the array during the recursive calls.
 */
class SkylineProblem {
    public static List<List<Integer>> result = new ArrayList<List<Integer>>();

    public static List<List<Integer>> getSkyline(List<List<Integer>> buildings) {
        int l = buildings.size();
        if(l == 0) return List.of();
        if(l == 1) return List.of(List.of(buildings.get(0).get(0), buildings.get(0).get(2)), List.of(buildings.get(0).get(1), 0));
        List<List<Integer>> leftSplit = new ArrayList<List<Integer>>();
        List<List<Integer>> rightSplit = new ArrayList<List<Integer>>();
        for (int i = 0; i < l; i++) {
            if (i < l / 2) leftSplit.add(buildings.get(i));
            else rightSplit.add(buildings.get(i));
        }
        List<List<Integer>> leftBuildings = getSkyline(leftSplit);
        List<List<Integer>> rightBuildings = getSkyline(rightSplit);
        return merge(leftBuildings, rightBuildings);
    }

    private static List<List<Integer>> merge(List<List<Integer>> left, List<List<Integer>> right) {
        result.clear();
        int curx = 0;
        int cury = 0;
        int i1 = 0;
        int i2 = 0;
        int ly = 0;
        int ry = 0;
        while (i1 < left.size() && i2 < right.size()) {
            int x1 = left.get(i1).get(0);
            int y1 = left.get(i1).get(1);
            int x2 = right.get(i2).get(0);
            int y2 = right.get(i2).get(1);
            if (x1 < x2) {
                i1++;
                ly = y1;
            } else {
                i2++;
                ry = y2;
            }
            curx = Math.min(x1, x2);
            cury = Math.max(ly, ry);
            update(curx, cury);
        }
        while (i1 < left.size()){
            int x1 = left.get(i1).get(0);
            int y1 = left.get(i1).get(1);
            i1++;
            update(x1, y1);
        }
        while (i2 < right.size()){
            int x2 = right.get(i2).get(0);
            int y2 = right.get(i2).get(1);
            i2++;
            update(x2, y2);
        }
        List<List<Integer>> temp = new ArrayList<List<Integer>>(result);
        return temp;
    }

    private static void update(int x, int y) {
        if(result.isEmpty() || (result.get(result.size() - 1).get(0) != x && result.get(result.size() - 1).get(1) != y)) {
            result.add(Arrays.asList(x,y));
        } else {
            result.get(result.size() - 1).set(1, y);
        }
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int buildingsLength = Integer.parseInt(scanner.nextLine());
        List<List<Integer>> buildings = new ArrayList<>();
        for (int i = 0; i < buildingsLength; i++) {
            buildings.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        scanner.close();
        List<List<Integer>> res = getSkyline(buildings);
        for (List<Integer> row : res) {
            System.out.println(row.stream().map(String::valueOf).collect(Collectors.joining(" ")));
        }
    }
}
