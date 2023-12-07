package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.stream.CountingCollector;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day7 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var hands = input.map(this::mapToHand)
                         .sorted()
                         .toList();

        var score = 0;

        for (int i = 0; i < hands.size(); i++) {
            var hand = hands.get(i);

            score += hand.bid * (hands.size() - i);

//            System.out.println("Winnings for hand %s are %d".formatted(hand, hand.bid * (hands.size() - i)));
        }

        return score;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var hands = input.map(this::mapToHand2)
                         .sorted()
                         .toList();

        var score = 0;

        for (int i = 0; i < hands.size(); i++) {
            var hand = hands.get(i);

            score += hand.bid * (hands.size() - i);

//            System.out.println("Winnings for hand %s are %d".formatted(hand, hand.bid * (hands.size() - i)));
        }

        return score;
    }

    private Hand mapToHand(String line) {
        var split = line.split(" ");
        var cards = split[0].chars().mapToObj(i -> (char) i).map(Card::new).toList();
        var bid = Integer.parseInt(split[1]);
        var type = determineType(cards);

        return new Hand(cards, type, bid);
    }

    private Hand2 mapToHand2(String line) {
        var split = line.split(" ");
        var cards = split[0].chars().mapToObj(i -> (char) i).map(Card2::new).toList();
        var bid = Integer.parseInt(split[1]);
        var type = determineType2(cards);

        return new Hand2(cards, type, bid);
    }

    private Type determineType(List<Card> cards) {
        Map<Character, Integer> count = cards.stream()
                                             .map(Card::value)
                                             .collect(new CountingCollector<>());

        if (count.size() == 1) {
            return Type.FIVE_OF_A_KIND;
        } else if (count.size() == 2) {
            if (count.values().stream().anyMatch(value -> value == 4)) {
                return (Type.FOUR_OF_A_KIND);
            } else {
                return Type.FULL_HOUSE;
            }
        } else if (count.size() == 3) {
            if (count.values().stream().anyMatch(value -> value == 3)) {
                return Type.THREE_OF_A_KIND;
            } else {
                return Type.TWO_PAIR;
            }
        } else if (count.size() == 4) {
            return Type.ONE_PAIR;
        } else {
            return Type.HIGH_CARD;
        }
    }

    private Type determineType2(List<Card2> cards) {
        Map<Character, Integer> count = cards.stream()
                                             .map(Card2::value)
                                             .collect(new CountingCollector<>());

        if (count.containsKey('J')) {
            var amount = count.remove('J');
            if (count.size() == 1 || count.isEmpty()) {
                return Type.FIVE_OF_A_KIND;
            } else if (count.size() == 2) {
                if (count.values().stream().anyMatch(value -> value == 4 - amount)) {
                    return (Type.FOUR_OF_A_KIND);
                } else {
                    return Type.FULL_HOUSE;
                }
            } else if (count.size() == 3) {
                if (count.values().stream().anyMatch(value -> value == 3 - amount)) {
                    return Type.THREE_OF_A_KIND;
                } else {
                    return Type.TWO_PAIR;
                }
            } else {
                return Type.ONE_PAIR;
            }
        } else {
            if (count.size() == 1) {
                return Type.FIVE_OF_A_KIND;
            } else if (count.size() == 2) {
                if (count.values().stream().anyMatch(value -> value == 4)) {
                    return (Type.FOUR_OF_A_KIND);
                } else {
                    return Type.FULL_HOUSE;
                }
            } else if (count.size() == 3) {
                if (count.values().stream().anyMatch(value -> value == 3)) {
                    return Type.THREE_OF_A_KIND;
                } else {
                    return Type.TWO_PAIR;
                }
            } else if (count.size() == 4) {
                return Type.ONE_PAIR;
            } else {
                return Type.HIGH_CARD;
            }
        }
    }

    private record Hand(List<Card> cards, Type type, int bid) implements Comparable<Hand> {
        @Override
        public int compareTo(Hand o) {
            if (this.type == o.type) {
                if (cards.get(0).compareTo(o.cards.get(0)) != 0) {
                    return cards.get(0).compareTo(o.cards.get(0));
                } else if (cards.get(1).compareTo(o.cards.get(1)) != 0) {
                    return cards.get(1).compareTo(o.cards.get(1));
                } else if (cards.get(2).compareTo(o.cards.get(2)) != 0) {
                    return cards.get(2).compareTo(o.cards.get(2));
                } else if (cards.get(3).compareTo(o.cards.get(3)) != 0) {
                    return cards.get(3).compareTo(o.cards.get(3));
                } else if (cards.get(4).compareTo(o.cards.get(4)) != 0) {
                    return cards.get(4).compareTo(o.cards.get(4));
                } else {
                    return 0;
                }
            } else {
                return this.type.compareTo(o.type);
            }
        }
    }

    private record Hand2(List<Card2> cards, Type type, int bid) implements Comparable<Hand2> {
        @Override
        public int compareTo(Hand2 o) {
            if (this.type == o.type) {
                if (cards.get(0).compareTo(o.cards.get(0)) != 0) {
                    return cards.get(0).compareTo(o.cards.get(0));
                } else if (cards.get(1).compareTo(o.cards.get(1)) != 0) {
                    return cards.get(1).compareTo(o.cards.get(1));
                } else if (cards.get(2).compareTo(o.cards.get(2)) != 0) {
                    return cards.get(2).compareTo(o.cards.get(2));
                } else if (cards.get(3).compareTo(o.cards.get(3)) != 0) {
                    return cards.get(3).compareTo(o.cards.get(3));
                } else if (cards.get(4).compareTo(o.cards.get(4)) != 0) {
                    return cards.get(4).compareTo(o.cards.get(4));
                } else {
                    return 0;
                }
            } else {
                return this.type.compareTo(o.type);
            }
        }
    }

    private record Card(Character value) implements Comparable<Card> {

        @Override
        public int compareTo(Card o) {
            if (this == o) {
                return 0;
            } else if (this.value.equals(o.value)) {
                return 0;
            } else if (!Character.isDigit(value) && Character.isDigit(o.value)) {
                return -1;
            } else if (Character.isDigit(value) && !Character.isDigit(o.value)) {
                return 1;
            } else if (Character.isDigit(value) && Character.isDigit(o.value)){
                return o.value.compareTo(value);
            } else {
                if (this.value == 'A') {
                    return -1;
                } else if (this.value == 'K' && o.value == 'A') {
                    return 1;
                } else if (this.value == 'K') {
                    return -1;
                } else if (this.value == 'Q' && (o.value == 'A' || o.value == 'K')) {
                    return 1;
                } else if (this.value == 'Q') {
                    return -1;
                } else if (this.value =='J' && (o.value == 'A' || o.value == 'K'|| o.value =='Q')){
                    return 1;
                } else if (this.value =='J') {
                    return -1;
                } else {
                    return 1;
                }
            }
        }

        @Override
        public String toString() {
            return "" + value;
        }
    }

    private record Card2(Character value) implements Comparable<Card2> {

        @Override
        public int compareTo(Card2 o) {
            if (this == o) {
                return 0;
            } else if (this.value.equals(o.value)) {
                return 0;
            } else if (this.value == 'J') {
                return 1;
            } else if (o.value == 'J') {
                return -1;
            } else if (!Character.isDigit(value) && Character.isDigit(o.value)) {
                return -1;
            } else if (Character.isDigit(value) && !Character.isDigit(o.value)) {
                return 1;
            } else if (Character.isDigit(value) && Character.isDigit(o.value)){
                return o.value.compareTo(value);
            } else {
                if (this.value == 'A') {
                    return -1;
                } else if (this.value == 'K' && o.value == 'A') {
                    return 1;
                } else if (this.value == 'K') {
                    return -1;
                } else if (this.value == 'Q' && (o.value == 'A' || o.value == 'K')) {
                    return 1;
                } else if (this.value == 'Q') {
                    return -1;
                } else {
                    return 1;
                }
            }
        }

        @Override
        public String toString() {
            return "" + value;
        }
    }

    public enum Type {
        FIVE_OF_A_KIND,
        FOUR_OF_A_KIND,
        FULL_HOUSE,
        THREE_OF_A_KIND,
        TWO_PAIR,
        ONE_PAIR,
        HIGH_CARD
    }
}
