package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.stream.CountingCollector;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class Day7 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var hands = input.map(line -> mapToHand(line, this::determineType))
                         .sorted(new HandComparator(new CardComparator1()))
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
        var hands = input.map(line -> mapToHand(line, this::determineType2))
                         .sorted(new HandComparator(new CardComparator2()))
                         .toList();

        var score = 0;

        for (int i = 0; i < hands.size(); i++) {
            var hand = hands.get(i);

            score += hand.bid * (hands.size() - i);

//            System.out.println("Winnings for hand %s are %d".formatted(hand, hand.bid * (hands.size() - i)));
        }

        return score;
    }

    private Hand mapToHand(String line, Function<List<Card>, Type> typeFunction) {
        var split = line.split(" ");
        var cards = split[0].chars().mapToObj(i -> (char) i).map(Card::new).toList();
        var bid = Integer.parseInt(split[1]);
        var type = typeFunction.apply(cards);

        return new Hand(cards, type, bid);
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

    private Type determineType2(List<Card> cards) {
        Map<Character, Integer> count = cards.stream()
                                             .map(Card::value)
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

    private record Hand(List<Card> cards, Type type, int bid) {}

    private record Card(Character value) {
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

    private static class HandComparator implements Comparator<Hand> {
        private final Comparator<Card> cardComparator;

        private HandComparator(Comparator<Card> cardComparator) {
            this.cardComparator = cardComparator;
        }

        @Override
        public int compare(Hand o1, Hand o2) {
            if (o1.type() == o2.type()) {
                if (cardComparator.compare(o1.cards().get(0), o2.cards().get(0)) != 0) {
                    return cardComparator.compare(o1.cards().get(0), o2.cards().get(0));
                } else if (cardComparator.compare(o1.cards().get(1), o2.cards().get(1)) != 0) {
                    return cardComparator.compare(o1.cards().get(1), o2.cards().get(1));
                } else if (cardComparator.compare(o1.cards().get(2), o2.cards().get(2)) != 0) {
                    return cardComparator.compare(o1.cards().get(2), o2.cards().get(2));
                } else if (cardComparator.compare(o1.cards().get(3), o2.cards().get(3)) != 0) {
                    return cardComparator.compare(o1.cards().get(3), o2.cards().get(3));
                } else if (cardComparator.compare(o1.cards().get(4), o2.cards().get(4)) != 0) {
                    return cardComparator.compare(o1.cards().get(4), o2.cards().get(4));
                } else {
                    return 0;
                }
            } else {
                return o1.type().compareTo(o2.type());
            }
        }
    }

    private static class CardComparator1 implements Comparator<Card> {
        @Override
        public int compare(Card o1, Card o2) {
            if (o1 == o2) {
                return 0;
            } else if (o1.value().equals(o2.value())) {
                return 0;
            } else if (!Character.isDigit(o1.value()) && Character.isDigit(o2.value())) {
                return -1;
            } else if (Character.isDigit(o1.value()) && !Character.isDigit(o2.value())) {
                return 1;
            } else if (Character.isDigit(o1.value()) && Character.isDigit(o2.value())) {
                return o2.value().compareTo(o1.value());
            } else {
                if (o1.value() == 'A') {
                    return -1;
                } else if (o1.value() == 'K' && o2.value() == 'A') {
                    return 1;
                } else if (o1.value() == 'K') {
                    return -1;
                } else if (o1.value() == 'Q' && (o2.value() == 'A' || o2.value() == 'K')) {
                    return 1;
                } else if (o1.value() == 'Q') {
                    return -1;
                } else if (o1.value() == 'J' && (o2.value() == 'A' || o2.value() == 'K' || o2.value() == 'Q')) {
                    return 1;
                } else if (o1.value() == 'J') {
                    return -1;
                } else {
                    return 1;
                }
            }
        }
    }

    private static class CardComparator2 implements Comparator<Card> {
        @Override
        public int compare(Card o1, Card o2) {
            if (o1 == o2) {
                return 0;
            } else if (o1.value().equals(o2.value())) {
                return 0;
            } else if (o1.value() == 'J') {
                return 1;
            } else if (o2.value() == 'J') {
                return -1;
            } else if (!Character.isDigit(o1.value()) && Character.isDigit(o2.value())) {
                return -1;
            } else if (Character.isDigit(o1.value()) && !Character.isDigit(o2.value())) {
                return 1;
            } else if (Character.isDigit(o1.value()) && Character.isDigit(o2.value())) {
                return o2.value().compareTo(o1.value());
            } else {
                if (o1.value() == 'A') {
                    return -1;
                } else if (o1.value() == 'K' && o2.value() == 'A') {
                    return 1;
                } else if (o1.value() == 'K') {
                    return -1;
                } else if (o1.value() == 'Q' && (o2.value() == 'A' || o2.value() == 'K')) {
                    return 1;
                } else if (o1.value() == 'Q') {
                    return -1;
                } else {
                    return 1;
                }
            }
        }
    }
}
