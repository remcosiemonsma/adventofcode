package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.HashMap;
import java.util.stream.Stream;

public class Day15 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var split = input.findFirst()
                         .map(s -> s.split(","))
                         .orElseThrow(() -> new AssertionError("Eek!"));

        var initialLoad = new int[split.length];

        var memory = new HashMap<Integer, Rounds>();

        var lastNumber = -1;

        for (var position = 0; position < split.length; position++) {
            lastNumber = Integer.parseInt(split[position]);
            memory.put(lastNumber, new Rounds(position, position));
        }

        for (var round = initialLoad.length; round < 2020; round++) {
            var finalRound = round;
            var rounds = memory.computeIfAbsent(lastNumber, key -> new Rounds(finalRound, finalRound));
            lastNumber = rounds.lastRound - rounds.beforeLastRound;

            rounds = memory.computeIfAbsent(lastNumber, key -> new Rounds(finalRound, finalRound));

            rounds.beforeLastRound = rounds.lastRound;
            rounds.lastRound = round;
        }

        return lastNumber;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var split = input.findFirst()
                         .map(s -> s.split(","))
                         .orElseThrow(() -> new AssertionError("Eek!"));

        var initialLoad = new int[split.length];

        var memory = new HashMap<Integer, Rounds>();

        var lastNumber = -1;

        for (var position = 0; position < split.length; position++) {
            lastNumber = Integer.parseInt(split[position]);
            memory.put(lastNumber, new Rounds(position, position));
        }

        for (var round = initialLoad.length; round < 30000000; round++) {
            var finalRound = round;
            var rounds = memory.computeIfAbsent(lastNumber, key -> new Rounds(finalRound, finalRound));
            lastNumber = rounds.lastRound - rounds.beforeLastRound;

            rounds = memory.computeIfAbsent(lastNumber, key -> new Rounds(finalRound, finalRound));

            rounds.beforeLastRound = rounds.lastRound;
            rounds.lastRound = round;
        }

        return lastNumber;
    }

    private static class Rounds {
        private int lastRound;
        private int beforeLastRound;

        public Rounds(int lastRound, int beforeLastRound) {
            this.lastRound = lastRound;
            this.beforeLastRound = beforeLastRound;
        }
    }
}
