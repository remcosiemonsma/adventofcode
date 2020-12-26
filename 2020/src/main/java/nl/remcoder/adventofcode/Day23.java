package nl.remcoder.adventofcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class Day23 {

    public int handlePart1(Stream<String> input, int moves) {
        String line = input.findFirst().get();

        Cup firstCup = null;
        Cup previousCup = null;

        Map<Integer, Cup> cups = new HashMap<>();

        for (char c : line.toCharArray()) {
            int label = c - '0';
            Cup cup = new Cup(label);

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

        firstCup.previous = previousCup;
        previousCup.next = firstCup;

        playGame(moves, firstCup, cups);

        StringBuilder stringBuilder = new StringBuilder();

        Cup cup = cups.get(1).next;

        while (cup.label != 1) {
            stringBuilder.append(cup.label);
            cup = cup.next;
        }

        return Integer.parseInt(stringBuilder.toString());
    }

    public long handlePart2(Stream<String> input) {
        String line = input.findFirst().get();

        Cup firstCup = null;
        Cup previousCup = null;

        Map<Integer, Cup> cups = new HashMap<>();

        for (char c : line.toCharArray()) {
            int label = c - '0';
            Cup cup = new Cup(label);

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

        for (int cupNumber = 10; cupNumber <= 1000000; cupNumber++) {
            Cup cup = new Cup(cupNumber);
            cups.put(cupNumber, cup);
            previousCup.next = cup;
            cup.previous = previousCup;
            previousCup = cup;
        }

        firstCup.previous = previousCup;
        previousCup.next = firstCup;

        playGame(10000000, firstCup, cups);

        Cup oneCup = cups.get(1);

        return (long) oneCup.next.label * (long) oneCup.next.next.label;
    }

    private void playGame(int moves, Cup firstCup, Map<Integer, Cup> cups) {
        Cup currentCup = firstCup.previous;

        int max = cups.keySet().stream().mapToInt(Integer::intValue).max().getAsInt();

        for (int round = 0; round < moves; round++) {
            currentCup = currentCup.next;

            Cup firstPickedCup = currentCup.next;

            currentCup.next = currentCup.next.next.next.next;
            currentCup.next.previous = currentCup;

            int nextLabel = currentCup.label - 1;

            if (nextLabel < 1) {
                nextLabel = max;
            }

            Cup destinationCup = cups.get(nextLabel);

            while (destinationCup == firstPickedCup || destinationCup == firstPickedCup.next || destinationCup == firstPickedCup.next.next) {
                destinationCup = cups.get(nextLabel--);
                if (nextLabel < 1) {
                    nextLabel = max;
                }
            }

            Cup tempCup = destinationCup.next;
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
