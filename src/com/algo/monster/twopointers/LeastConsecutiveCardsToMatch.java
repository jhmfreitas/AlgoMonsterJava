package com.algo.monster.twopointers;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A bunch of cards is laid out in front of you in a line, where the value of each card ranges from 0 to 10^6.
 * A pair of cards are matching if they have the same number value.
 *
 * Given a list of integer cards, your goal is to match a pair of cards, but you can only pick up cards in a consecutive manner.
 * What's the minimum number of cards that you need to pick up to make a pair? If there is no matching pairs, return -1.
 *
 * For example, given cards = [3, 4, 2, 3, 4, 7], then picking up [3, 4, 2, 3] makes a pair of 3s and picking up [4, 2, 3, 4]
 * matches two 4s. We need 4 consecutive cards to match a pair of 3s and 4 consecutive cards to match 4s, so you need to pick up at least 4 cards to make a match.
 *
 */
class LeastConsecutiveCardsToMatch {
    /**
     * O(N^2)
     * @param cards
     * @return
     */
    public static int leastConsecutiveCardsToMatch(List<Integer> cards) {
        int shortest = cards.size() + 1;
        for (int right = 0; right < cards.size(); right++) {
            for (int left = 0; left < right; left++) {
                if(Objects.equals(cards.get(right), cards.get(left))) {
                    shortest = Math.min(shortest, right - left + 1);
                }
            }
        }
        return shortest == cards.size() + 1 ? -1 :shortest;
    }

    /**
     * O(N)
     * @param cards
     * @return
     */
    public static int leastConsecutiveCardsToMatch2(List<Integer> cards) {
        HashMap<Integer, Integer> window = new HashMap<Integer, Integer>();
        int left = 0, shortest = cards.size()+1;
        for (int right = 0; right < cards.size(); ++right) {
            window.put(cards.get(right), window.getOrDefault(cards.get(right), 0) + 1);
            while (window.get(cards.get(right)) == 2) {
                shortest = Math.min(shortest, right-left+1);
                window.put(cards.get(left), window.get(cards.get(left)) - 1);
                ++left;
            }
        }
        return (shortest != cards.size()+1) ? shortest : -1;
    }

    public static List<String> splitWords(String s) {
        return s.isEmpty() ? List.of() : Arrays.asList(s.split(" "));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> cards = splitWords(scanner.nextLine()).stream().map(Integer::parseInt).collect(Collectors.toList());
        scanner.close();
        int res = leastConsecutiveCardsToMatch(cards);
        System.out.println(res);
    }
}
