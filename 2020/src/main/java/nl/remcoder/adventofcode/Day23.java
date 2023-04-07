package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class Day23 implements BiAdventOfCodeSolution<Integer, Long> {
    private int moves;

    @Override
    public Integer handlePart1(Stream<String> input) {
        var line = input.findFirst()
                        .orElseThrow(() -> new AssertionError("Eek!"));

        Cup firstCup = null;
        Cup previousCup = null;

        var cups = new HashMap<Integer, Cup>();

        for (var c : line.toCharArray()) {
            int label = c - '0';
            var cup = new Cup(label);

            cups.put(label, cup);

            if (previousCup != null) {
                previousCup.next = cup;
            }
            if (firstCup == null) {
                firstCup = cup;
            }
            cup.previous = previousCup;
            previousCup = cup;
        }

        assert firstCup != null;
        firstCup.previous = previousCup;
        previousCup.next = firstCup;

        playGame(moves, firstCup, cups);

        var stringBuilder = new StringBuilder();

        var cup = cups.get(1).next;

        while (cup.label != 1) {
            stringBuilder.append(cup.label);
            cup = cup.next;
        }

        return Integer.parseInt(stringBuilder.toString());
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var line = input.findFirst()
                        .orElseThrow(() -> new AssertionError("Eek!"));

        Cup firstCup = null;
        Cup previousCup = null;

        var cups = new HashMap<Integer, Cup>();

        for (var c : line.toCharArray()) {
            var label = c - '0';
            var cup = new Cup(label);

            cups.put(label, cup);

            if (previousCup != null) {
                previousCup.next = cup;
            }
            if (firstCup == null) {
                firstCup = cup;
            }
            cup.previous = previousCup;
            previousCup = cup;
        }

        assert previousCup != null;
        for (var cupNumber = 10; cupNumber <= 1000000; cupNumber++) {
            var cup = new Cup(cupNumber);
            cups.put(cupNumber, cup);
            previousCup.next = cup;
            cup.previous = previousCup;
            previousCup = cup;
        }

        firstCup.previous = previousCup;
        previousCup.next = firstCup;

        playGame(10000000, firstCup, cups);

        var oneCup = cups.get(1);

        return (long) oneCup.next.label * (long) oneCup.next.next.label;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    private void playGame(int moves, Cup firstCup, Map<Integer, Cup> cups) {
        var currentCup = firstCup.previous;

        var max = cups.keySet()
                      .stream()
                      .mapToInt(Integer::intValue)
                      .max()
                      .orElseThrow(() -> new AssertionError("Ook!"));

        for (var round = 0; round < moves; round++) {
            currentCup = currentCup.next;

            var firstPickedCup = currentCup.next;

            currentCup.next = currentCup.next.next.next.next;
            currentCup.next.previous = currentCup;

            var nextLabel = currentCup.label - 1;

            if (nextLabel < 1) {
                nextLabel = max;
            }

            var destinationCup = cups.get(nextLabel);

            while (destinationCup == firstPickedCup || destinationCup == firstPickedCup.next ||
                   destinationCup == firstPickedCup.next.next) {
                destinationCup = cups.get(nextLabel--);
                if (nextLabel < 1) {
                    nextLabel = max;
                }
            }

            var tempCup = destinationCup.next;
            destinationCup.next = firstPickedCup;
            firstPickedCup.previous = destinationCup;

            firstPickedCup.next.next.next = tempCup;
            tempCup.previous = firstPickedCup.next.next;
        }
    }

    private static class Cup {
        private final int label;
        private Cup next;
        private Cup previous;

        public Cup(int label) {
            this.label = label;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Cup cup = (Cup) o;
            return label == cup.label;
        }

        @Override
        public int hashCode() {
            return Objects.hash(label);
        }

        @Override
        public String toString() {
            return "Cup{" +
                   "label=" + label +
                   '}';
        }
    }
}
