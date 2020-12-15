package nl.remcoder.adventofcode;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Day15 {
    public int handlePart1(Stream<String> input) {
        String[] split = input.findFirst().get().split(",");

        int[] initialLoad = new int[split.length];

        Map<Integer, Rounds> memory = new HashMap<>();

        int lastNumber = -1;

        for (int position = 0; position < split.length; position++) {
            lastNumber = Integer.parseInt(split[position]);
            memory.put(lastNumber, new Rounds(position, position));
        }

        for (int round = initialLoad.length; round < 2020; round++) {
            int finalRound = round;
            Rounds rounds = memory.computeIfAbsent(lastNumber, key -> new Rounds(finalRound, finalRound));
            lastNumber = rounds.lastRound - rounds.beforeLastRound;

            rounds = memory.computeIfAbsent(lastNumber, key -> new Rounds(finalRound, finalRound));

            rounds.beforeLastRound = rounds.lastRound;
            rounds.lastRound = round;
        }

        return lastNumber;
    }

    public int handlePart2(Stream<String> input) {
        String[] split = input.findFirst().get().split(",");

        int[] initialLoad = new int[split.length];

        Map<Integer, Rounds> memory = new HashMap<>();

        int lastNumber = -1;

        for (int position = 0; position < split.length; position++) {
            lastNumber = Integer.parseInt(split[position]);
            memory.put(lastNumber, new Rounds(position, position));
        }

        for (int round = initialLoad.length; round < 30000000; round++) {
            int finalRound = round;
            Rounds rounds = memory.computeIfAbsent(lastNumber, key -> new Rounds(finalRound, finalRound));
            lastNumber = rounds.lastRound - rounds.beforeLastRound;

            rounds = memory.computeIfAbsent(lastNumber, key -> new Rounds(finalRound, finalRound));

            rounds.beforeLastRound = rounds.lastRound;
            rounds.lastRound = round;
        }

        return lastNumber;
    }

    private class Rounds {
        private int lastRound;
        private int beforeLastRound;

        public Rounds(int lastRound, int beforeLastRound) {
            this.lastRound = lastRound;
            this.beforeLastRound = beforeLastRound;
        }
    }
}
