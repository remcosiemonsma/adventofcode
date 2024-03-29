package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day14 implements AdventOfCodeSolution<Integer> {
    private static final Pattern REINDEER_PATTERN =
            Pattern.compile("(.*) can fly (\\d*) km/s for (\\d*) seconds, but then must rest for (\\d*) seconds.");

    @Override
    public Integer handlePart2(Stream<String> input) {
        var reindeer = input.map(REINDEER_PATTERN::matcher)
                            .filter(Matcher::matches)
                            .map(this::mapToReindeer)
                            .toList();

        for (var i = 0; i < 2503; i++) {
            reindeer.forEach(Reindeer::tick);
            int maxDistance = reindeer.stream()
                                      .mapToInt(Reindeer::getDistance)
                                      .max()
                                      .orElseThrow(() -> new AssertionError("Eek!"));
            reindeer.stream()
                    .filter(leadingReindeer -> leadingReindeer.getDistance() == maxDistance)
                    .forEach(Reindeer::incrementScore);
        }

        return reindeer
                .stream()
                .mapToInt(Reindeer::getScore)
                .max()
                .orElseThrow(() -> new AssertionError("Eek!"));
    }

    @Override
    public Integer handlePart1(Stream<String> input) {
        var reindeer = input.map(REINDEER_PATTERN::matcher)
                            .filter(Matcher::matches)
                            .map(this::mapToReindeer)
                            .toList();

        for (var i = 0; i < 2503; i++) {
            reindeer.forEach(Reindeer::tick);
        }

        return reindeer.stream()
                       .mapToInt(Reindeer::getDistance)
                       .max()
                       .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private Reindeer mapToReindeer(Matcher matcher) {
        return new Reindeer(Integer.parseInt(matcher.group(2)),
                            Integer.parseInt(matcher.group(3)),
                            Integer.parseInt(matcher.group(4)));
    }

    private static class Reindeer {
        private final int speed;
        private final int runtime;
        private final int resttime;
        private State state;
        private int restperiod;
        private int runperiod;
        private int distance;
        private int score;

        private Reindeer(int speed, int runtime, int resttime) {
            this.speed = speed;
            this.runtime = runtime;
            this.resttime = resttime;
            state = State.FLYING;
        }

        public void tick() {
            switch (state) {
                case FLYING -> fly();
                case RESTING -> rest();
            }
        }

        private void rest() {
            restperiod--;
            if (restperiod == 0) {
                state = State.FLYING;
                runperiod = 0;
            }
        }

        private void fly() {
            runperiod++;
            distance += speed;
            if (runperiod == runtime) {
                state = State.RESTING;
                restperiod = resttime;
            }
        }

        public int getDistance() {
            return distance;
        }

        public int getScore() {
            return score;
        }

        public void incrementScore() {
            score++;
        }

        private enum State {
            RESTING,
            FLYING
        }
    }
}
