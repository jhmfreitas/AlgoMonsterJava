package com.algo.monster.miscellaneous.greedy;


import java.util.*;
import java.util.stream.Collectors;

/**
 * There are a total of n gas stations along a clockwise, circular route, and for the ith gas station (starting from 0),
 * there are gas[i] litre of gas stored.
 *
 * The distance between the ith gas station and the i + 1th gas station is dist[i].
 *
 * You have a car, and you would like to travel clockwise starting from any gas station and return to the same gas station.
 * The car has a sufficiently large gas tank, and for each unit of gas, your car can travel a unit distance.
 * Initially, the fuel tank of your car is empty, but you can add fuel from any fuel station for as long as that fuel station has fuel left.
 *
 * You wonder whether you can successfully travel this way. If so, output the index representing the starting gas station.
 * Otherwise, output -1. The solution is guaranteed to be unique, if one exists.
 *
 */
class GasStation {
    /**
     * It seemed to work and to be more optimized but found a test cases that fails
     *
     * 1 2 3 4 5
     * 5 4 3 2 1
     *
     *  Time Complexity: O(N)
     *  Space Complexity: O(1)
     *
     * @param gas
     * @param dist
     * @return
     */
    public static int startingStation2(List<Integer> gas, List<Integer> dist) {
        int bestDelta = Integer.MAX_VALUE;
        int bestDeltaPosition = 0;

        for (int i = 0; i < gas.size(); i++) {
            int tempBestDelta = deltaToNextPosition(i, gas, dist);
            if(bestDelta != Math.max(tempBestDelta,deltaToNextPosition(bestDeltaPosition, gas, dist))){
                bestDelta = tempBestDelta;
                bestDeltaPosition = i;
            }
        }

        int res = bestDeltaPosition;
        int depositTank = gas.get(bestDeltaPosition) - dist.get(bestDeltaPosition%dist.size());
        int initialPos = (bestDeltaPosition+1)%dist.size();
        for (int j = initialPos; j!=bestDeltaPosition; j = (j + 1) % gas.size()) {
            depositTank += gas.get(j) - dist.get(j);

            if (depositTank < 0) {
                res = -1;
                break;
            }
        }
        return res;
    }
    public static int deltaToNextPosition(int currentPosition, List<Integer> gas, List<Integer> dist) {
        return gas.get(currentPosition) - dist.get(currentPosition%dist.size());
    }


    /**
     * Works for all cases
     *
     * Time Complexity: O(N)
     * Space Complexity: O(1)
     * @param gas
     * @param dist
     * @return
     */
    public static int startingStation(List<Integer> gas, List<Integer> dist) {
        int totalGas = 0;
        int totalDistance = 0;

        int currentGas = 0;
        int position = 0;

        // https://www.youtube.com/watch?v=wDgKaNrSOEI
        for (int i = 0; i < gas.size(); i++) {
            totalGas += gas.get(i);
            totalDistance += dist.get(i);
            currentGas += gas.get(i) - dist.get(i);

            if (currentGas < 0) {
                currentGas = 0;
                position = i + 1;
            }
        }

        if(totalGas < totalDistance) {
            return -1;
        }

        return position;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> gas = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> dist = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        scanner.close();
        int res = startingStation(gas, dist);
        System.out.println(res);
    }
}
