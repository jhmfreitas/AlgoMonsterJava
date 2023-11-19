package com.algo.monster.graph;

import java.util.*;

class OpenTheLock {
    public static int numSteps(String targetCombo, List<String> trappedCombos) {
        int count = 0;
        if (targetCombo.equals("0000")) {
            return count;
        }
        ArrayDeque<String> queue = new ArrayDeque<>();
        HashMap<String, Integer> steps = new HashMap<>();
        queue.add("0000");
        steps.put("0000", 0);
        while (!queue.isEmpty()) {
            String node = queue.pop();
            for (String combo : getNeighbors(node, steps, trappedCombos)) {
                queue.add(combo);
                steps.put(combo, steps.get(node) + 1);
                if(combo.equals(targetCombo)) {
                    return steps.get(combo);
                }
            }
        }
        return -1;
    }

    private static List<String> getNeighbors(String root, HashMap<String, Integer> steps, List<String> trappedCombos) {
        List<String> possibilities = new ArrayList<>();
        char[] rootCharArray = root.toCharArray();

        for (int i = 0; i < 4; i++) {
            // This for loop works for -1 and +1 combinations
            for (int j = -1; j <= 1; j += 2) {
                // Will always return a value between 0 and 9
                int result = (Character.getNumericValue(root.charAt(i)) + j + 10) % 10;
                char oldValue = rootCharArray[i];
                // Return decimal corresponding value
                rootCharArray[i] = Character.forDigit(result, 10);
                String possibleCombination = new String(rootCharArray);

                if (!trappedCombos.contains(possibleCombination) && steps.get(possibleCombination) == null) {
                    possibilities.add(possibleCombination);
                }

                // Restores old value
                rootCharArray[i] = oldValue;
            }
        }

        return possibilities;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String targetCombo = scanner.nextLine();
        List<String> trappedCombos = splitWords(scanner.nextLine());
        scanner.close();
        int res = numSteps(targetCombo, trappedCombos);
        System.out.println(res);
    }
}
