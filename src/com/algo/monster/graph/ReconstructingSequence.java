package com.algo.monster.graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A sequence s is a list of integers. Its subsequence is a new sequence that can be made up by deleting elements from s,
 * without changing the order of integers.
 *
 * We are given an original sequence and a list of subsequences seqs.
 *
 * Determine whether original is the only sequence that can be reconstructed from seqs. Reconstruction means building
 * the shortest sequence so that all sequences in seqs are subsequences of it.
 *
 * Parameters
 * original: a list of integers of size n representing the original sequence.
 * seqs: a list of sequences of size m representing the sequences to be reconstructed.
 * Result
 * true or false, depending on whether the original sequence can be uniquely reconstructed.
 *
 * Time Complexity: O(n+m)
 *
 * Since we are doing a topological sort the time complexity is equal to the number of edges + number of nodes.
 * We define n to be number of nodes in our graph and m the number of edges for O(n+m) time complexity.
 *
 * Space Complexity: O(n)
 *
 * The queue holds at most n nodes in the worst case.
 *
 */
class ReconstructingSequence {
    public static boolean sequenceReconstruction(List<Integer> original, List<List<Integer>> seqs) {
        HashMap<Integer, Set<Integer>> graph = new HashMap<>();
        original.forEach(n -> graph.put(n, new HashSet<>()));
        seqs.forEach(seq -> {
            for (int i = 1; i < seq.size(); i++) {
                for (int j = 0; j < i; j++) {
                    Set<Integer> neighbors = graph.get(seq.get(j));
                    neighbors.add(seq.get(i));
                    graph.put(seq.get(j), neighbors);
                }
            }
        });
        return topoSort(graph, original);
    }

    private static boolean topoSort(HashMap<Integer, Set<Integer>> graph, List<Integer> original) {
        List<Integer> reconstructedSeq = new ArrayList<>();
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        HashMap<Integer, Integer> inDegrees = findInDegrees(graph);
        inDegrees.entrySet().stream().filter(entry -> entry.getValue() == 0).forEach(e -> queue.add(e.getKey()));

        while (!queue.isEmpty()) {
            if(queue.size() > 1) {
                return false;
            }

            Integer node = queue.poll();
            reconstructedSeq.add(node);
            for (Integer neighbor : graph.get(node)) {
                inDegrees.put(neighbor, inDegrees.get(neighbor) - 1);
                if(inDegrees.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        return reconstructedSeq.equals(original);
    }

    private static HashMap<Integer, Integer> findInDegrees(HashMap<Integer, Set<Integer>> graph) {
        HashMap<Integer, Integer> inDegrees = new HashMap<>();
        graph.keySet().forEach(node -> {
            inDegrees.put(node, 0);
        });
        graph.forEach((key, value) -> {
            for (Integer neighbor : value) {
                inDegrees.put(neighbor, inDegrees.get(neighbor) + 1);
            }
        });
        return inDegrees;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> original = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        int seqsLength = Integer.parseInt(scanner.nextLine());
        List<List<Integer>> seqs = new ArrayList<>();
        for (int i = 0; i < seqsLength; i++) {
            seqs.add(splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList()));
        }
        scanner.close();
        boolean res = sequenceReconstruction(original, seqs);
        System.out.println(res);
    }
}
