package com.algo.monster.ood;

import java.util.Scanner;
import java.util.*;
import java.util.stream.Collectors;

class PlayingCards {

    public static abstract class Card implements Comparable<Card>{

        public abstract int getValue();

        @Override
        public int compareTo(Card o) {
            return Integer.compare(this.getValue(), o.getValue());
        }
    }

    public static class Game {
        public Card[] cards;
        public static List<Hand> hands = new ArrayList<Hand>();

        public int i = 0;
        public Game() {
            cards = new Card[52];
        }

        public void addCard(String suit, String value) {
            cards[i] = new PlayingCard(suit, value);
            i++;
        }

        public String cardString(int card) {
            return cards[card].toString();
        }

        public boolean cardBeats(int cardA, int cardB) {
            Card cardOne = cards[cardA];
            Card cardTwo = cards[cardB];
            return cardOne.compareTo(cardTwo) > 0;
        }

        public void addJoker(String color) {
            cards[i] = new JokerCard(Color.valueOf(color));
            i++;
        }

        public void addHand(List<Integer> cardIndices) {
            hands.add(new Hand(cardIndices.stream().map(i -> cards[i]).collect(Collectors.toList())));
        }

        public String handString(int hand) {
            return hands.get(hand).toString();
        }

        public static boolean handBeats(int handA, int handB) {
            Hand firstHand = hands.get(handA);
            Hand secondHand = hands.get(handB);

            List<Card> firstHandCards = firstHand.getCards();
            firstHandCards.sort(Card::compareTo);
            firstHandCards.sort(Comparator.reverseOrder());

            List<Card> secondHandCards = secondHand.getCards();
            secondHandCards.sort(Card::compareTo);
            secondHandCards.sort(Comparator.reverseOrder());

            int i = 0;
            while (firstHandCards.size() > i+1 && secondHandCards.size() > i+1 && firstHandCards.get(i).compareTo(secondHandCards.get(i)) == 0 ){
                i++;
            }

            return firstHandCards.get(i).compareTo(secondHandCards.get(i)) > 0;
        }
    }

    public enum Suit {
        SPADES, HEARTS, DIAMONDS, CLUBS
    }

    public enum Color {
        Red, Black
    }

    static class Hand {
        private List<Card> cards;

        public Hand(List<Card> cards){
            this.cards = cards;
        }

        public List<Card> getCards() {
            return cards;
        }

        @Override
        public String toString() {
            return this.cards.stream().map(String::valueOf).collect(Collectors.joining(", "));
        }

    }

    static class PlayingCard extends Card{
        private Suit suit;
        private int value;

        public static Map<String, Integer> CARD_VALUES = new HashMap<>() {{
            this.put("A", 1);
            for (int i=2 ; i<= 10;i++){
                this.put(String.valueOf(i), i);
            }
            this.put("J", 11);
            this.put("Q", 12);
            this.put("K", 13);
        }};
        public static Map<Integer, String> VALUE_NAMES = CARD_VALUES.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

        public static final Map<String, Suit> CARD_SUITS = Map.of(
                "Spades", Suit.SPADES,
                "Hearts", Suit.HEARTS,
                "Diamonds", Suit.DIAMONDS,
                "Clubs", Suit.CLUBS
        );
        public static Map<Suit, String> SUIT_NAMES = CARD_SUITS.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));


        public PlayingCard(String suit, String value){
            this.suit = CARD_SUITS.get(suit);
            this.value = CARD_VALUES.get(value);
        }

        public Suit getSuit() {
            return this.suit;
        }

        @Override
        public int getValue() {
            return this.value;
        }

        @Override
        public String toString() {
            return String.format("%s of %s", VALUE_NAMES.get(this.getValue()), SUIT_NAMES.get(this.getSuit()));
        }
    }

    static class JokerCard extends Card {

        private Color jokerColor;

        public JokerCard(Color color) {
            this.jokerColor = color;
        }

        @Override
        public int getValue() {
            // Value high enough to be bigger than any value
            return 52;
        }

        @Override
        public String toString() {
            return String.format("%s Joker", this.jokerColor);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = new Game();
        ArrayList<Integer> handAList = new ArrayList<>();
        int listALength = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < listALength; i++) {
            String[] segs = scanner.nextLine().split(" ");
            if (segs[0].equals("Joker"))
                game.addJoker(segs[1]);
            else
                game.addCard(segs[0], segs[1]);
            handAList.add(i);
        }
        game.addHand(handAList);
        System.out.println(game.handString(0));
        ArrayList<Integer> handBList = new ArrayList<>();
        int listBLength = Integer.parseInt(scanner.nextLine());
        for (int i = listALength; i < listALength + listBLength; i++) {
            String[] segs = scanner.nextLine().split(" ");
            if (segs[0].equals("Joker"))
                game.addJoker(segs[1]);
            else
                game.addCard(segs[0], segs[1]);
            handBList.add(i);
        }
        game.addHand(handBList);
        System.out.println(game.handString(1));
        System.out.println(game.handBeats(0, 1));
        scanner.close();
    }
}

