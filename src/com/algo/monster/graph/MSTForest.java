package com.algo.monster.graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The Umbristan Department of Forestry(UDF) is tackling a rather difficult problem and the Umbristan government has
 * detached you one of its best workers to go resolve the issue. When you arrive you are quickly briefed on the problem
 * at hand. Inside the Umbristan National Park there exists an area that has been closed off as fencing needs to be erected
 * in the area. Your job is to split the area into different regions by connecting fences to the trees.
 * The department has set up some rules for the fences
 *
 * The department has counted the number of trees in the area as well as determined all possible tree pairs where a fence
 * can be built between the pair.
 *
 * The fences needs to be set up such that every tree in the area is connected to a fence.
 *
 * The fences should be connected to one another unless a connecting fence is unable to be built (you should be able to
 * visit all the trees by walking along the fence, except when the it's impossible using the list provided by the department)
 *
 * The department is on a very strict operating budget, so you need to minimize the metres of fencing required.
 *
 * Can you help them figure out the smallest amount of fencing required?
 *
 * It is possible that not all the nodes will be connected to one another depending on the tree pairs. Input will consist
 * of trees the number of trees in the area labelled from 1 to n as well as pairs, a list consisting of the tuple [a, b, d]
 * which denotes that a fence can be built between the trees a and b that will be d metres in length.
 *
 * Time Complexity: O(m*log(n))
 *
 * Space Complexity: O(n)
 *
 */
class MSTForest {
    public static class UnionFind<T> {
        private HashMap<T, T> id = new HashMap<>();

        public T find(T x) {
            T y = id.getOrDefault(x, x);
            if (y != x) {
                y = find(y);
                id.put(x, y);
            }
            return y;
        }

        public void union(T x, T y) {
            id.put(find(x), find(y));
        }
    }


    public static int mstForest(int trees, List<List<Integer>> pairs) {
        pairs.sort(Comparator.comparingInt(list -> list.get(2)));

        UnionFind<Integer> dsu = new UnionFind<>();
        int ret = 0;
        int count = 0;
        for (List<Integer> edge: pairs) {
            Integer start = edge.get(0);
            Integer end = edge.get(1);
            Integer edgeWeight = edge.get(2);
            if(!Objects.equals(dsu.find(start), dsu.find(end))) {
                dsu.union(start, end);
                ret += edgeWeight;
                count++;
                if(count == trees - 1)
                    break;
            }
        }
        return ret;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int trees = Integer.parseInt(scanner.nextLine());
        int pairsLength = Integer.parseInt(scanner.nextLine());
        List<List<Integer>> pairs = new ArrayList<>();
        for (int i = 0; i < pairsLength; i++) {
            pairs.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        scanner.close();
        int res = mstForest(trees, pairs);
        System.out.println(res);
    }
}
